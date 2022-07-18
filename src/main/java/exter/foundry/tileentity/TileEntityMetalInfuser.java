package exter.foundry.tileentity;

import exter.foundry.api.FoundryAPI;
import exter.foundry.api.recipe.IInfuserRecipe;
import exter.foundry.api.recipe.IInfuserSubstanceRecipe;
import exter.foundry.api.substance.InfuserSubstance;
import exter.foundry.container.ContainerMetalInfuser;
import exter.foundry.recipes.manager.InfuserRecipeManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityMetalInfuser extends TileEntityFoundryPowered implements ISidedInventory, IFluidHandler {
    private static final int NETDATAID_INPUT_TANK_FLUID = 1;
    private static final int NETDATAID_INPUT_TANK_AMOUNT = 2;

    private static final int NETDATAID_OUTPUT_TANK_FLUID = 3;
    private static final int NETDATAID_OUTPUT_TANK_AMOUNT = 4;

    public static final int INVENTORY_SUBSTANCE_INPUT = 0;
    public static final int INVENTORY_CONTAINER_INPUT_DRAIN = 1;
    public static final int INVENTORY_CONTAINER_INPUT_FILL = 2;
    public static final int INVENTORY_CONTAINER_OUTPUT_DRAIN = 3;
    public static final int INVENTORY_CONTAINER_OUTPUT_FILL = 4;
    private ItemStack[] inventory;

    public static final int TANK_INPUT = 0;
    public static final int TANK_OUTPUT = 1;
    private FluidTank[] tanks;
    private FluidTankInfo[] tank_info;

    private InfuserSubstance substance;

    private static final int INFUSE_ENERGY_NEEDED = 100;

    private int progress;
    private int extract_energy;

    private IInfuserSubstanceRecipe current_substance_recipe;

    public TileEntityMetalInfuser() {
        super();

        int i;
        tanks = new FluidTank[2];
        tank_info = new FluidTankInfo[2];
        for (i = 0; i < 2; i++) {
            tanks[i] = new FluidTank(FoundryAPI.INFUSER_TANK_CAPACITY);
            tank_info[i] = new FluidTankInfo(tanks[i]);
        }
        progress = 0;
        extract_energy = 1;

        inventory = new ItemStack[5];

        current_substance_recipe = null;

        AddContainerSlot(new ContainerSlot(TANK_INPUT, INVENTORY_CONTAINER_INPUT_DRAIN, false));
        AddContainerSlot(new ContainerSlot(TANK_INPUT, INVENTORY_CONTAINER_INPUT_FILL, true));
        AddContainerSlot(new ContainerSlot(TANK_OUTPUT, INVENTORY_CONTAINER_OUTPUT_DRAIN, false));
        AddContainerSlot(new ContainerSlot(TANK_OUTPUT, INVENTORY_CONTAINER_OUTPUT_FILL, true));
    }

    @Override
    public void readFromNBT(NBTTagCompound compund) {
        super.readFromNBT(compund);

        if (compund.hasKey("progress")) {
            progress = compund.getInteger("progress");
        }

        if (compund.hasKey("extract_time")) {
            extract_energy = compund.getInteger("extract_time");
        }

        NBTTagCompound substance_tag = (NBTTagCompound) compund.getTag("Substance");
        if (substance_tag != null) {
            if (substance_tag.getBoolean("empty")) {
                substance = null;
            } else {
                substance = InfuserSubstance.ReadFromNBT(substance_tag);
            }
        }
    }

    private void WriteSubstanceToNBT(NBTTagCompound compound) {
        if (substance != null) {
            compound.setBoolean("empty", false);
            substance.WriteToNBT(compound);
        } else {
            compound.setBoolean("empty", true);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagCompound substance_tag = new NBTTagCompound();
        WriteSubstanceToNBT(substance_tag);
        compound.setTag("Substance", substance_tag);
        compound.setInteger("progress", progress);
        compound.setInteger("extract_time", extract_energy);
    }

    public void GetGUINetworkData(int id, int value) {
        switch (id) {
            case NETDATAID_INPUT_TANK_FLUID:
                SetTankFluid(tanks[TANK_INPUT], value);
                break;
            case NETDATAID_INPUT_TANK_AMOUNT:
                SetTankAmount(tanks[TANK_INPUT], value);
                break;
            case NETDATAID_OUTPUT_TANK_FLUID:
                SetTankFluid(tanks[TANK_OUTPUT], value);
                break;
            case NETDATAID_OUTPUT_TANK_AMOUNT:
                SetTankAmount(tanks[TANK_OUTPUT], value);
                break;
        }
    }

    public void SendGUINetworkData(ContainerMetalInfuser container, ICrafting crafting) {
        crafting.sendProgressBarUpdate(
                container,
                NETDATAID_INPUT_TANK_FLUID,
                tanks[TANK_INPUT].getFluid() != null
                        ? tanks[TANK_INPUT].getFluid().getFluidID()
                        : 0);
        crafting.sendProgressBarUpdate(
                container,
                NETDATAID_INPUT_TANK_AMOUNT,
                tanks[TANK_INPUT].getFluid() != null ? tanks[TANK_INPUT].getFluid().amount : 0);
        crafting.sendProgressBarUpdate(
                container,
                NETDATAID_OUTPUT_TANK_FLUID,
                tanks[TANK_OUTPUT].getFluid() != null
                        ? tanks[TANK_OUTPUT].getFluid().getFluidID()
                        : 0);
        crafting.sendProgressBarUpdate(
                container,
                NETDATAID_OUTPUT_TANK_AMOUNT,
                tanks[TANK_OUTPUT].getFluid() != null ? tanks[TANK_OUTPUT].getFluid().amount : 0);
    }

    @Override
    public int getSizeInventory() {
        return 5;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (inventory[slot] != null) {
            ItemStack is;

            if (inventory[slot].stackSize <= amount) {
                is = inventory[slot];
                inventory[slot] = null;
                markDirty();
                return is;
            } else {
                is = inventory[slot].splitStack(amount);

                if (inventory[slot].stackSize == 0) {
                    inventory[slot] = null;
                }

                markDirty();
                return is;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (inventory[slot] != null) {
            ItemStack is = inventory[slot];
            inventory[slot] = null;
            return is;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        inventory[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit()) {
            stack.stackSize = this.getInventoryStackLimit();
        }

        markDirty();
    }

    @Override
    public String getInventoryName() {
        return "Infuser";
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) != this
                ? false
                : player.getDistanceSq((double) xCoord + 0.5D, (double) yCoord + 0.5D, (double) zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {}

    @Override
    public void closeInventory() {}

    public int GetProgress() {
        return progress;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    private static final int[] INSERT_SLOTS = {0};
    private static final int[] EXTRACT_SLOTS = {0};

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemstack) {
        return slot == 0;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return side == 1 ? INSERT_SLOTS : EXTRACT_SLOTS;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemstack, int side) {
        return isItemValidForSlot(slot, itemstack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemstack, int side) {
        return slot == 0;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return tanks[TANK_INPUT].fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource.isFluidEqual(tanks[TANK_OUTPUT].getFluid())) {
            return tanks[TANK_OUTPUT].drain(resource.amount, doDrain);
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tanks[TANK_OUTPUT].drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return tank_info;
    }

    @Override
    protected void UpdateEntityClient() {}

    private void CheckCurrentSubstanceRecipe() {
        if (current_substance_recipe == null) {
            progress = 0;
            extract_energy = 1;
            return;
        }

        if (!current_substance_recipe.MatchesRecipe(inventory[INVENTORY_SUBSTANCE_INPUT])) {
            progress = 0;
            extract_energy = 1;
            current_substance_recipe = null;
            return;
        }
    }

    private void DoSubstanceExtraction() {
        if (current_substance_recipe == null) {
            progress = 0;
            extract_energy = 1;
            return;
        }

        InfuserSubstance recipe_sub = current_substance_recipe.GetOutputSubstance();
        if (substance != null
                && (!recipe_sub.IsSubstanceEqual(substance)
                        || FoundryAPI.INFUSER_SUBSTANCE_AMOUNT_MAX - substance.amount < recipe_sub.amount)) {
            progress = 0;
            extract_energy = 1;
            return;
        }
        extract_energy = current_substance_recipe.GetEnergyNeeded();
        if (GetStoredEnergy() > 0) {
            int energy = UseEnergy(600, true);
            progress += energy;
            if (progress >= extract_energy) {
                progress -= extract_energy;
                if (substance == null) {
                    substance = new InfuserSubstance(recipe_sub);
                } else {
                    substance.amount += recipe_sub.amount;
                }
                decrStackSize(0, 1);
                NBTTagCompound tag = new NBTTagCompound();
                WriteSubstanceToNBT(tag);
                UpdateNBTTag("Substance", tag);
                UpdateInventoryItem(0);
            }
        }
    }

    @Override
    protected void UpdateEntityServer() {
        super.UpdateEntityServer();
        int last_progress = progress;
        int last_extract_time = extract_energy;

        if (tanks[TANK_INPUT].getFluidAmount() > 0 && GetStoredEnergy() >= INFUSE_ENERGY_NEEDED) {
            IInfuserRecipe recipe = InfuserRecipeManager.instance.FindRecipe(tanks[TANK_INPUT].getFluid(), substance);
            if (recipe != null) {
                FluidStack result = recipe.GetOutput();
                if (tanks[TANK_OUTPUT].fill(result, false) == result.amount) {
                    tanks[TANK_INPUT].drain(recipe.GetInputFluid().amount, true);
                    tanks[TANK_OUTPUT].fill(result, true);
                    UseEnergy(INFUSE_ENERGY_NEEDED, true);
                    substance.amount -= recipe.GetInputSubstance().amount;
                    if (substance.amount <= 0) {
                        substance = null;
                    }
                    NBTTagCompound tag = new NBTTagCompound();
                    WriteSubstanceToNBT(tag);
                    UpdateNBTTag("Substance", tag);

                    UpdateTank(TANK_INPUT);
                    UpdateTank(TANK_OUTPUT);
                }
            }
        }

        CheckCurrentSubstanceRecipe();

        if (current_substance_recipe == null) {
            current_substance_recipe =
                    InfuserRecipeManager.instance.FindSubstanceRecipe(inventory[INVENTORY_SUBSTANCE_INPUT]);
            progress = 0;
        }

        DoSubstanceExtraction();

        if (last_extract_time != extract_energy) {
            UpdateValue("extract_time", extract_energy);
        }

        if (last_progress != progress) {
            UpdateValue("progress", progress);
        }
    }

    public int GetExtractTime() {
        return extract_energy;
    }

    public InfuserSubstance GetSubstance() {
        return substance;
    }

    @Override
    public FluidTank GetTank(int slot) {
        return tanks[slot];
    }

    @Override
    public int GetTankCount() {
        return 2;
    }

    @Override
    public int GetEnergyCapacity() {
        return 3000;
    }
}
