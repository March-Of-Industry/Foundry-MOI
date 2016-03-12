package exter.foundry.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import exter.foundry.api.FoundryAPI;
import exter.foundry.api.recipe.ICastingRecipe;
import exter.foundry.config.FoundryConfig;
import exter.foundry.item.FoundryItems;
import exter.foundry.item.ItemMold;
import exter.foundry.recipes.manager.AlloyMixerRecipeManager;
import exter.foundry.recipes.manager.AtomizerRecipeManager;
import exter.foundry.recipes.manager.CastingRecipeManager;
import exter.foundry.recipes.manager.MeltingRecipeManager;
import exter.foundry.registry.FluidLiquidMetal;
import exter.foundry.registry.LiquidMetalRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.util.RecipeMatch.Match;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.smeltery.AlloyRecipe;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

@Optional.Interface(iface = "exter.foundry.integration.IModIntegration", modid = "tconstruct")
public class ModIntegrationTiCon implements IModIntegration
{
  private Map<String,String> liquid_map;
  private Map<String,String> reverse_liquid_map;
  static private final int GCD(int a, int b)
  {
    while(b != 0)
    {
      int t = b;
      b = a % b;
      a = t;
    }
    return a;
  }
  
  static private int DivCeil(int a,int b)
  {
    return a / b + ((a % b == 0) ? 0 : 1);
  }

  static private final int TICON_INGOT_AMOUNT = 144;

  static private final int INGOT_GCD = GCD(TICON_INGOT_AMOUNT,FoundryAPI.FLUID_AMOUNT_INGOT);

  @Optional.Method(modid = "tconstruct")
  @Override
  public void onPreInit(Configuration config)
  {

  }

  @Optional.Method(modid = "tconstruct")
  @Override
  public void onInit()
  {
  }

  @Optional.Method(modid = "tconstruct")
  private void createAlloyRecipe(AlloyRecipe mix,int index,List<FluidStack> inputs)
  {
    if(index == mix.getFluids().size())
    {
      FluidStack[] in = new FluidStack[mix.getFluids().size()];
      in = inputs.toArray(in);
      FluidStack result = new FluidStack(mix.getResult().getFluid(),mix.getResult().amount * 9 / INGOT_GCD);
      AlloyMixerRecipeManager.instance.addRecipe(result, in);
      return;
    }

    FluidStack ing = mix.getFluids().get(index);
    String mapped = liquid_map.get(ing.getFluid().getName());
    if(mapped != null)
    {
      List<FluidStack> in = new ArrayList<FluidStack>(inputs);
      in.add(new FluidStack( // Convert TiCon Fluid Stack to Foundry Fluid Stack
          LiquidMetalRegistry.instance.getFluid(mapped),
          ing.amount * 9 * TICON_INGOT_AMOUNT / (FoundryAPI.FLUID_AMOUNT_INGOT * INGOT_GCD)));
      createAlloyRecipe(mix,index + 1,in);
    }
    List<FluidStack> in = new ArrayList<FluidStack>(inputs);
    FluidStack fl = ing;
    in.add(new FluidStack(fl.getFluid(),fl.amount * 9 / INGOT_GCD));
    createAlloyRecipe(mix,index + 1,in);
  }
  
  @Optional.Method(modid = "tconstruct")
  private void createAlloyRecipe(AlloyRecipe mix)
  {
    if(mix.getFluids().size() > 4)
    {
      return;
    }
    createAlloyRecipe(mix,0,new ArrayList<FluidStack>());
  }

  @Optional.Method(modid = "tconstruct")
  @Override
  public void onPostInit()
  {
    
  }
  
 
  @Optional.Method(modid = "tconstruct")
  @Override
  public void onAfterPostInit()
  {

    ItemStack ingot_cast = ItemStack.copyItemStack(TinkerSmeltery.castIngot);
    
    liquid_map = new HashMap<String,String>();
    for(String name:LiquidMetalRegistry.instance.getFluidNames())
    {
      if(name.equals("Glass"))
      {
        if(FoundryConfig.recipe_glass)
        {
          if(FluidRegistry.getFluid("glass") != null)
          {
            liquid_map.put("glass", "Glass");
          }
        }
      } else if(!name.startsWith("Glass") && !LiquidMetalRegistry.instance.getFluid(name).special)
      {
        String tic_name = name.toLowerCase();
        if(FluidRegistry.getFluid(tic_name) != null)
        {
          liquid_map.put(tic_name, name);
        }
      }
    }

    reverse_liquid_map = new HashMap<String,String>();
    for(Map.Entry<String,String> e:liquid_map.entrySet())
    {
      reverse_liquid_map.put(
          LiquidMetalRegistry.instance.getFluid(e.getValue()).getName(),
          e.getKey());
    }

    //Add support for TiCon's fluids to the Metal Caster recipes.
    for(ICastingRecipe casting:new ArrayList<ICastingRecipe>(CastingRecipeManager.instance.getRecipes()))
    {
      String mapped = reverse_liquid_map.get(casting.getInput().getFluid().getName());
      if(mapped != null)
      {
        Fluid mapped_fluid = FluidRegistry.getFluid(mapped);
        if(mapped_fluid != null)
        {
          CastingRecipeManager.instance.addRecipe(
              casting.getOutput(),
              new FluidStack(
                  mapped_fluid,
                  DivCeil(casting.getInput().amount * TICON_INGOT_AMOUNT, FoundryAPI.FLUID_AMOUNT_INGOT)),
              casting.getMold(),
              casting.getInputExtra());
        }
      }
    }

    
    //Convert TiCon Smeltery recipes to Foundry ICF melting recipes (except those that have an existing recipe).
    for(slimeknights.tconstruct.library.smeltery.MeltingRecipe recipe : TinkerRegistry.getAllMeltingRecipies())
    {
      
      for(ItemStack stack:recipe.input.getInputs())
      {
        if(MeltingRecipeManager.instance.findRecipe(stack) == null)
        {
          FluidStack result = recipe.output;
          String mapped = liquid_map.get(result.getFluid().getName());
          if(mapped != null)
          {
            FluidStack mapped_liquid;
          
            if(mapped.equals("Glass"))
            {
              mapped_liquid = new FluidStack(
                  LiquidMetalRegistry.instance.getFluid(mapped),
                  result.amount);
            } else
            {
              mapped_liquid = new FluidStack(
                  LiquidMetalRegistry.instance.getFluid(mapped),
                  DivCeil(result.amount * FoundryAPI.FLUID_AMOUNT_INGOT, TICON_INGOT_AMOUNT));
            }
            if(mapped_liquid.amount <= 6000)
            {
              MeltingRecipeManager.instance.addRecipe(stack, mapped_liquid);
            }
          } else
          {
            if(result.amount <= 6000)
            {
              int temp = recipe.temperature + 274;
              if(temp < 350)
              {
                temp = 350;
              }
              MeltingRecipeManager.instance.addRecipe(stack, result, temp);
            }
          }
        }
      }
    }
    
    //Convert TiCon Alloy recipes Foundry Alloy Mixer recipes.
    for(AlloyRecipe mix:TinkerRegistry.getAlloys())
    {
      String mapped_result = liquid_map.get(mix.getResult().getFluid().getName());
      if(mapped_result == null)
      {
        createAlloyRecipe(mix);
      }
    }
    
    
    //Convert TiCon table casting recipes to Foundry Metal Caster recipes.
    for(slimeknights.tconstruct.library.smeltery.CastingRecipe casting:TinkerRegistry.getAllTableCastingRecipes())
    {
      if(casting.cast != null && !casting.consumesCast())
      {
        String mapped = liquid_map.get(casting.getFluid().getFluid().getName());
        FluidStack mapped_liquid = null;
        if(mapped != null)
        {
          mapped_liquid = new FluidStack(
              LiquidMetalRegistry.instance.getFluid(mapped),
              DivCeil(casting.getFluid().amount * FoundryAPI.FLUID_AMOUNT_INGOT, TICON_INGOT_AMOUNT));
        }
        for(ItemStack cast:casting.cast.getInputs())
        {
          if(!CastingRecipeManager.instance.isItemMold(cast))
          {
            //Register the cast as a mold
            CastingRecipeManager.instance.addMold(cast);
          }
        

          if(cast.isItemEqual(ingot_cast))
          {
            ItemStack ingot_mold = FoundryItems.mold(ItemMold.MOLD_INGOT);
            if(casting.getFluid().amount <= 6000)
            {
              CastingRecipeManager.instance.addRecipe(casting.getResult(), casting.getFluid(), ingot_mold, null);
            }
          } else if(mapped_liquid != null)
          {
            if(mapped_liquid.amount <= 6000)
           {
              CastingRecipeManager.instance.addRecipe(casting.getResult(), mapped_liquid, cast, null);
            }
          }
          if(casting.getFluid().amount <= 6000)
          {
            CastingRecipeManager.instance.addRecipe(casting.getResult(), casting.getFluid(), cast, null);
          }
        }
      }
    }
    ItemStack block_mold = FoundryItems.mold(ItemMold.MOLD_BLOCK);
    for(slimeknights.tconstruct.library.smeltery.CastingRecipe casting:TinkerRegistry.getAllBasinCastingRecipes())
    {
      if(casting.getFluid().amount <= 6000 && casting.cast == null)
      {
        CastingRecipeManager.instance.addRecipe(casting.getResult(), casting.getFluid(), block_mold, null);
      }
    }
    
    //Add support for Foundry's fluid to the TiCon casting table.
    List<slimeknights.tconstruct.library.smeltery.CastingRecipe> recipes = new ArrayList<slimeknights.tconstruct.library.smeltery.CastingRecipe>();
    for(slimeknights.tconstruct.library.smeltery.CastingRecipe casting : TinkerRegistry.getAllTableCastingRecipes())
    {
      if(casting.cast != null)
      {
        Match bucket_match = casting.cast.matches(new ItemStack[] {new ItemStack(Items.bucket)});
        if(bucket_match != null && bucket_match.stacks != null && bucket_match.stacks.size() > 0)
        {
          continue;
        }
      }
      String mapped = liquid_map.get(casting.getFluid().getFluid().getName());
      if(mapped == null)
      {
        continue;
      }
      FluidLiquidMetal fluid = LiquidMetalRegistry.instance.getFluid(mapped);
      FluidStack mapped_liquid = new FluidStack(
          fluid,
          mapped.equals("Glass") ?
              casting.getFluid().amount :
              DivCeil(casting.getFluid().amount * FoundryAPI.FLUID_AMOUNT_INGOT, TICON_INGOT_AMOUNT));
      slimeknights.tconstruct.library.smeltery.CastingRecipe recipe = new slimeknights.tconstruct.library.smeltery.CastingRecipe(
          casting.getResult(),
          casting.cast,
          mapped_liquid,
          casting.consumesCast(),
          casting.switchOutputs());
      recipes.add(recipe);
    }
    for(slimeknights.tconstruct.library.smeltery.CastingRecipe r : recipes)
    {
      TinkerRegistry.registerTableCasting(r);
    }
    
    
    //Add support for Foundry's fluid to the TiCon casting basin.
    recipes.clear();
    for(slimeknights.tconstruct.library.smeltery.CastingRecipe casting : TinkerRegistry.getAllBasinCastingRecipes())
    {
      if(casting.cast != null)
      {
        continue;
      }
      String mapped = liquid_map.get(casting.getFluid().getFluid().getName());
      if(mapped == null)
      {
        continue;
      }
      FluidLiquidMetal fluid = LiquidMetalRegistry.instance.getFluid(mapped);
      FluidStack mapped_liquid = new FluidStack(
          fluid,
        mapped.equals("Glass") ?
            casting.getFluid().amount :
            DivCeil(casting.getFluid().amount * FoundryAPI.FLUID_AMOUNT_INGOT, TICON_INGOT_AMOUNT));
      slimeknights.tconstruct.library.smeltery.CastingRecipe recipe = new slimeknights.tconstruct.library.smeltery.CastingRecipe(
          casting.getResult(),
          null,
          mapped_liquid,
          casting.consumesCast(),
          casting.switchOutputs());
      recipes.add(recipe);
    }
    for(slimeknights.tconstruct.library.smeltery.CastingRecipe r : recipes)
    {
      TinkerRegistry.registerBasinCasting(r);
    }
    
    //Add TiCon molten metal support to the Atomizer.
    for(Map.Entry<String, String> entry:liquid_map.entrySet())
    {
      String name = entry.getKey();
      if(!name.equals("molten_glass"))
      {
        AtomizerRecipeManager.instance.addRecipe("dust" + entry.getValue(), new FluidStack(FluidRegistry.getFluid(name),TICON_INGOT_AMOUNT));
      }
    }
  }

  @Optional.Method(modid = "tconstruct")
  @Override
  public String getName()
  {
    return "TiCon";
  }

  @Optional.Method(modid = "tconstruct")
  @SideOnly(Side.CLIENT)
  @Override
  public void onClientPreInit()
  {
    
  }

  @Optional.Method(modid = "tconstruct")
  @SideOnly(Side.CLIENT)
  @Override
  public void onClientInit()
  {
    
  }

  @Optional.Method(modid = "tconstruct")
  @SideOnly(Side.CLIENT)
  @Override
  public void onClientPostInit()
  {
    
  }
}
