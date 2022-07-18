package exter.foundry.api;

import exter.foundry.api.recipe.manager.IAlloyFurnaceRecipeManager;
import exter.foundry.api.recipe.manager.IAlloyMixerRecipeManager;
import exter.foundry.api.recipe.manager.IAtomizerRecipeManager;
import exter.foundry.api.recipe.manager.ICastingRecipeManager;
import exter.foundry.api.recipe.manager.IInfuserRecipeManager;
import exter.foundry.api.recipe.manager.IMeltingRecipeManager;
import exter.foundry.api.registry.IFluidRegistry;
import exter.foundry.api.registry.IItemRegistry;

/**
 * API for recipes of Foundry machines.
 */
public class FoundryAPI {
    public static final int FLUID_AMOUNT_BLOCK = 1296;
    public static final int FLUID_AMOUNT_INGOT = 144;
    public static final int FLUID_AMOUNT_NUGGET = 16;
    public static final int FLUID_AMOUNT_ORE = 288;

    /**
     * Maximum amount of substance a metal infuser can store.
     */
    public static final int INFUSER_SUBSTANCE_AMOUNT_MAX = 1000;

    /**
     * Tank capacity for machines.
     */
    public static final int ICF_TANK_CAPACITY = 9216;

    public static final int CASTER_TANK_CAPACITY = 9216;
    public static final int INFUSER_TANK_CAPACITY = 9216;
    public static final int ALLOYMIXER_TANK_CAPACITY = 4608;
    public static final int ATOMIZER_TANK_CAPACITY = 9216;
    public static final int ATOMIZER_WATER_TANK_CAPACITY = 16000;

    // These fields are set by Foundry during it's preInit phase.
    // If foundry is not installed they become null.
    public static IMeltingRecipeManager recipes_melting;
    public static ICastingRecipeManager recipes_casting;
    public static IAlloyMixerRecipeManager recipes_alloymixer;
    public static IInfuserRecipeManager recipes_infuser;
    public static IAlloyFurnaceRecipeManager recipes_alloyfurnace;
    public static IAtomizerRecipeManager recipes_atomizer;

    @Deprecated
    public static IItemRegistry items;

    public static IFluidRegistry fluids;
}
