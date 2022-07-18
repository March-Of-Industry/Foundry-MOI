package exter.foundry.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.creativetab.FoundryTabMaterials;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemIngot extends Item {
    public static final int INGOT_COPPER = 0;
    public static final int INGOT_TIN = 1;
    public static final int INGOT_BRONZE = 2;
    public static final int INGOT_ELECTRUM = 3;
    public static final int INGOT_INVAR = 4;
    public static final int INGOT_NICKEL = 5;
    public static final int INGOT_ZINC = 6;
    public static final int INGOT_BRASS = 7;
    public static final int INGOT_SILVER = 8;
    public static final int INGOT_STEEL = 9;
    public static final int INGOT_LEAD = 10;
    public static final int INGOT_ALUMINUM = 11;
    public static final int INGOT_CHROMIUM = 12;
    public static final int INGOT_PLATINUM = 13;
    public static final int INGOT_MANGANESE = 14;
    public static final int INGOT_TITANIUM = 15;
    public static final int INGOT_CUPRONICKEL = 16;

    private static final String[] ICON_PATHS = {
        "foundry:ingot_copper",
        "foundry:ingot_tin",
        "foundry:ingot_bronze",
        "foundry:ingot_electrum",
        "foundry:ingot_invar",
        "foundry:ingot_nickel",
        "foundry:ingot_zinc",
        "foundry:ingot_brass",
        "foundry:ingot_silver",
        "foundry:ingot_steel",
        "foundry:ingot_lead",
        "foundry:ingot_aluminum",
        "foundry:ingot_chromium",
        "foundry:ingot_platinum",
        "foundry:ingot_manganese",
        "foundry:ingot_titanium",
        "foundry:ingot_cupronickel"
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
        "Titanium",
        "Cupronickel"
    };

    public static final String[] OREDICT_NAMES = {
        "ingotCopper",
        "ingotTin",
        "ingotBronze",
        "ingotElectrum",
        "ingotInvar",
        "ingotNickel",
        "ingotZinc",
        "ingotBrass",
        "ingotSilver",
        "ingotSteel",
        "ingotLead",
        "ingotAluminum",
        "ingotChromium",
        "ingotPlatinum",
        "ingotManganese",
        "ingotTitanium",
        "ingotCupronickel"
    };

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemIngot() {
        super();
        setCreativeTab(FoundryTabMaterials.tab);
        setHasSubtypes(true);
        setUnlocalizedName("ingot");
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        return getUnlocalizedName() + itemstack.getItemDamage();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        icons = new IIcon[ICON_PATHS.length];

        int i;
        for (i = 0; i < icons.length; i++) {
            icons[i] = register.registerIcon(ICON_PATHS[i]);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int dmg) {
        return icons[dmg];
    }

    @SuppressWarnings("unchecked")
    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tabs, @SuppressWarnings("rawtypes") List list) {
        int i;
        for (i = 0; i < ICON_PATHS.length; i++) {
            ItemStack itemstack = new ItemStack(this, 1, i);
            list.add(itemstack);
        }
    }
}
