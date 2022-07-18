package exter.foundry.api.recipe.manager;

import exter.foundry.api.recipe.IAlloyMixerRecipe;
import java.util.List;
import net.minecraftforge.fluids.FluidStack;

public interface IAlloyMixerRecipeManager {
    /**
     * Register an Alloy Mixer recipe.
     * @param out Output (fluid type and amount).
     * @param in Inputs (fluid type and amount required), length must be less or equal 4.
     */
    public void AddRecipe(FluidStack out, FluidStack[] in);

    /**
     * Get a list of all the recipes
     * @return List of all the recipes
     */
    public List<IAlloyMixerRecipe> GetRecipes();

    /**
     * Find a valid recipe that contains the given inputs.
     * A recipe is found if the recipe's inputs contains the fluid in the parameters.
     * @param in_a FluidStack for the first input.
     * @param in_b FluidStack for the second input.
     * @param order [Output] Order in which the input fluids are matched.
     * @return
     */
    public IAlloyMixerRecipe FindRecipe(FluidStack[] in, int[] order);

    /**
     * Removes a recipe.
     * @param The recipe to remove.
     */
    public void RemoveRecipe(IAlloyMixerRecipe recipe);
}
