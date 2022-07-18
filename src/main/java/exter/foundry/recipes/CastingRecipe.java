package exter.foundry.recipes;

import exter.foundry.api.FoundryUtils;
import exter.foundry.api.orestack.OreStack;
import exter.foundry.api.recipe.ICastingRecipe;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Metal Caster recipe manager
 */
public class CastingRecipe implements ICastingRecipe {
    private final FluidStack fluid;
    private final ItemStack mold;
    private final Object extra;

    private final Object output;

    private final int speed;

    @Override
    public FluidStack GetInputFluid() {
        return fluid.copy();
    }

    @Override
    public ItemStack GetInputMold() {
        return mold.copy();
    }

    @Override
    public boolean ContainsExtra(ItemStack stack) {
        if (stack == null) {
            return extra == null;
        }
        return FoundryUtils.IsItemMatch(stack, extra) && stack.stackSize >= FoundryUtils.GetStackSize(extra);
    }

    @Override
    public boolean RequiresExtra() {
        return extra != null;
    }

    @Override
    public Object GetInputExtra() {
        if (extra instanceof ItemStack) {
            return ((ItemStack) extra).copy();
        } else if (extra instanceof OreStack) {
            return new OreStack((OreStack) extra);
        }
        return extra;
    }

    @Override
    public Object GetOutput() {
        if (output instanceof ItemStack) {
            return ((ItemStack) output).copy();
        }
        return output;
    }

    @Override
    public ItemStack GetOutputItem() {
        if (output instanceof String) {
            List<ItemStack> ores = OreDictionary.getOres((String) output);
            if (ores != null && ores.size() > 0) {
                ItemStack out = ores.get(0).copy();
                out.stackSize = 1;
                return out;
            } else {
                return null;
            }
        } else // output instance of ItemStack
        {
            return ((ItemStack) output).copy();
        }
    }

    public CastingRecipe(Object result, FluidStack in_fluid, ItemStack in_mold, Object in_extra, int cast_speed) {
        if (result instanceof ItemStack) {
            output = ((ItemStack) result).copy();
        } else if (result instanceof String) {
            output = result;
        } else {
            throw new IllegalArgumentException("Casting recipe result is not of a valid class.");
        }
        if (in_fluid == null) {
            throw new IllegalArgumentException("Casting recipe fluid cannot be null.");
        }
        fluid = in_fluid.copy();
        if (in_mold == null) {
            throw new IllegalArgumentException("Casting recipe mold cannot be null.");
        }
        mold = in_mold.copy();
        if (in_extra instanceof OreStack) {
            extra = new OreStack((OreStack) in_extra);
        } else if (in_extra instanceof String) {
            extra = new OreStack((String) in_extra);
        } else if (in_extra instanceof ItemStack) {
            extra = ((ItemStack) in_extra).copy();
        } else {
            if (in_extra != null) {
                throw new IllegalArgumentException("Casting recipe extra item is not of a valid class.");
            }
            extra = null;
        }
        if (cast_speed < 1) {
            throw new IllegalArgumentException("Casting recipe speed must be > 0.");
        }
        speed = cast_speed;
    }

    @Override
    public boolean MatchesRecipe(ItemStack mold_stack, FluidStack fluid_stack, ItemStack in_extra) {
        if (GetOutputItem() == null) {
            return false;
        }
        return fluid_stack != null
                && fluid_stack.containsFluid(fluid)
                && mold_stack != null
                && mold.isItemEqual(mold_stack)
                && ItemStack.areItemStackTagsEqual(mold, mold_stack)
                && (extra == null
                        || (FoundryUtils.IsItemMatch(in_extra, extra)
                                && in_extra.stackSize >= FoundryUtils.GetStackSize(in_extra)));
    }

    @Override
    public int GetCastingSpeed() {
        return speed;
    }
}
