package exter.foundry.integration.nei;

import codechicken.nei.recipe.GuiCraftingRecipe;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.GuiUsageRecipe;
import exter.foundry.api.substance.InfuserSubstance;
import java.awt.Rectangle;
import java.util.List;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public abstract class FoundryRecipeHandlerSubstance extends FoundryRecipeHandler {

    public abstract class CachedFoundryRecipeSubstance extends CachedFoundryRecipe {
        public abstract InfuserSubstance GetSubstance();
    }

    protected abstract Rectangle GetSubstanceRect();

    @Override
    public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe) {
        CachedFoundryRecipeSubstance foundryRecipe = (CachedFoundryRecipeSubstance) arecipes.get(recipe);
        InfuserSubstance subs = foundryRecipe.GetSubstance();
        if (isMouseOver(GetSubstanceRect(), gui, recipe)) {
            currenttip.add(StatCollector.translateToLocal("substance." + subs.type));
            currenttip.add(String.valueOf(EnumChatFormatting.GRAY) + subs.amount + " mL");
        }
        return super.handleTooltip(gui, currenttip, recipe);
    }

    @Override
    public boolean mouseClicked(GuiRecipe gui, int button, int recipe) {
        CachedFoundryRecipeSubstance foundryRecipe = (CachedFoundryRecipeSubstance) arecipes.get(recipe);
        InfuserSubstance subs = foundryRecipe.GetSubstance();
        if (isMouseOver(GetSubstanceRect(), gui, recipe)) {
            if (button == 0) {
                return GuiCraftingRecipe.openRecipeGui("foundry.substance", subs);
            }
            if (button == 1) {
                return GuiUsageRecipe.openRecipeGui("foundry.substance", subs);
            }
        }
        return super.mouseClicked(gui, button, recipe);
    }

    protected void SetColor(int color) {
        float red = (float) (color >> 16 & 255) / 255.0F;
        float green = (float) (color >> 8 & 255) / 255.0F;
        float blue = (float) (color & 255) / 255.0F;
        GL11.glColor4f(red, green, blue, 1.0f);
    }
}
