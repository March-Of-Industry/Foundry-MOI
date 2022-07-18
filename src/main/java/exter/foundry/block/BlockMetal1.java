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

public class BlockMetal1 extends Block implements ISubBlocks {
    public static final int BLOCK_COPPER = 0;
    public static final int BLOCK_TIN = 1;
    public static final int BLOCK_BRONZE = 2;
    public static final int BLOCK_ELECTRUM = 3;
    public static final int BLOCK_INVAR = 4;
    public static final int BLOCK_NICKEL = 5;
    public static final int BLOCK_ZINC = 6;
    public static final int BLOCK_BRASS = 7;
    public static final int BLOCK_SILVER = 8;
    public static final int BLOCK_STEEL = 9;
    public static final int BLOCK_LEAD = 10;
    public static final int BLOCK_ALUMINUM = 11;
    public static final int BLOCK_CHROMIUM = 12;
    public static final int BLOCK_PLATINUM = 13;
    public static final int BLOCK_MANGANESE = 14;
    public static final int BLOCK_TITANIUM = 15;

    private static final String[] ICON_PATHS = {
        "foundry:metalblock_copper",
        "foundry:metalblock_tin",
        "foundry:metalblock_bronze",
        "foundry:metalblock_electrum",
        "foundry:metalblock_invar",
        "foundry:metalblock_nickel",
        "foundry:metalblock_zinc",
        "foundry:metalblock_brass",
        "foundry:metalblock_silver",
        "foundry:metalblock_steel",
        "foundry:metalblock_lead",
        "foundry:metalblock_aluminum",
        "foundry:metalblock_chromium",
        "foundry:metalblock_platinum",
        "foundry:metalblock_manganese",
        "foundry:metalblock_titanium"
    };

    public static final String[] METAL_NAMES = {
        "Copper",
        "Tin",
        "Bronze",
        "Electrum",
        "Invar",
        "Nickel",
        "Zinc",
        "Brass",
        "Silver",
        "Steel",
        "Lead",
        "Aluminum",
        "Chromium",
        "Platinum",
        "Manganese",
        "Titanium"
    };

    public static final String[] OREDICT_NAMES = {
        "blockCopper",
        "blockTin",
        "blockBronze",
        "blockElectrum",
        "blockInvar",
        "blockNickel",
        "blockZinc",
        "blockBrass",
        "blockSilver",
        "blockSteel",
        "blockLead",
        "blockAluminum",
        "blockChromium",
        "blockPlatinum",
        "blockManganese",
        "blockTitanium"
    };

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public BlockMetal1() {
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
