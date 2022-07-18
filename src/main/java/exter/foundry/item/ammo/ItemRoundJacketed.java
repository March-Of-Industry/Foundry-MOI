package exter.foundry.item.ammo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.item.firearm.ItemRevolver;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemRoundJacketed extends ItemRoundBase {
    public IIcon icon;

    public ItemRoundJacketed() {
        super(7, 100, 40);
        setUnlocalizedName("roundJacketed");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon("foundry:round_jacketed");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int dmg) {
        return icon;
    }

    @Override
    public String GetRoundType(ItemStack round) {
        return ItemRevolver.AMMO_TYPE;
    }
}
