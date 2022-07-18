package exter.foundry.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.creativetab.FoundryTabBlocks;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class BlockMetal2 extends Block implements ISubBlocks {
    public static final int BLOCK_CUPRONICKEL = 0;

    private static final String[] ICON_PATHS = {"foundry:metalblock_cupronickel"};

    public static final String[] METAL_NAMES = {"Cupronickel"};

    public static final String[] OREDICT_NAMES = {"blockCupronickel"};

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockMetal2() {
        super(Material.iron);
        setHardness(1.0F);
        setResistance(8.0F);
        setBlockName("metalBlock");
        setStepSound(Block.soundTypeMetal);
        setCreativeTab(FoundryTabBlocks.tab);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register) {
        icons = new IIcon[ICON_PATHS.length];

        int i;
        for (i = 0; i < icons.length; i++) {
            icons[i] = register.registerIcon(ICON_PATHS[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[meta];
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, @SuppressWarnings("rawtypes") List list) {
        int i;
        for (i = 0; i < ICON_PATHS.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public String[] GetSubNames() {
        return METAL_NAMES;
    }
}
