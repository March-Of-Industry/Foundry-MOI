package exter.foundry.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.creativetab.FoundryTabMolds;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemMold extends Item {
    public static final int MOLD_INGOT = 0;
    public static final int MOLD_INGOT_SOFT = 1;
    public static final int MOLD_CHESTPLATE = 2;
    public static final int MOLD_CHESTPLATE_SOFT = 3;
    public static final int MOLD_PICKAXE = 4;
    public static final int MOLD_PICKAXE_SOFT = 5;
    public static final int MOLD_BLOCK = 6;
    public static final int MOLD_BLOCK_SOFT = 7;
    public static final int MOLD_AXE = 8;
    public static final int MOLD_AXE_SOFT = 9;
    public static final int MOLD_SWORD = 10;
    public static final int MOLD_SWORD_SOFT = 11;
    public static final int MOLD_SHOVEL = 12;
    public static final int MOLD_SHOVEL_SOFT = 13;
    public static final int MOLD_HOE = 14;
    public static final int MOLD_HOE_SOFT = 15;
    public static final int MOLD_LEGGINGS = 16;
    public static final int MOLD_LEGGINGS_SOFT = 17;
    public static final int MOLD_HELMET = 18;
    public static final int MOLD_HELMET_SOFT = 19;
    public static final int MOLD_BOOTS = 20;
    public static final int MOLD_BOOTS_SOFT = 21;
    public static final int MOLD_GEAR = 22;
    public static final int MOLD_GEAR_SOFT = 23;
    public static final int MOLD_CABLE_IC2 = 24;
    public static final int MOLD_CABLE_IC2_SOFT = 25;
    public static final int MOLD_CASING_IC2 = 26;
    public static final int MOLD_CASING_IC2_SOFT = 27;
    public static final int MOLD_SLAB = 28;
    public static final int MOLD_SLAB_SOFT = 29;
    public static final int MOLD_STAIRS = 30;
    public static final int MOLD_STAIRS_SOFT = 31;
    public static final int MOLD_PLATE = 32;
    public static final int MOLD_PLATE_SOFT = 33;
    public static final int MOLD_CAP_TC = 34;
    public static final int MOLD_CAP_TC_SOFT = 35;
    public static final int MOLD_INSULATED_CABLE_IC2 = 36;
    public static final int MOLD_INSULATED_CABLE_IC2_SOFT = 37;
    public static final int MOLD_SICKLE = 38;
    public static final int MOLD_SICKLE_SOFT = 39;
    public static final int MOLD_BOW = 40;
    public static final int MOLD_BOW_SOFT = 41;
    public static final int MOLD_FLUXPLATE = 42;
    public static final int MOLD_FLUXPLATE_SOFT = 43;
    public static final int MOLD_BULLET = 44;
    public static final int MOLD_BULLET_SOFT = 45;
    public static final int MOLD_BULLET_HOLLOW = 46;
    public static final int MOLD_BULLET_HOLLOW_SOFT = 47;
    public static final int MOLD_BULLET_CASING = 48;
    public static final int MOLD_BULLET_CASING_SOFT = 49;
    public static final int MOLD_GUN_BARREL = 50;
    public static final int MOLD_GUN_BARREL_SOFT = 51;
    public static final int MOLD_REVOLVER_DRUM = 52;
    public static final int MOLD_REVOLVER_DRUM_SOFT = 53;
    public static final int MOLD_REVOLVER_FRAME = 54;
    public static final int MOLD_REVOLVER_FRAME_SOFT = 55;
    public static final int MOLD_WIRE_PR = 56;
    public static final int MOLD_WIRE_PR_SOFT = 57;
    public static final int MOLD_PELLET = 58;
    public static final int MOLD_PELLET_SOFT = 59;
    public static final int MOLD_SHELL_CASING = 60;
    public static final int MOLD_SHELL_CASING_SOFT = 61;
    public static final int MOLD_SHOTGUN_PUMP = 62;
    public static final int MOLD_SHOTGUN_PUMP_SOFT = 63;
    public static final int MOLD_SHOTGUN_FRAME = 64;
    public static final int MOLD_SHOTGUN_FRAME_SOFT = 65;
    public static final int MOLD_SHARD_TC = 66;
    public static final int MOLD_SHARD_TC_SOFT = 67;

    private static final String[] ICON_PATHS = {
        "foundry:mold_ingot",
        "foundry:claymold_ingot",
        "foundry:mold_chestplate",
        "foundry:claymold_chestplate",
        "foundry:mold_pickaxe",
        "foundry:claymold_pickaxe",
        "foundry:mold_block",
        "foundry:claymold_block",
        "foundry:mold_axe",
        "foundry:claymold_axe",
        "foundry:mold_sword",
        "foundry:claymold_sword",
        "foundry:mold_shovel",
        "foundry:claymold_shovel",
        "foundry:mold_hoe",
        "foundry:claymold_hoe",
        "foundry:mold_leggings",
        "foundry:claymold_leggings",
        "foundry:mold_helmet",
        "foundry:claymold_helmet",
        "foundry:mold_boots",
        "foundry:claymold_boots",
        "foundry:mold_gear",
        "foundry:claymold_gear",
        "foundry:mold_cable_ic2",
        "foundry:claymold_cable_ic2",
        "foundry:mold_casing_ic2",
        "foundry:claymold_casing_ic2",
        "foundry:mold_slab",
        "foundry:claymold_slab",
        "foundry:mold_stairs",
        "foundry:claymold_stairs",
        "foundry:mold_plate_ic2",
        "foundry:claymold_plate_ic2",
        "foundry:mold_cap_tc",
        "foundry:claymold_cap_tc",
        "foundry:mold_cable_insulated_ic2",
        "foundry:claymold_cable_insulated_ic2",
        "foundry:mold_sickle",
        "foundry:claymold_sickle",
        "foundry:mold_bow",
        "foundry:claymold_bow",
        "foundry:mold_fluxplate",
        "foundry:claymold_fluxplate",
        "foundry:mold_bullet",
        "foundry:claymold_bullet",
        "foundry:mold_bulletHollow",
        "foundry:claymold_bulletHollow",
        "foundry:mold_bulletCasing",
        "foundry:claymold_bulletCasing",
        "foundry:mold_gunBarrel",
        "foundry:claymold_gunBarrel",
        "foundry:mold_revolverDrum",
        "foundry:claymold_revolverDrum",
        "foundry:mold_revolverFrame",
        "foundry:claymold_revolverFrame",
        "foundry:mold_wire_pr",
        "foundry:claymold_wire_pr",
        "foundry:mold_pellet",
        "foundry:claymold_pellet",
        "foundry:mold_shellCasing",
        "foundry:claymold_shellCasing",
        "foundry:mold_shotgunPump",
        "foundry:claymold_shotgunPump",
        "foundry:mold_shotgunFrame",
        "foundry:claymold_shotgunFrame",
        "foundry:mold_shard_tc",
        "foundry:claymold_shard_tc"
    };

    public static final String[] REGISTRY_NAMES = {
        "moldIngot",
        "moldSoftIngot",
        "moldChestplate",
        "moldSoftChestplate",
        "moldPickaxe",
        "moldSoftPickaxe",
        "moldBlock",
        "moldSoftBlock",
        "moldAxe",
        "moldSoftAxe",
        "moldSword",
        "moldSoftSword",
        "moldShovel",
        "moldSoftShovel",
        "moldHoe",
        "moldSoftHoe",
        "moldLeggings",
        "moldSoftLeggings",
        "moldHelmet",
        "moldSoftHelmet",
        "moldBoots",
        "moldSoftBoots",
        "moldGear",
        "moldSoftGear",
        "moldCable",
        "moldSoftCable",
        "moldCasing",
        "moldSoftCasing",
        "moldSlab",
        "moldSoftSlab",
        "moldStairs",
        "moldSoftStairs",
        "moldPlate",
        "moldSoftPlate",
        "moldCap",
        "moldSoftCap",
        "moldInsulatedCable",
        "moldSoftInsulatedCable",
        "moldSickle",
        "moldSoftSickle",
        "moldBow",
        "moldSoftBow",
        "moldFluxPlate",
        "moldSoftFluxPlate",
        "moldBullet",
        "moldSoftBullet",
        "moldBulletHollow",
        "moldSoftBulletHollow",
        "moldBulletCasing",
        "moldSoftBulletCasing",
        "moldGunBarrel",
        "moldSoftGunBarrel",
        "moldRevolverDrum",
        "moldSoftRevolverDrum",
        "moldRevolverFrame",
        "moldSoftRevolverFrame",
        "moldWire",
        "moldSoftWire",
        "moldPellet",
        "moldSoftPellet",
        "moldShellCasing",
        "moldSoftShellCasing",
        "moldShotgunPump",
        "moldSoftShotgunPump",
        "moldShotgunFrame",
        "moldSoftShotgunFrame",
        "moldShard",
        "moldSoftShard"
    };

    @Deprecated
    public static final String[] REGISTRY_NAMES_LEGACY = {
        "itemIngotMold",
        "itemSoftIngotMold",
        "itemChestplateMold",
        "itemSoftChestplateMold",
        "itemPickaxeMold",
        "itemSoftPickaxeMold",
        "itemBlockMold",
        "itemSoftBlockMold",
        "itemAxeMold",
        "itemSoftAxeMold",
        "itemSwordMold",
        "itemSoftSwordMold",
        "itemShovelMold",
        "itemSoftShovelMold",
        "itemHoeMold",
        "itemSoftHoeMold",
        "itemLeggingsMold",
        "itemSoftLeggingsMold",
        "itemHelmetMold",
        "itemSoftHelmetMold",
        "itemBootsMold",
        "itemSoftBootsMold",
        "itemGearMold",
        "itemSoftGearMold",
        "itemCableMold",
        "itemSoftCableMold",
        "itemCasingMold",
        "itemSoftCasingMold",
        "itemSlabMold",
        "itemSoftSlabMold",
        "itemStairsMold",
        "itemSoftStairsMold",
        "itemPlateMold",
        "itemSoftPlateMold",
        "itemCapMold",
        "itemSoftCapMold",
        "itemInsulatedCableMold",
        "itemSoftInsulatedCableMold",
        "itemSickleMold",
        "itemSoftSickleMold",
        "itemBowMold",
        "itemSoftBowMold",
        "itemFluxPlateMold",
        "itemSoftFluxPlateMold",
        "ItemBulletMold",
        "ItemSoftBulletMold",
        "ItemBulletHollowMold",
        "ItemSoftBulletHollowMold",
        "ItemBulletCasingMold",
        "ItemSoftBulletCasingMold",
        "ItemGunBarrelMold",
        "ItemSoftGunBarrelMold",
        "ItemRevolverDrumMold",
        "ItemSoftRevolverDrumMold",
        "ItemRevolverFrameMold",
        "ItemSoftRevolverFrameMold",
        "itemWireMold",
        "itemSoftWireMold",
        "itemPelletMold",
        "itemSoftPelletMold",
        "ItemShellCasingMold",
        "ItemSoftShellCasingMold",
        "ItemShotgunPumpMold",
        "ItemSoftShotgunPumpMold",
        "ItemShotgunFrameMold",
        "ItemSoftShotgunFrameMold",
        "ItemShardMold",
        "ItemSoftShardMold"
    };

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemMold() {
        super();
        maxStackSize = 1;
        setCreativeTab(FoundryTabMolds.tab);
        setHasSubtypes(true);
        setUnlocalizedName("mold");
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
