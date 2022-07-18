package exter.foundry;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import exter.foundry.api.FoundryAPI;
import exter.foundry.block.BlockFoundryOre;
import exter.foundry.block.FoundryBlocks;
import exter.foundry.config.FoundryConfig;
import exter.foundry.entity.EntitySkeletonGun;
import exter.foundry.integration.*;
import exter.foundry.item.FoundryItems;
import exter.foundry.item.ItemComponent;
import exter.foundry.network.FoundryNetworkChannel;
import exter.foundry.proxy.CommonFoundryProxy;
import exter.foundry.recipes.FoundryRecipes;
import exter.foundry.recipes.manager.AlloyFurnaceRecipeManager;
import exter.foundry.recipes.manager.AlloyMixerRecipeManager;
import exter.foundry.recipes.manager.AtomizerRecipeManager;
import exter.foundry.recipes.manager.CastingRecipeManager;
import exter.foundry.recipes.manager.InfuserRecipeManager;
import exter.foundry.recipes.manager.MeltingRecipeManager;
import exter.foundry.registry.ItemRegistry;
import exter.foundry.registry.LiquidMetalRegistry;
import exter.foundry.tileentity.TileEntityAlloyFurnace;
import exter.foundry.tileentity.TileEntityAlloyMixer;
import exter.foundry.tileentity.TileEntityInductionCrucibleFurnace;
import exter.foundry.tileentity.TileEntityMaterialRouter;
import exter.foundry.tileentity.TileEntityMetalAtomizer;
import exter.foundry.tileentity.TileEntityMetalCaster;
import exter.foundry.tileentity.TileEntityMetalInfuser;
import exter.foundry.tileentity.TileEntityRefractoryHopper;
import exter.foundry.worldgen.FoundryWorldGenerator;
import exter.foundry.worldgen.WordGenOre;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = Tags.MOD_ID,
        name = Tags.MOD_NAME,
        version = Tags.MOD_VERSION,
        dependencies = "required-after:Forge@[10.13.4.1448,);"
                + "after:TConstruct;"
                + "after:BuildCraft|Core;"
                + "after:Railcraft;"
                + "after:ThermalExpansion;"
                + "after:RedstoneArsenal;"
                + "after:IC2;"
                + "after:Forestry;"
                + "after:gregtech;"
                + "after:Thaumcraft;"
                + "after:Botania;"
                + "after:EnderIO")
public class ModFoundry {

    @Instance(Tags.MOD_ID)
    public static ModFoundry instance;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(
            clientSide = "exter.foundry.proxy.ClientFoundryProxy",
            serverSide = "exter.foundry.proxy.CommonFoundryProxy")
    public static CommonFoundryProxy proxy;

    public static Logger log = LogManager.getLogger(Tags.MOD_NAME);

    public CraftingEvents crafting_events;

    public static FoundryNetworkChannel network_channel;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("Loading Patched Version of Foundry");
        Configuration config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        ModIntegration.RegisterIntegration(config, ModIntegrationIC2.class, "ic2");
        ModIntegration.RegisterIntegration(config, ModIntegrationBuildcraft.class, "buildcraft");
        ModIntegration.RegisterIntegration(config, ModIntegrationForestry.class, "forestry");
        ModIntegration.RegisterIntegration(config, ModIntegrationRailcraft.class, "railcraft");
        ModIntegration.RegisterIntegration(config, ModIntegrationTF.class, "tf");
        ModIntegration.RegisterIntegration(config, ModIntegrationTE4.class, "te4");
        ModIntegration.RegisterIntegration(config, ModIntegrationRedstoneArsenal.class, "redarsenal");
        ModIntegration.RegisterIntegration(config, ModIntegrationTiCon.class, "ticon");
        ModIntegration.RegisterIntegration(config, ModIntegrationGregtech.class, "gregtech");
        ModIntegration.RegisterIntegration(config, ModIntegrationThaumcraft.class, "thaumcraft");
        ModIntegration.RegisterIntegration(config, ModIntegrationBotania.class, "botania");
        ModIntegration.RegisterIntegration(config, ModIntegrationMetallurgy.class, "metallurgy");
        ModIntegration.RegisterIntegration(config, ModIntegrationTwilightForest.class, "twf");
        ModIntegration.RegisterIntegration(config, ModIntegrationProjectRed.class, "projectred");
        ModIntegration.RegisterIntegration(config, ModIntegrationMekanism.class, "mekanism");
        ModIntegration.RegisterIntegration(config, ModIntegrationEnderIO.class, "enderio");
        ModIntegration.RegisterIntegration(config, ModIntegrationMatterOverdrive.class, "matter_overdrive");
        ModIntegration.RegisterIntegration(config, ModIntegrationMystcraft.class, "mystcraft");
        ModIntegration.RegisterIntegration(config, ModIntegrationRFTools.class, "rftools");
        ModIntegration.RegisterIntegration(config, ModIntegrationMinetweaker.class, "minetweaker");

        FoundryAPI.items = ItemRegistry.instance;
        FoundryAPI.fluids = LiquidMetalRegistry.instance;

        FoundryAPI.recipes_melting = MeltingRecipeManager.instance;
        FoundryAPI.recipes_casting = CastingRecipeManager.instance;
        FoundryAPI.recipes_alloymixer = AlloyMixerRecipeManager.instance;
        FoundryAPI.recipes_infuser = InfuserRecipeManager.instance;
        FoundryAPI.recipes_alloyfurnace = AlloyFurnaceRecipeManager.instance;
        FoundryAPI.recipes_atomizer = AtomizerRecipeManager.instance;

        ModOreDictionary.init();
        FoundryConfig.Load(config);
        FoundryItems.RegisterItems(config);
        FoundryBlocks.RegisterBlocks(config);


        FoundryRecipes.PreInit();

        ModIntegration.PreInit(config);

        config.save();

        crafting_events = new CraftingEvents();

        network_channel = new FoundryNetworkChannel();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
        proxy.PreInit();
        ModIntegration.AfterPreInit();
    }

    @SuppressWarnings("unchecked")
    @EventHandler
    public void load(FMLInitializationEvent event) {
        ModIntegration.Init();

        GameRegistry.registerTileEntity(TileEntityInductionCrucibleFurnace.class, "Foundry_ICF");
        GameRegistry.registerTileEntity(TileEntityMetalCaster.class, "Foundry_MetalCaster");
        GameRegistry.registerTileEntity(TileEntityAlloyMixer.class, "Foundry_AlloyMixer");
        GameRegistry.registerTileEntity(TileEntityMetalInfuser.class, "Foundry_MetalInfuser");
        GameRegistry.registerTileEntity(TileEntityAlloyFurnace.class, "Foundry_AlloyFurnace");
        GameRegistry.registerTileEntity(TileEntityMaterialRouter.class, "Foundry_MaterialRouter");
        GameRegistry.registerTileEntity(TileEntityRefractoryHopper.class, "Foundry_RefractoryHopper");
        GameRegistry.registerTileEntity(TileEntityMetalAtomizer.class, "Foundry_MetalAtomizer");

        FoundryRecipes.Init();

        proxy.Init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ModIntegration.PostInit();
        FoundryRecipes.PostInit();
        proxy.PostInit();
        ModIntegration.AfterPostInit();
    }
}
