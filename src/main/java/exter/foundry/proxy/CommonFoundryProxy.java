package exter.foundry.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import exter.foundry.container.ContainerAlloyFurnace;
import exter.foundry.container.ContainerAlloyMixer;
import exter.foundry.container.ContainerInductionCrucibleFurnace;
import exter.foundry.container.ContainerMaterialRouter;
import exter.foundry.container.ContainerMetalAtomizer;
import exter.foundry.container.ContainerMetalCaster;
import exter.foundry.container.ContainerMetalInfuser;
import exter.foundry.container.ContainerRefractoryHopper;
import exter.foundry.container.ContainerRevolver;
import exter.foundry.container.ContainerShotgun;
import exter.foundry.gui.GuiAlloyFurnace;
import exter.foundry.gui.GuiAlloyMixer;
import exter.foundry.gui.GuiInductionCrucibleFurnace;
import exter.foundry.gui.GuiMaterialRouter;
import exter.foundry.gui.GuiMetalAtomizer;
import exter.foundry.gui.GuiMetalCaster;
import exter.foundry.gui.GuiMetalInfuser;
import exter.foundry.gui.GuiRefractoryHopper;
import exter.foundry.gui.GuiRevolver;
import exter.foundry.gui.GuiShotgun;
import exter.foundry.tileentity.TileEntityAlloyFurnace;
import exter.foundry.tileentity.TileEntityAlloyMixer;
import exter.foundry.tileentity.TileEntityInductionCrucibleFurnace;
import exter.foundry.tileentity.TileEntityMaterialRouter;
import exter.foundry.tileentity.TileEntityMetalAtomizer;
import exter.foundry.tileentity.TileEntityMetalCaster;
import exter.foundry.tileentity.TileEntityMetalInfuser;
import exter.foundry.tileentity.TileEntityRefractoryHopper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class CommonFoundryProxy implements IGuiHandler {
    public static final int GUI_ICF = 0;
    public static final int GUI_CASTER = 1;
    public static final int GUI_ALLOYMIXER = 2;
    public static final int GUI_INFUSER = 3;
    public static final int GUI_ALLOYFURNACE = 4;
    public static final int GUI_MATERIALROUTER = 5;
    public static final int GUI_REFRACTORYHOPPER = 6;
    public static final int GUI_REVOLVER = 7;
    public static final int GUI_SHOTGUN = 8;
    public static final int GUI_ATOMIZER = 9;

    public static int hopper_renderer_id = -1;

    public void PreInit() {}

    public void Init() {}

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_ICF:
                return new ContainerInductionCrucibleFurnace(
                        (TileEntityInductionCrucibleFurnace) world.getTileEntity(x, y, z), player.inventory);
            case GUI_CASTER:
                return new ContainerMetalCaster((TileEntityMetalCaster) world.getTileEntity(x, y, z), player.inventory);
            case GUI_ALLOYMIXER:
                return new ContainerAlloyMixer((TileEntityAlloyMixer) world.getTileEntity(x, y, z), player.inventory);
            case GUI_INFUSER:
                return new ContainerMetalInfuser(
                        (TileEntityMetalInfuser) world.getTileEntity(x, y, z), player.inventory);
            case GUI_ALLOYFURNACE:
                return new ContainerAlloyFurnace(
                        (TileEntityAlloyFurnace) world.getTileEntity(x, y, z), player.inventory);
            case GUI_MATERIALROUTER:
                return new ContainerMaterialRouter(
                        (TileEntityMaterialRouter) world.getTileEntity(x, y, z), player.inventory);
            case GUI_REFRACTORYHOPPER:
                return new ContainerRefractoryHopper(
                        (TileEntityRefractoryHopper) world.getTileEntity(x, y, z), player.inventory);
            case GUI_REVOLVER:
                return new ContainerRevolver(player.getHeldItem(), player.inventory);
            case GUI_SHOTGUN:
                return new ContainerShotgun(player.getHeldItem(), player.inventory);
            case GUI_ATOMIZER:
                return new ContainerMetalAtomizer(
                        (TileEntityMetalAtomizer) world.getTileEntity(x, y, z), player.inventory);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case GUI_ICF: {
                TileEntityInductionCrucibleFurnace te =
                        (TileEntityInductionCrucibleFurnace) world.getTileEntity(x, y, z);
                return new GuiInductionCrucibleFurnace(te, player.inventory);
            }
            case GUI_CASTER: {
                TileEntityMetalCaster te = (TileEntityMetalCaster) world.getTileEntity(x, y, z);
                return new GuiMetalCaster(te, player.inventory);
            }
            case GUI_ALLOYMIXER: {
                TileEntityAlloyMixer te = (TileEntityAlloyMixer) world.getTileEntity(x, y, z);
                return new GuiAlloyMixer(te, player.inventory);
            }
            case GUI_INFUSER: {
                TileEntityMetalInfuser te = (TileEntityMetalInfuser) world.getTileEntity(x, y, z);
                return new GuiMetalInfuser(te, player.inventory);
            }
            case GUI_ALLOYFURNACE: {
                TileEntityAlloyFurnace te = (TileEntityAlloyFurnace) world.getTileEntity(x, y, z);
                return new GuiAlloyFurnace(te, player.inventory);
            }
            case GUI_MATERIALROUTER: {
                TileEntityMaterialRouter te = (TileEntityMaterialRouter) world.getTileEntity(x, y, z);
                return new GuiMaterialRouter(te, player.inventory);
            }
            case GUI_REFRACTORYHOPPER: {
                TileEntityRefractoryHopper te = (TileEntityRefractoryHopper) world.getTileEntity(x, y, z);
                return new GuiRefractoryHopper(te, player.inventory);
            }
            case GUI_REVOLVER: {
                return new GuiRevolver(player.getHeldItem(), player.inventory);
            }
            case GUI_SHOTGUN: {
                return new GuiShotgun(player.getHeldItem(), player.inventory);
            }
            case GUI_ATOMIZER: {
                TileEntityMetalAtomizer te = (TileEntityMetalAtomizer) world.getTileEntity(x, y, z);
                return new GuiMetalAtomizer(te, player.inventory);
            }
        }
        return null;
    }

    public void PostInit() {}
}
