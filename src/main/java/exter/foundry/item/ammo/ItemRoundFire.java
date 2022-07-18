package exter.foundry.item.ammo;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.creativetab.FoundryTabFirearms;
import exter.foundry.item.firearm.ItemRevolver;
import java.util.List;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemRoundFire extends ItemRoundBase {
    public IIcon icon;

    public ItemRoundFire() {
        super(8, 50, 25);
        setCreativeTab(FoundryTabFirearms.tab);
        setUnlocalizedName("roundFire");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icon = register.registerIcon("foundry:round_fire");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int dmg) {
        return icon;
    }

    @Override
    public void OnBulletHitBlock(
            ItemStack ammo,
            EntityLivingBase shooter,
            Vec3 from,
            World world,
            int x,
            int y,
            int z,
            ForgeDirection side) {
        int xx = x + side.offsetX;
        int yy = y + side.offsetY;
        int zz = z + side.offsetZ;
        if (world.isAirBlock(xx, yy, zz) && !world.isAirBlock(x, y, z)) {
            world.setBlock(xx, yy, zz, Blocks.fire);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(
            ItemStack stack, EntityPlayer player, @SuppressWarnings("rawtypes") List list, boolean par4) {
        super.addInformation(stack, player, list, par4);
        if (GuiScreen.isShiftKeyDown()) {
            list.add(EnumChatFormatting.YELLOW + "Sets target on fire.");
        }
    }

    @Override
    public void OnBulletDamagedLivingEntity(ItemStack round, EntityLivingBase entity, int count) {
        if (!entity.isImmuneToFire()) {
            entity.setFire(5);
        }
    }

    @Override
    public String GetRoundType(ItemStack round) {
        return ItemRevolver.AMMO_TYPE;
    }
}
