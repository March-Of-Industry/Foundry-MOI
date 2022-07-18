package exter.foundry.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.container.ContainerRefractoryHopper;
import exter.foundry.tileentity.TileEntityRefractoryHopper;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiRefractoryHopper extends GuiFoundry {
    private static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation("foundry:textures/gui/refractoryhopper.png");

    private static final int TANK_HEIGHT = 44;

    private static final int TANK_X = 64;
    private static final int TANK_Y = 20;

    private static final int TANK_OVERLAY_X = 176;
    private static final int TANK_OVERLAY_Y = 0;

    private TileEntityRefractoryHopper te_hopper;

    public GuiRefractoryHopper(TileEntityRefractoryHopper hopper, IInventory player_inv) {
        super(new ContainerRefractoryHopper(hopper, player_inv));
        allowUserInput = false;
        ySize = 165;
        te_hopper = hopper;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
        super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);

        fontRendererObj.drawString("Refractory Hopper", 5, 6, 0x404040);
        fontRendererObj.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }

    @Override
    public void drawScreen(int mousex, int mousey, float par3) {
        super.drawScreen(mousex, mousey, par3);

        int i;
        for (i = 0; i < 5; i++) {
            if (func_146978_c /*isPointInRegion*/(TANK_X, TANK_Y, 16, TANK_HEIGHT, mousex, mousey)) {
                List<String> currenttip = new ArrayList<String>();
                AddTankTooltip(currenttip, mousex, mousey, te_hopper.GetTank(i));
                drawHoveringText(currenttip, mousex, mousey, fontRendererObj);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(GUI_TEXTURE);
        int window_x = (width - xSize) / 2;
        int window_y = (height - ySize) / 2;
        drawTexturedModalRect(window_x, window_y, 0, 0, xSize, ySize);

        DisplayTank(
                window_x, window_y, TANK_X, TANK_Y, TANK_HEIGHT, TANK_OVERLAY_X, TANK_OVERLAY_Y, te_hopper.GetTank(0));
    }

    @Override
    protected ResourceLocation GetGUITexture() {
        return GUI_TEXTURE;
    }
}
