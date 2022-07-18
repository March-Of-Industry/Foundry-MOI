package exter.foundry.item;

import cpw.mods.fml.common.registry.GameRegistry;
import exter.foundry.item.ammo.ItemRoundAP;
import exter.foundry.item.ammo.ItemRoundFire;
import exter.foundry.item.ammo.ItemRoundHollow;
import exter.foundry.item.ammo.ItemRoundJacketed;
import exter.foundry.item.ammo.ItemRoundNormal;
import exter.foundry.item.ammo.ItemRoundPoison;
import exter.foundry.item.ammo.ItemShellAP;
import exter.foundry.item.ammo.ItemShellNormal;
import exter.foundry.item.firearm.ItemRevolver;
import exter.foundry.item.firearm.ItemShotgun;
import exter.foundry.registry.ItemRegistry;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class FoundryItems {
    public static ItemComponent item_component;
    public static ItemMold item_mold;
    public static ItemIngot item_ingot;
    public static ItemRevolver item_revolver;
    public static ItemShotgun item_shotgun;
    public static ItemRoundNormal item_round;
    public static ItemRoundJacketed item_round_jacketed;
    public static ItemRoundHollow item_round_hollow;
    public static ItemRoundFire item_round_fire;
    public static ItemRoundPoison item_round_poison;
    public static ItemRoundAP item_round_ap;
    public static ItemShellNormal item_shell;
    public static ItemShellAP item_shell_ap;

    public static ItemRefractoryFluidContainer item_container;

    public static Map<String, ItemStack> ingot_stacks = new HashMap<String, ItemStack>();

    public static void RegisterItems(Configuration config) {
        int i;

        item_component = new ItemComponent();
        item_mold = new ItemMold();
        item_ingot = new ItemIngot();
        item_revolver = new ItemRevolver();
        item_shotgun = new ItemShotgun();
        item_round = new ItemRoundNormal();
        item_round_jacketed = new ItemRoundJacketed();
        item_round_hollow = new ItemRoundHollow();
        item_round_fire = new ItemRoundFire();
        item_round_poison = new ItemRoundPoison();
        item_round_ap = new ItemRoundAP();
        item_shell = new ItemShellNormal();
        item_shell_ap = new ItemShellAP();

        GameRegistry.registerItem(item_component, "foundryComponent");
        GameRegistry.registerItem(item_mold, "foundryMold");
        GameRegistry.registerItem(item_ingot, "foundryIngot");
        GameRegistry.registerItem(item_revolver, "foundryRevolver");
        GameRegistry.registerItem(item_shotgun, "foundryShotgun");
        GameRegistry.registerItem(item_round, "foundryRound");
        GameRegistry.registerItem(item_round_jacketed, "foundryRoundJacketed");
        GameRegistry.registerItem(item_round_hollow, "foundryRoundHollow");
        GameRegistry.registerItem(item_round_fire, "foundryRoundFire");
        GameRegistry.registerItem(item_round_poison, "foundryRoundPoison");
        GameRegistry.registerItem(item_shell, "foundryShell");
        GameRegistry.registerItem(item_round_ap, "foundryRoundAP");
        GameRegistry.registerItem(item_shell_ap, "foundryShellAP");

        for (i = 0; i < ItemComponent.REGISTRY_NAMES.length; i++) {
            ItemStack stack = new ItemStack(item_component, 1, i);
            ItemRegistry.instance.RegisterItem(ItemComponent.REGISTRY_NAMES_LEGACY[i], stack);
            GameRegistry.registerCustomItemStack(ItemComponent.REGISTRY_NAMES[i], stack);
        }

        for (i = 0; i < ItemMold.REGISTRY_NAMES.length; i++) {
            ItemStack stack = new ItemStack(item_mold, 1, i);
            ItemRegistry.instance.RegisterItem(ItemMold.REGISTRY_NAMES_LEGACY[i], stack);
            GameRegistry.registerCustomItemStack(ItemMold.REGISTRY_NAMES[i], stack);
        }
        for (i = 0; i < ItemIngot.METAL_NAMES.length; i++) {
            ItemStack is = new ItemStack(item_ingot, 1, i);
            OreDictionary.registerOre(ItemIngot.OREDICT_NAMES[i], is);
            ingot_stacks.put(ItemIngot.METAL_NAMES[i], is);
        }
        ingot_stacks.put("Iron", new ItemStack(Items.iron_ingot));
        ingot_stacks.put("Gold", new ItemStack(Items.gold_ingot));

        item_container = new ItemRefractoryFluidContainer(FluidContainerRegistry.BUCKET_VOLUME);
        GameRegistry.registerItem(item_container, "foundryContainer");
        ItemRegistry.instance.RegisterItem("itemRefractoryFluidContainer", item_container.EmptyContainer(1));
        GameRegistry.registerCustomItemStack("refractoryFluidContainer", item_container.EmptyContainer(1));
    }

    public static ItemStack Ingot(int dv) {
        return Ingot(dv, 1);
    }

    public static ItemStack Ingot(int dv, int amount) {
        return new ItemStack(item_ingot, amount, dv);
    }

    public static ItemStack Component(int dv) {
        return Component(dv, 1);
    }

    public static ItemStack Component(int dv, int amount) {
        return new ItemStack(item_component, amount, dv);
    }

    public static ItemStack Mold(int dv) {
        return Mold(dv, 1);
    }

    public static ItemStack Mold(int dv, int amount) {
        return new ItemStack(item_mold, amount, dv);
    }
}
