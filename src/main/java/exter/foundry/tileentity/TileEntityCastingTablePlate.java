package exter.foundry.tileentity;

import exter.foundry.api.FoundryAPI;
import exter.foundry.item.FoundryItems;
import exter.foundry.item.ItemMold;
import net.minecraft.item.ItemStack;

public class TileEntityCastingTablePlate extends TileEntityCastingTableBase
{
  private final ItemStack mold;
  public TileEntityCastingTablePlate()
  {
    super();    
    mold = FoundryItems.mold(ItemMold.SubItem.PLATE);
  }
  
  @Override
  public ItemStack getMold()
  {
    return mold;
  }

  @Override
  public int getFluidNeeded()
  {
    return FoundryAPI.FLUID_AMOUNT_PLATE;
  }
}
