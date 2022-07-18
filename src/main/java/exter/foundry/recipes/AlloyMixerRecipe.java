package exter.foundry.recipes;

import exter.foundry.api.recipe.IAlloyMixerRecipe;
import net.minecraftforge.fluids.FluidStack;

/*
 * Alloy Mixer recipe manager
 */
public class AlloyMixerRecipe implements IAlloyMixerRecipe {

    public FluidStack[] inputs;
    public FluidStack output;

    @Override
    public FluidStack GetInput(int in) {
        return inputs[in].copy();
    }

    @Override
    public int GetInputCount() {
        return inputs.length;
    }

    @Override
    public FluidStack GetOutput() {
        return output.copy();
    }

    public AlloyMixerRecipe(FluidStack out, FluidStack[] in) {
        output = out.copy();
        if (in == null) {
            throw new IllegalArgumentException("Alloy mixer recipe inputs cannot be null");
        }
        if (in.length > 4) {
            throw new IllegalArgumentException("Alloy mixer recipe cannot have more the 4 inputs");
        }
        inputs = new FluidStack[in.length];
        int i;
        for (i = 0; i < in.length; i++) {
            if (in[i] == null) {
                throw new IllegalArgumentException("Alloy mixer recipe input cannot be null");
            }
            inputs[i] = in[i].copy();
        }
    }

    private static final boolean[] matched = new boolean[4];

    @Override
    public boolean MatchesRecipe(FluidStack[] in, int[] order) {
        int matches = 0;
        int i;
        if (order != null && order.length < inputs.length) {
            order = null;
        }

        if (in.length < inputs.length) {
            return false;
        }

        for (i = 0; i < 4; i++) {
            matched[i] = false;
        }

        for (i = 0; i < in.length; i++) {
            if (in[i] != null) {
                int j;
                for (j = 0; j < inputs.length; j++) {
                    if (!matched[j] && in[i].containsFluid(inputs[j])) {
                        matched[j] = true;
                        matches++;
                        if (order != null) {
                            order[j] = i;
                        }
                        break;
                    }
                }
            }
        }
        return matches == inputs.length;
    }
}
