package exter.foundry.integration.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.google.common.collect.ImmutableList;
import exter.foundry.Tags;
import exter.foundry.block.FoundryBlocks;
import java.util.List;
import net.minecraft.item.ItemStack;

public class NEIFoundryConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {
        API.hideItem(new ItemStack(FoundryBlocks.block_slabdouble1));
        API.hideItem(new ItemStack(FoundryBlocks.block_slabdouble2));
        API.hideItem(new ItemStack(FoundryBlocks.block_slabdouble3));

        List<TemplateRecipeHandler> handlers = ImmutableList.<TemplateRecipeHandler>of(
                new InductionCrucibleFurnaceRecipeHandler(),
                new AlloyMixerRecipeHandler(),
                new MetalCasterRecipeHandler(),
                new InfuserRecipeHandler(),
                new InfuserSubstanceRecipeHandler(),
                new AlloyFurnaceRecipeHandler(),
                new MetalAtomizerRecipeHandler());

        for (TemplateRecipeHandler handler : handlers) {
            API.registerRecipeHandler(handler);
            API.registerUsageHandler(handler);
        }
    }

    @Override
    public String getName() {
        return Tags.MOD_NAME;
    }

    @Override
    public String getVersion() {
        return Tags.MOD_VERSION;
    }
}
