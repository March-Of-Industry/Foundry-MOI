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

public class ItemComponent extends Item {
    public static final int COMPONENT_GEAR = 0;
    public static final int COMPONENT_HEATINGCOIL = 1;
    public static final int COMPONENT_REFRACTORYCLAY = 2;
    public static final int COMPONENT_REFRACTORYBRICK = 3;
    public static final int COMPONENT_BLANKMOLD = 4;
    public static final int COMPONENT_GUN_BARREL = 5;
    public static final int COMPONENT_REVOLVER_DRUM = 6;
    public static final int COMPONENT_REVOLVER_FRAME = 7;
    public static final int COMPONENT_AMMO_CASING = 8;
    public static final int COMPONENT_AMMO_BULLET = 9;
    public static final int COMPONENT_AMMO_BULLET_HOLLOW = 10;
    public static final int COMPONENT_AMMO_BULLET_JACKETED = 11;
    public static final int COMPONENT_GUNPOWDER_SMALL = 12;
    public static final int COMPONENT_BLAZEPOWDER_SMALL = 13;
    public static final int COMPONENT_AMMO_PELLET = 14;
    public static final int COMPONENT_AMMO_CASING_SHELL = 15;
    public static final int COMPONENT_SHOTGUN_PUMP = 16;
    public static final int COMPONENT_SHOTGUN_FRAME = 17;
    public static final int COMPONENT_DUST_ZINC = 18;
    public static final int COMPONENT_DUST_BRASS = 19;
    public static final int COMPONENT_DUST_CUPRONICKEL = 20;
    public static final int COMPONENT_SHARD_ENERGY_TC = 21;
    public static final int COMPONENT_SHARD_LIFE_TC = 22;
    public static final int COMPONENT_SHARD_VOID_TC = 23;
    public static final int COMPONENT_AMMO_BULLET_STEEL = 24;
    public static final int COMPONENT_AMMO_PELLET_STEEL = 25;

    private static final String[] ICON_PATHS = {
        "foundry:gear",
        "foundry:heatingcoil",
        "foundry:foundry_clay",
        "foundry:foundry_brick",
        "foundry:claymold_blank",
        "foundry:gun_barrel",
        "foundry:revolver_drum",
        "foundry:revolver_frame",
        "foundry:ammo_casing",
        "foundry:ammo_bullet",
        "foundry:ammo_bulletHollow",
        "foundry:ammo_bulletJacketed",
        "foundry:gunpowderSmall",
        "foundry:blazePowderSmall",
        "foundry:ammo_pellet",
        "foundry:ammo_casingShell",
        "foundry:shotgun_pump",
        "foundry:shotgun_frame",
        "foundry:dust_zinc",
        "foundry:dust_brass",
        "foundry:dust_cupronickel",
        "foundry:shard_energy_tc",
        "foundry:shard_life_tc",
        "foundry:shard_void_tc",
        "foundry:ammo_bullet_steel",
        "foundry:ammo_pellet_steel"
    };

    @Deprecated
    public static final String[] REGISTRY_NAMES_LEGACY = {
        "itemStoneGear",
        "itemHeatingCoil",
        "itemRefractoryClay",
        "itemRefractoryBrick",
        "itemBlankClayMold",
        "itemGunBarrel",
        "itemRevolverDrum",
        "itemRevolverFrame",
        "itemAmmoCasing",
        "itemAmmoBullet",
        "itemAmmoBulletHollow",
        "itemAmmoBulletJacketed",
        "itemGunpowderSmall",
        "itemBlazePowderSmall",
        "itemAmmoPellet",
        "itemAmmoShellCasing",
        "itemShotgunPump",
        "itemShotgunFrame",
        "itemZincDust",
        "itemBrassDust",
        "itemCupronickelDust",
        "itemShardEnergy",
        "itemShardLife",
        "itemShardVoid",
        "itemAmmoBulletSteel",
        "itemAmmoPelletSteel"
    };

    public static final String[] REGISTRY_NAMES = {
        "gearStone",
        "heatingCoil",
        "refractoryClay",
        "refractoryBrick",
        "moldSoftBlank",
        "gunBarrel",
        "revolverDrum",
        "revolverFrame",
        "ammoCasing",
        "ammoBullet",
        "ammoBulletHollow",
        "AmmoBulletJacketed",
        "dustSmallGunpowder",
        "dustSmallBlaze",
        "ammoPellet",
        "ammoShellCasing",
        "shotgunPump",
        "shotgunFrame",
        "dustZinc",
        "dustBrass",
        "dustCupronickel",
        "shardEnergy",
        "shardLife",
        "shardVoid",
        "ammoBulletSteel",
        "ammoPelletSteel"
    };

    @SideOnly(Side.CLIENT)
    private IIcon[] icons;

    public ItemComponent() {
        super();
        setCreativeTab(FoundryTabMaterials.tab);
        setHasSubtypes(true);
        setUnlocalizedName("component");
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
