package exter.foundry.container;

import exter.foundry.container.slot.SlotContainer;
import exter.foundry.tileentity.TileEntityRefractoryHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRefractoryHopper extends Container {
    private TileEntityRefractoryHopper te_hopper;

    // Slot numbers
    private static final int SLOTS_TE = 0;
    private static final int SLOTS_TE_SIZE = 2;

    private static final int SLOTS_INVENTORY = 2;
    private static final int SLOTS_HOTBAR = 2 + 3 * 9;

    private static final int SLOT_INVENTORY_X = 8;
    private static final int SLOT_INVENTORY_Y = 83;

    private static final int SLOT_HOTBAR_X = 8;
    private static final int SLOT_HOTBAR_Y = 141;

    public ContainerRefractoryHopper(TileEntityRefractoryHopper hopper, IInventory player_inventory) {
        te_hopper = hopper;
        te_hopper.openInventory();
        int i, j;

        addSlotToContainer(new SlotContainer(te_hopper, TileEntityRefractoryHopper.INVENTORY_CONTAINER_DRAIN, 94, 20));
        addSlotToContainer(new SlotContainer(te_hopper, TileEntityRefractoryHopper.INVENTORY_CONTAINER_FILL, 94, 48));

        // Player Inventory
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                addSlotToContainer(new Slot(
                        player_inventory, j + i * 9 + 9, SLOT_INVENTORY_X + j * 18, SLOT_INVENTORY_Y + i * 18));
            }
        }
        for (i = 0; i < 9; ++i) {
            addSlotToContainer(new Slot(player_inventory, i, SLOT_HOTBAR_X + i * 18, SLOT_HOTBAR_Y));
        }
    }

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return te_hopper.isUseableByPlayer(par1EntityPlayer);
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slot_index) {
        ItemStack slot_stack = null;
        Slot slot = (Slot) inventorySlots.get(slot_index);

        if (slot != null && slot.getHasStack()) {
            ItemStack stack = slot.getStack();
            slot_stack = stack.copy();

            if (slot_index >= SLOTS_INVENTORY && slot_index < SLOTS_HOTBAR) {
                if (!mergeItemStack(stack, SLOTS_TE, SLOTS_TE + SLOTS_TE_SIZE, false)) {
                    return null;
                }
            } else if (slot_index >= SLOTS_HOTBAR && slot_index < SLOTS_HOTBAR + 9) {
                if (!mergeItemStack(stack, SLOTS_INVENTORY, SLOTS_INVENTORY + 3 * 9, false)) {
                    return null;
                }
            } else if (!mergeItemStack(stack, SLOTS_INVENTORY, SLOTS_HOTBAR + 9, false)) {
                return null;
            }

            if (stack.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (stack.stackSize == slot_stack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(player, stack);
        }

        return slot_stack;
    }

    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        te_hopper.closeInventory();
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (int i = 0; i < crafters.size(); i++) {
            te_hopper.SendGUINetworkData(this, (ICrafting) crafters.get(i));
        }
    }

    @Override
    public void updateProgressBar(int i, int j) {
        te_hopper.GetGUINetworkData(i, j);
    }
}
