package exter.foundry.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.ModFoundry;
import exter.foundry.creativetab.FoundryTabMachines;
import exter.foundry.proxy.CommonFoundryProxy;
import exter.foundry.tileentity.TileEntityFoundry;
import exter.foundry.tileentity.TileEntityRefractoryHopper;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRefractoryHopper extends BlockContainer {
    @SideOnly(Side.CLIENT)
    public IIcon icon_outside;

    @SideOnly(Side.CLIENT)
    public IIcon icon_outside_bottom;

    @SideOnly(Side.CLIENT)
    public IIcon icon_top;

    @SideOnly(Side.CLIENT)
    public IIcon icon_inside;

    private Random rand = new Random();

    public BlockRefractoryHopper() {
        super(Material.iron);
        setCreativeTab(FoundryTabMachines.tab);
        setHardness(1.0F);
        setResistance(8.0F);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        setBlockName("refractoryHopper");
    }

    @Override
    public void setBlockBoundsBasedOnState(
            IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void addCollisionBoxesToList(
            World world,
            int x,
            int y,
            int z,
            AxisAlignedBB bb,
            @SuppressWarnings("rawtypes") List blist,
            Entity entity) {
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, bb, blist, entity);
        float f = 0.125F;
        setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, bb, blist, entity);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(world, x, y, z, bb, blist, entity);
        setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, bb, blist, entity);
        setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, bb, blist, entity);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitx, float hity, float hitz, int meta) {
        int facing = Facing.oppositeSide[side];
        if (facing == 1) {
            facing = 0;
        }
        return facing;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityRefractoryHopper();
    }

    @Override
    public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6) {
        super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
        TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
        return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        TileEntityFoundry te = (TileEntityFoundry) world.getTileEntity(x, y, z);

        if (te != null) {
            te.UpdateRedstone();
        }
    }

    @Override
    public boolean onBlockActivated(
            World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz) {
        if (world.isRemote) {
            return true;
        } else {
            player.openGui(ModFoundry.instance, CommonFoundryProxy.GUI_REFRACTORYHOPPER, world, x, y, z);
            return true;
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int par6) {
        TileEntity te = world.getTileEntity(x, y, z);

        if (te != null && (te instanceof TileEntityFoundry) && !world.isRemote) {
            world.func_147453_f(x, y, z, block);
            TileEntityFoundry tef = (TileEntityFoundry) te;
            int i;
            for (i = 0; i < tef.getSizeInventory(); i++) {
                ItemStack is = tef.getStackInSlot(i);

                if (is != null && is.stackSize > 0) {
                    double drop_x = (rand.nextFloat() * 0.3) + 0.35;
                    double drop_y = (rand.nextFloat() * 0.3) + 0.35;
                    double drop_z = (rand.nextFloat() * 0.3) + 0.35;
                    EntityItem entityitem = new EntityItem(world, x + drop_x, y + drop_y, z + drop_z, is);
                    entityitem.delayBeforeCanPickup = 10;

                    world.spawnEntityInWorld(entityitem);
                }
            }
        }
        world.removeTileEntity(x, y, z);
        super.breakBlock(world, x, y, z, block, par6);
    }

    @Override
    public int getRenderType() {
        return CommonFoundryProxy.hopper_renderer_id;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(
            IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        switch (side) {
            case 0:
                return icon_outside_bottom;
            case 1:
                return icon_top;
            default:
                return icon_outside;
        }
    }

    public static int GetDirection(int meta) {
        return meta & 7;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister registry) {
        icon_outside = registry.registerIcon("foundry:refhopper_outside");
        icon_outside_bottom = registry.registerIcon("foundry:refhopper_outside_bottom");
        icon_top = registry.registerIcon("foundry:refhopper_top");
        icon_inside = registry.registerIcon("foundry:refhopper_inside");
    }
}
