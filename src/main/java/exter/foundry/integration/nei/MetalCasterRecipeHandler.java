package exter.foundry.integration.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import exter.foundry.api.FoundryUtils;
import exter.foundry.api.orestack.OreStack;
import exter.foundry.api.recipe.ICastingRecipe;
import exter.foundry.block.BlockFoundryMachine;
import exter.foundry.block.FoundryBlocks;
import exter.foundry.gui.GuiMetalCaster;
import exter.foundry.recipes.manager.CastingRecipeManager;
import java.awt.Rectangle;
import java.util.List;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class MetalCasterRecipeHandler extends FoundryRecipeHandler {

    public static int GUI_SMELT_TIME = 40;

    public static ProgressBar PROGRESS = new ProgressBar(60, 51, 176, 53, 27, 15, 0, GUI_SMELT_TIME);

    public class CachedCasterRecipe extends CachedFoundryRecipe {

        FluidTank tank;
        PositionedStack mold;
        PositionedStack extra;
        PositionedStack output;

        public CachedCasterRecipe(ICastingRecipe recipe) {
            tank = new FluidTank(recipe.GetInputFluid(), 6000, new Rectangle(34, 10, 16, 47));
            mold = new PositionedStack(recipe.GetInputMold(), 61, 10, true);
            output = new PositionedStack(asItemStackOrList(recipe.GetOutput()), 81, 40, true);
            output.setPermutationToRender(0);
            List<ItemStack> extras = getExtraItems(recipe);
            if (!extras.isEmpty()) {
                extra = new PositionedStack(extras.toArray(new ItemStack[0]), 81, 10, true);
            }
        }

        @Override
        public FluidTank getTank() {
            return tank;
        }

        @Override
        public PositionedStack getResult() {
            return output;
        }

        @Override
        public List<PositionedStack> getIngredients() {
            return extra != null
                    ? ImmutableList.<PositionedStack>of(mold, extra)
                    : ImmutableList.<PositionedStack>of(mold);
        }
    }

    @SuppressWarnings("unchecked")
    protected List<ItemStack> getExtraItems(ICastingRecipe recipe) {
        Object extra = recipe.GetInputExtra();
        if (extra == null) {
            return Lists.newArrayList();
        } else if (extra instanceof OreStack) {
            OreStack stack = (OreStack) extra;
            List<ItemStack> list = (List<ItemStack>) asItemStackOrList(stack.name);
            if (list != null && !list.isEmpty()) {
                for (ItemStack s : list) {
                    s.stackSize = stack.amount;
                }
            }
            return list;
        } else if (recipe.GetInputExtra() instanceof String) {
            List<ItemStack> list = (List<ItemStack>) asItemStackOrList(recipe.GetInputExtra());
            if (list != null && !list.isEmpty()) {
                for (ItemStack stack : list) {
                    stack.stackSize = 1;
                }
            }
            return list;
        } else if (recipe.GetInputExtra() instanceof ItemStack) {
            ItemStack stack = (ItemStack) recipe.GetInputExtra();
            return Lists.newArrayList(stack);
        }
        return Lists.newArrayList();
    }

    @Override
    public String getRecipeName() {
        return "Metal Caster";
    }

    @Override
    public String getGuiTexture() {
        return "foundry:textures/gui/caster.png";
    }

    @Override
    public void drawExtras(int recipe) {
        CachedCasterRecipe castingRecipe = (CachedCasterRecipe) arecipes.get(recipe);
        int currentProgress = castingRecipe.getAgeTicks() % GUI_SMELT_TIME;
        if (currentProgress > 0) {
            drawProgressBar(new ProgressBar(55, 40, 176, 53, 27, 15, 0, GUI_SMELT_TIME), currentProgress);
        }
        drawTanks(
                castingRecipe.getTanks(), GUI_SMELT_TIME - castingRecipe.getAgeTicks() / GUI_SMELT_TIME, TANK_OVERLAY);
    }

    @Override
    public boolean mouseClicked(GuiRecipe gui, int button, int recipe) {
        CachedCasterRecipe castingRecipe = (CachedCasterRecipe) arecipes.get(recipe);
        if (isMouseOver(castingRecipe.getTank().position, gui, recipe)) {
            if (button == 0) {
                return GuiCraftingRecipe.openRecipeGui("liquid", castingRecipe.getTank().fluid);
            }
            if (button == 1) {
                return GuiUsageRecipe.openRecipeGui("liquid", castingRecipe.getTank().fluid);
            }
        }
        return super.mouseClicked(gui, button, recipe);
    }

    public void loadAllRecipes() {
        for (ICastingRecipe recipe : CastingRecipeManager.instance.GetRecipes()) {
            addRecipe(recipe);
        }
    }

    @Override
    public void loadUsageRecipes(String outputId, Object... results) {
        if (outputId.equals("foundry.casting")) {
            loadAllRecipes();
        }
        if (outputId.equals("item")) {
            for (ICastingRecipe recipe : CastingRecipeManager.instance.GetRecipes()) {
                for (ItemStack stack : getExtraItems(recipe)) {
                    if (stack.isItemEqual((ItemStack) results[0])) {
                        addRecipe(recipe);
                    }
                }
                if (recipe.GetInputMold().isItemEqual((ItemStack) results[0]) && recipe.GetOutput() != null) {
                    addRecipe(recipe);
                }
                FluidStack fluid = getFluidStackFor((ItemStack) results[0]);
                if (fluid != null && fluid.isFluidEqual(recipe.GetInputFluid())) {
                    addRecipe(recipe);
                }
            }
        }
        if (outputId.equals("liquid")) {
            for (ICastingRecipe recipe : CastingRecipeManager.instance.GetRecipes()) {
                if (recipe.GetInputFluid().isFluidEqual((FluidStack) results[0])) {
                    addRecipe(recipe);
                }
            }
        }
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals("foundry.casting")) {
            loadAllRecipes();
        }
        if (outputId.equals("item")) {
            for (ICastingRecipe recipe : CastingRecipeManager.instance.GetRecipes()) {
                Object output = recipe.GetOutput();
                if (output != null && FoundryUtils.IsItemMatch((ItemStack) results[0], output)) {
                    arecipes.add(new CachedCasterRecipe(recipe));
                }
            }
        }
    }

    public void addRecipe(ICastingRecipe recipe) {
        if (recipe.GetOutput() != null && recipe.GetInputFluid() != null) {
            if (recipe.GetOutput() instanceof String) {
                if (OreDictionary.getOres((String) recipe.GetOutput()).size() == 0) {
                    return;
                }
            }
            arecipes.add(new CachedCasterRecipe(recipe));
        }
    }

    @Override
    public ItemStack getMachineItem() {
        return new ItemStack(FoundryBlocks.block_machine, 1, BlockFoundryMachine.MACHINE_CASTER);
    }

    @Override
    public Rectangle getRecipeRect() {
        return new Rectangle(55, 40, 25, 15);
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(55, 40, 25, 15), "foundry.casting", new Object[0]));
    }

    @Override
    public List<Class<? extends GuiContainer>> getRecipeTransferRectGuis() {
        return ImmutableList.<Class<? extends GuiContainer>>of(GuiMetalCaster.class);
    }
}
