package exter.foundry.integration;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.ModFoundry;
import exter.foundry.api.FoundryAPI;
import exter.foundry.api.FoundryUtils;
import exter.foundry.item.FoundryItems;
import exter.foundry.item.ItemMold;
import exter.foundry.recipes.manager.CastingRecipeManager;
import exter.foundry.util.FoundryMiscUtils;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public abstract class ModIntegration {
    private static Map<String, ModIntegration> integrations = new HashMap<String, ModIntegration>();

    public final String Name;

    protected boolean is_loaded;

    public ModIntegration(String mod_name) {
        Name = mod_name;
        is_loaded = true;
    }

    public abstract void OnPreInit(Configuration config);

    public abstract void OnInit();

    public abstract void OnPostInit();

    public void OnAfterPreInit() {}

    public void OnAfterPostInit() {}

    @SideOnly(Side.CLIENT)
    public void OnClientPreInit() {}

    @SideOnly(Side.CLIENT)
    public void OnClientInit() {}

    @SideOnly(Side.CLIENT)
    public void OnClientPostInit() {}

    public final boolean IsLoaded() {
        return is_loaded;
    }

    public static final ModIntegration GetIntegration(String name) {
        return integrations.get(name);
    }

    public static final void RegisterIntegration(
            Configuration config, Class<? extends ModIntegration> mod, String name) {
        try {
            ModIntegration integration =
                    mod.getDeclaredConstructor(String.class).newInstance(name);
            if (config.get("integration", "enable." + integration.Name, true).getBoolean(true)) {
                integrations.put(integration.Name, integration);
            }
        } catch (NoClassDefFoundError e) {
            ModFoundry.log.debug(e.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    public static final void PreInit(Configuration config) {
        for (ModIntegration m : integrations.values()) {
            if (m.is_loaded) {
                ModFoundry.log.info("PreInit integration: " + m.Name);
                m.OnPreInit(config);
            }
        }
    }

    public static final void AfterPreInit() {
        for (ModIntegration m : integrations.values()) {
            if (m.is_loaded) {
                ModFoundry.log.info("AfterPreInit integration: " + m.Name);
                m.OnAfterPreInit();
            }
        }
    }

    public static final void Init() {
        for (ModIntegration m : integrations.values()) {
            if (m.is_loaded) {
                ModFoundry.log.info("Init integration: " + m.Name);
                m.OnInit();
            }
        }
    }

    public static final void PostInit() {
        for (ModIntegration m : integrations.values()) {
            if (m.is_loaded) {
                ModFoundry.log.info("PostInit integration: " + m.Name);
                m.OnPostInit();
            }
        }
    }

    public static final void AfterPostInit() {
        for (ModIntegration m : integrations.values()) {
            if (m.is_loaded) {
                ModFoundry.log.info("AfterPostInit integration: " + m.Name);
                m.OnAfterPostInit();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static final void ClientPreInit() {
        for (ModIntegration m : integrations.values()) {
            if (m.is_loaded) {
                m.OnClientPreInit();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static final void ClientPostInit() {
        for (ModIntegration m : integrations.values()) {
            if (m.is_loaded) {
                m.OnClientPostInit();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static final void ClientInit() {
        for (ModIntegration m : integrations.values()) {
            if (m.is_loaded) {
                m.OnClientInit();
            }
        }
    }

    protected static void RegisterCasting(
            ItemStack item, Fluid liquid_metal, int ingots, int mold_meta, ItemStack extra) {
        RegisterCasting(item, new FluidStack(liquid_metal, FoundryAPI.FLUID_AMOUNT_INGOT * ingots), mold_meta, extra);
    }

    protected static void RegisterCasting(ItemStack item, FluidStack fluid, int mold_meta, ItemStack extra) {
        if (item != null) {
            ItemStack mold = new ItemStack(FoundryItems.item_mold, 1, mold_meta);
            if (CastingRecipeManager.instance.FindRecipe(
                            new FluidStack(fluid.getFluid(), FoundryAPI.CASTER_TANK_CAPACITY), mold, extra)
                    == null) {
                CastingRecipeManager.instance.AddRecipe(item, fluid, mold, extra);
            }
        }
    }

    protected static void RegisterPlateMoldRecipe(ItemStack item, String oredict_name) {
        if (FoundryUtils.IsItemInOreDictionary(oredict_name, item)) {
            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_PLATE_SOFT, oredict_name);
        } else {
            FoundryMiscUtils.RegisterMoldRecipe(ItemMold.MOLD_PLATE_SOFT, item);
        }
    }

    protected static ItemStack newItemStack(Item item) {
        if (item == null) {
            return null;
        }
        return new ItemStack(item);
    }
}
