package exter.foundry.integration;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import exter.foundry.api.FoundryAPI;
import exter.foundry.api.substance.InfuserSubstance;
import exter.foundry.config.FoundryConfig;
import exter.foundry.item.FoundryItems;
import exter.foundry.item.ItemMold;
import exter.foundry.recipes.FoundryRecipes;
import exter.foundry.recipes.manager.CastingRecipeManager;
import exter.foundry.recipes.manager.InfuserRecipeManager;
import exter.foundry.recipes.manager.MeltingRecipeManager;
import exter.foundry.util.FoundryMiscUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidStack;

public class ModIntegrationRailcraft extends ModIntegration {

    public boolean gear_recipes;

    public ModIntegrationRailcraft(String mod_name) {
        super(mod_name);
    }

    @Override
    public void OnPreInit(Configuration config) {
        gear_recipes = config.get("integration", Name + ".gears", true).getBoolean(true);
    }

    @Override
    public void OnInit() {}

    @Override
    public void OnPostInit() {
        if (!Loader.isModLoaded("Railcraft")) {
            is_loaded = false;
            return;
        }

        ItemStack steel_pickaxe = GameRegistry.findItemStack("Railcraft", "tool.steel.pickaxe", 1);
        ItemStack steel_axe = GameRegistry.findItemStack("Railcraft", "tool.steel.axe", 1);
        ItemStack steel_shovel = GameRegistry.findItemStack("Railcraft", "tool.steel.shovel", 1);
        ItemStack steel_hoe = GameRegistry.findItemStack("Railcraft", "tool.steel.hoe", 1);
        ItemStack steel_sword = GameRegistry.findItemStack("Railcraft", "tool.steel.sword", 1);

        ItemStack steel_helmet = GameRegistry.findItemStack("Railcraft", "armor.steel.helmet", 1);
        ItemStack steel_chestplate = GameRegistry.findItemStack("Railcraft", "armor.steel.plate", 1);
        ItemStack steel_leggings = GameRegistry.findItemStack("Railcraft", "armor.steel.legs", 1);
        ItemStack steel_boots = GameRegistry.findItemStack("Railcraft", "armor.steel.boots", 1);

        ItemStack iron_gear = GameRegistry.findItemStack("Railcraft", "part.gear.iron", 1);
        ItemStack steel_gear = GameRegistry.findItemStack("Railcraft", "part.gear.steel", 1);
        ItemStack goldplated_gear = GameRegistry.findItemStack("Railcraft", "part.gear.gold.plate", 1);

        ItemStack bushing = GameRegistry.findItemStack("Railcraft", "part.gear.bushing", 1);
        // ItemStack tin_plate = GameRegistry.findItemStack("Railcraft", "part.plate.tin", 1);
        ItemStack iron_plate = GameRegistry.findItemStack("Railcraft", "part.plate.iron", 1);
        ItemStack copper_plate = GameRegistry.findItemStack("Railcraft", "part.plate.copper", 1);
        ItemStack steel_plate = GameRegistry.findItemStack("Railcraft", "part.plate.steel", 1);

        if (FoundryConfig.recipe_tools_armor) {
            ItemStack extra_sticks1 = new ItemStack(Items.stick, 1);
            ItemStack extra_sticks2 = new ItemStack(Items.stick, 2);

            RegisterCasting(steel_chestplate, FoundryRecipes.liquid_steel, 8, ItemMold.MOLD_CHESTPLATE, null);
            RegisterCasting(steel_helmet, FoundryRecipes.liquid_steel, 5, ItemMold.MOLD_HELMET, null);
            RegisterCasting(steel_leggings, FoundryRecipes.liquid_steel, 7, ItemMold.MOLD_LEGGINGS, null);
            RegisterCasting(steel_boots, FoundryRecipes.liquid_steel, 4, ItemMold.MOLD_BOOTS, null);

            RegisterCasting(steel_pickaxe, FoundryRecipes.liquid_steel, 3, ItemMold.MOLD_PICKAXE, extra_sticks2);
            RegisterCasting(steel_axe, FoundryRecipes.liquid_steel, 3, ItemMold.MOLD_AXE, extra_sticks2);
            RegisterCasting(steel_hoe, FoundryRecipes.liquid_steel, 2, ItemMold.MOLD_HOE, extra_sticks2);
            RegisterCasting(steel_shovel, FoundryRecipes.liquid_steel, 1, ItemMold.MOLD_SHOVEL, extra_sticks2);
            RegisterCasting(steel_sword, FoundryRecipes.liquid_steel, 2, ItemMold.MOLD_SWORD, extra_sticks1);

            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_BOOTS_SOFT, steel_boots);
            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_HELMET_SOFT, steel_helmet);
            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_LEGGINGS_SOFT, steel_leggings);
            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_HOE_SOFT, steel_hoe);
            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_SWORD_SOFT, steel_sword);
            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_SHOVEL_SOFT, steel_shovel);
            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_AXE_SOFT, steel_axe);
            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_PICKAXE_SOFT, steel_pickaxe);
            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_CHESTPLATE_SOFT, steel_chestplate);
        }

        MeltingRecipeManager.instance.AddRecipe(
                "orePoorIron", new FluidStack(FoundryRecipes.liquid_iron, FoundryAPI.FLUID_AMOUNT_NUGGET * 2));
        MeltingRecipeManager.instance.AddRecipe(
                "orePoorCopper", new FluidStack(FoundryRecipes.liquid_copper, FoundryAPI.FLUID_AMOUNT_NUGGET * 2));
        MeltingRecipeManager.instance.AddRecipe(
                "orePoorTin", new FluidStack(FoundryRecipes.liquid_tin, FoundryAPI.FLUID_AMOUNT_NUGGET * 2));
        MeltingRecipeManager.instance.AddRecipe(
                "orePoorGold", new FluidStack(FoundryRecipes.liquid_gold, FoundryAPI.FLUID_AMOUNT_NUGGET * 2));

        if (iron_gear != null) {
            FoundryMiscUtils.RegisterInOreDictionary("gearIron", iron_gear);
        }
        if (steel_gear != null) {
            FoundryMiscUtils.RegisterInOreDictionary("gearSteel", steel_gear);
        }

        if (!FoundryConfig.recipe_gear_useoredict && gear_recipes) {
            ItemStack mold_gear = FoundryItems.Mold(ItemMold.MOLD_GEAR);
            if (iron_gear != null) {
                MeltingRecipeManager.instance.AddRecipe(
                        iron_gear, new FluidStack(FoundryRecipes.liquid_iron, FoundryAPI.FLUID_AMOUNT_INGOT * 4));
                CastingRecipeManager.instance.AddRecipe(
                        iron_gear,
                        new FluidStack(FoundryRecipes.liquid_iron, FoundryAPI.FLUID_AMOUNT_INGOT * 4),
                        mold_gear,
                        bushing);
            }

            if (steel_gear != null) {
                MeltingRecipeManager.instance.AddRecipe(
                        steel_gear, new FluidStack(FoundryRecipes.liquid_steel, FoundryAPI.FLUID_AMOUNT_INGOT * 4));
                CastingRecipeManager.instance.AddRecipe(
                        steel_gear,
                        new FluidStack(FoundryRecipes.liquid_steel, FoundryAPI.FLUID_AMOUNT_INGOT * 4),
                        mold_gear,
                        bushing);
            }

            if (goldplated_gear != null) {
                MeltingRecipeManager.instance.AddRecipe(
                        goldplated_gear,
                        new FluidStack(FoundryRecipes.liquid_gold, FoundryAPI.FLUID_AMOUNT_NUGGET * 4));
                CastingRecipeManager.instance.AddRecipe(
                        goldplated_gear,
                        new FluidStack(FoundryRecipes.liquid_gold, FoundryAPI.FLUID_AMOUNT_NUGGET * 4),
                        mold_gear,
                        bushing);
            }
        }

        ModIntegration gti = GetIntegration("gregtech");
        if (gti == null || !Loader.isModLoaded("gregtech")) {
            ItemStack mold_plate = FoundryItems.Mold(ItemMold.MOLD_PLATE);

            CastingRecipeManager.instance.AddRecipe(
                    copper_plate,
                    new FluidStack(FoundryRecipes.liquid_copper, FoundryAPI.FLUID_AMOUNT_INGOT),
                    mold_plate,
                    null);
            // CastingRecipeManager.instance.AddRecipe(tin_plate, new FluidStack(liquid_tin,
            // FoundryAPI.FLUID_AMOUNT_INGOT), mold_plate, null);
            CastingRecipeManager.instance.AddRecipe(
                    iron_plate,
                    new FluidStack(FoundryRecipes.liquid_iron, FoundryAPI.FLUID_AMOUNT_INGOT),
                    mold_plate,
                    null);
            CastingRecipeManager.instance.AddRecipe(
                    steel_plate,
                    new FluidStack(FoundryRecipes.liquid_steel, FoundryAPI.FLUID_AMOUNT_INGOT),
                    mold_plate,
                    null);

            RegisterPlateMoldRecipe(copper_plate, "plateCopper");
            // RegisterPlateMoldRecipe(tin_plate, "plateTin");
            RegisterPlateMoldRecipe(iron_plate, "plateIron");
            RegisterPlateMoldRecipe(steel_plate, "plateSteel");

            FoundryMiscUtils.RegisterInOreDictionary("plateCopper", copper_plate);
            // FoundryMiscUtils.RegisterInOreDictionary("plateTin", tin_plate);
            FoundryMiscUtils.RegisterInOreDictionary("plateIron", iron_plate);
            FoundryMiscUtils.RegisterInOreDictionary("plateSteel", steel_plate);
        }

        if (FoundryConfig.recipe_steel_enable) {
            ItemStack coal_coke = GameRegistry.findItemStack("Railcraft", "fuel.coke", 1);
            ItemStack coal_coke_block = GameRegistry.findItemStack("Railcraft", "cube.coke", 1);

            if (coal_coke != null) {
                InfuserRecipeManager.instance.AddSubstanceRecipe(new InfuserSubstance("carbon", 36), coal_coke, 110000);
            }

            if (coal_coke_block != null) {
                InfuserRecipeManager.instance.AddSubstanceRecipe(
                        new InfuserSubstance("carbon", 324), coal_coke_block, 880000);
            }
        }
    }
}
