package exter.foundry.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.container.ContainerMetalAtomizer;
import exter.foundry.gui.button.GuiButtonFoundry;
import exter.foundry.tileentity.TileEntityMetalAtomizer;
import exter.foundry.tileentity.TileEntityMetalCaster;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiMetalAtomizer extends GuiFoundry {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation("foundry:textures/gui/atomizer.png");

    private static final int TANK_HEIGHT = 47;
    private static final int TANK_INPUT_X = 39;
    private static final int TANK_INPUT_Y = 21;
    private static final int TANK_WATER_X = 123;
    private static final int TANK_WATER_Y = 21;

    private static final int PROGRESS_X = 60;
    private static final int PROGRESS_Y = 37;
    private static final int PROGRESS_WIDTH = 22;
    private static final int PROGRESS_HEIGHT = 15;

    private static final int POWER_X = 106;
    private static final int POWER_Y = 37;
    private static final int POWER_WIDTH = 3;
    private static final int POWER_HEIGHT = 16;

    private static final int TANK_OVERLAY_X = 176;
    private static final int TANK_OVERLAY_Y = 0;

    private static final int PROGRESS_OVERLAY_X = 176;
    private static final int PROGRESS_OVERLAY_Y = 53;

    private static final int POWER_OVERLAY_X = 176;
    private static final int POWER_OVERLAY_Y = 71;

    private static final int RSMODE_X = 176 - 16 - 4;
    private static final int RSMODE_Y = 4;
    private static final int RSMODE_TEXTURE_X = 176;
    private static final int RSMODE_TEXTURE_Y = 90;

    private TileEntityMetalAtomizer te_atomizer;
    private GuiButtonFoundry button_mode;

    public GuiMetalAtomizer(TileEntityMetalAtomizer at, IInventory player_inv) {
        super(new ContainerMetalAtomizer(at, player_inv));
        allowUserInput = false;
        ySize = 166;
        te_atomizer = at;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
        super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);

        fontRendererObj.drawString("Metal Atomizer", 5, 6, 0x404040);
        fontRendererObj.drawString("Inventory", 8, (ySize - 96) + 2, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(GUI_TEXTURE);
        int window_x = (width - xSize) / 2;
        int window_y = (height - ySize) / 2;
        drawTexturedModalRect(window_x, window_y, 0, 0, xSize, ySize);

        // Draw progress bar.
        int progress = te_atomizer.GetProgress() * PROGRESS_WIDTH / TileEntityMetalCaster.CAST_TIME;
        if (progress > 0) {
            drawTexturedModalRect(
                    window_x + PROGRESS_X,
                    window_y + PROGRESS_Y,
                    PROGRESS_OVERLAY_X,
                    PROGRESS_OVERLAY_Y,
                    progress,
                    PROGRESS_HEIGHT);
        }

        // Draw stored power bar.
        int power = te_atomizer.GetStoredEnergy() * POWER_HEIGHT / te_atomizer.GetEnergyCapacity();
        if (power > 0) {
            drawTexturedModalRect(
                    window_x + POWER_X,
                    window_y + POWER_Y + POWER_HEIGHT - power,
                    POWER_OVERLAY_X,
                    POWER_OVERLAY_Y + POWER_HEIGHT - power,
                    POWER_WIDTH,
                    power);
        }
        DisplayTank(
                window_x,
                window_y,
                TANK_INPUT_X,
                TANK_INPUT_Y,
                TANK_HEIGHT,
                TANK_OVERLAY_X,
                TANK_OVERLAY_Y,
                te_atomizer.GetTank(TileEntityMetalAtomizer.TANK_INPUT));
        DisplayTank(
                window_x,
                window_y,
                TANK_WATER_X,
                TANK_WATER_Y,
                TANK_HEIGHT,
                TANK_OVERLAY_X,
                TANK_OVERLAY_Y,
                te_atomizer.GetTank(TileEntityMetalAtomizer.TANK_WATER));
    }

    @Override
    public void drawScreen(int mousex, int mousey, float par3) {
        super.drawScreen(mousex, mousey, par3);

        if (func_146978_c /*isPointInRegion*/(TANK_INPUT_X, TANK_INPUT_Y, 16, TANK_HEIGHT, mousex, mousey)) {
            List<String> currenttip = new ArrayList<String>();
            AddTankTooltip(currenttip, mousex, mousey, te_atomizer.GetTank(TileEntityMetalAtomizer.TANK_INPUT));
            drawHoveringText(currenttip, mousex, mousey, fontRendererObj);
        }

        if (func_146978_c /*isPointInRegion*/(TANK_WATER_X, TANK_WATER_Y, 16, TANK_HEIGHT, mousex, mousey)) {
            List<String> currenttip = new ArrayList<String>();
            AddTankTooltip(currenttip, mousex, mousey, te_atomizer.GetTank(TileEntityMetalAtomizer.TANK_WATER));
            drawHoveringText(currenttip, mousex, mousey, fontRendererObj);
        }

        if (func_146978_c /*isPointInRegion*/(POWER_X, POWER_Y, POWER_WIDTH, POWER_HEIGHT, mousex, mousey)) {
            List<String> currenttip = new ArrayList<String>();
            int power = te_atomizer.GetStoredEnergy();
            int max_power = te_atomizer.GetEnergyCapacity();
            currenttip.add("Energy: " + String.valueOf(power) + "/" + String.valueOf(max_power));
            drawHoveringText(currenttip, mousex, mousey, fontRendererObj);
        }

        if (func_146978_c /*isPointInRegion*/(
                RSMODE_X, RSMODE_Y, button_mode.GetWidth(), button_mode.GetHeight(), mousex, mousey)) {
            List<String> currenttip = new ArrayList<String>();
            switch (te_atomizer.GetMode()) {
                case RSMODE_IGNORE:
                    currenttip.add("Mode: Ignore Restone");
                    break;
                case RSMODE_OFF:
                    currenttip.add("Mode: Redstone signal OFF");
                    break;
                case RSMODE_ON:
                    currenttip.add("Mode: Redstone signal ON");
                    break;
                case RSMODE_PULSE:
                    currenttip.add("Mode: Redstone pulse");
                    break;
            }
            drawHoveringText(currenttip, mousex, mousey, fontRendererObj);
        }
    }

    @Override
    protected ResourceLocation GetGUITexture() {
        return GUI_TEXTURE;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();
        int window_x = (width - xSize) / 2;
        int window_y = (height - ySize) / 2;
        button_mode = new GuiButtonFoundry(
                1,
                RSMODE_X + window_x,
                RSMODE_Y + window_y,
                16,
                15,
                GUI_TEXTURE,
                RSMODE_TEXTURE_X,
                RSMODE_TEXTURE_Y,
                RSMODE_TEXTURE_X + 16,
                RSMODE_TEXTURE_Y);
        buttonList.add(button_mode);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == button_mode.id) {
            te_atomizer.SetMode(te_atomizer.GetMode().Next());
        }
    }
}
