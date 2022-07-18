package exter.foundry.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import exter.foundry.container.ContainerMaterialRouter;
import exter.foundry.gui.button.GuiButtonFoundry;
import exter.foundry.material.MaterialRegistry;
import exter.foundry.tileentity.TileEntityMaterialRouter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiMaterialRouter extends GuiFoundry {

    private TileEntityMaterialRouter te_router;

    @SideOnly(Side.CLIENT)
    private abstract class FilterSlot {
        protected final String name;
        protected final int index;

        public FilterSlot(int slot_index, String filter_name) {
            index = slot_index;
            name = filter_name;
        }

        public abstract void Draw(int x, int y);

        public abstract void DrawTooltip(int x, int y);

        public abstract void OnClick();

        public final String GetName() {
            return name;
        }
    }

    @SideOnly(Side.CLIENT)
    private class FilterSlotMaterial extends FilterSlot {
        public FilterSlotMaterial(int slot_index, String filter_name) {
            super(slot_index, filter_name);
        }

        @Override
        public void Draw(int x, int y) {
            DrawMaterialIcon(x, y, name);
        }

        @Override
        public void DrawTooltip(int x, int y) {
            List<String> tooltip = new ArrayList<String>();
            tooltip.add(StatCollector.translateToLocal("router.material." + name));
            drawHoveringText(tooltip, x, y, fontRendererObj);
        }

        @Override
        public void OnClick() {
            te_router.gui_material_selected = index;
            te_router.SyncRoutes();
        }
    }

    @SideOnly(Side.CLIENT)
    private class FilterSlotType extends FilterSlot {
        public FilterSlotType(int slot_index, String filter_name) {
            super(slot_index, filter_name);
        }

        @Override
        public void Draw(int x, int y) {
            DrawTypeIcon(x, y, name);
        }

        @Override
        public void DrawTooltip(int x, int y) {
            List<String> tooltip = new ArrayList<String>();
            tooltip.add(StatCollector.translateToLocal("router.type." + name));
            drawHoveringText(tooltip, x, y, fontRendererObj);
        }

        @Override
        public void OnClick() {
            te_router.gui_type_selected = index;
            te_router.SyncRoutes();
        }
    }

    private List<FilterSlot> material_slots;
    private List<FilterSlot> type_slots;

    private GuiButtonFoundry[] route_buttons;
    private GuiButtonFoundry material_scroll_left;
    private GuiButtonFoundry material_scroll_right;
    private GuiButtonFoundry type_scroll_left;
    private GuiButtonFoundry type_scroll_right;
    private GuiButtonFoundry route_scroll_up;
    private GuiButtonFoundry route_scroll_down;
    private boolean do_scroll_sync;

    private void DrawMaterialIcon(int x, int y, String name) {
        if (name.equals("_Any")) {
            GL11.glEnable(GL11.GL_BLEND);
            drawTexturedModalRect(x, y, 216, 193, 16, 16);
        } else {
            DrawItemStack(x, y, MaterialRegistry.instance.GetMaterialIcon(name));
        }
    }

    private void DrawTypeIcon(int x, int y, String name) {
        if (name.equals("_Any")) {
            GL11.glEnable(GL11.GL_BLEND);
            drawTexturedModalRect(x, y, 216, 193, 16, 16);
        } else {
            DrawItemStack(x, y, MaterialRegistry.instance.GetTypeIcon(name));
        }
    }

    public GuiMaterialRouter(TileEntityMaterialRouter router, IInventory player_inv) {
        super(new ContainerMaterialRouter(router, player_inv));
        allowUserInput = false;
        xSize = 200;
        ySize = 229;
        te_router = router;

        material_slots = new ArrayList<FilterSlot>();
        material_slots.add(new FilterSlotMaterial(0, "_Any"));
        List<String> materials = new ArrayList<String>(MaterialRegistry.instance.GetMaterialNames());
        Collections.sort(materials, String.CASE_INSENSITIVE_ORDER);
        int i = 1;
        for (String name : materials) {
            material_slots.add(new FilterSlotMaterial(i++, name));
        }

        type_slots = new ArrayList<FilterSlot>();
        type_slots.add(new FilterSlotType(0, "_Any"));
        List<String> types = new ArrayList<String>(MaterialRegistry.instance.GetTypeNames());
        Collections.sort(types, String.CASE_INSENSITIVE_ORDER);
        i = 1;
        for (String name : types) {
            type_slots.add(new FilterSlotType(i++, name));
        }
        do_scroll_sync = false;
    }

    public static final ResourceLocation GUI_TEXTURE = new ResourceLocation("foundry:textures/gui/materialrouter.png");

    @Override
    protected ResourceLocation GetGUITexture() {
        return GUI_TEXTURE;
    }

    @Override
    public void drawScreen(int mousex, int mousey, float par3) {
        super.drawScreen(mousex, mousey, par3);

        int i;
        for (i = 0; i < 8; i++) {
            int index = i + te_router.gui_material_scroll;
            if (index >= material_slots.size()) {
                break;
            }
            FilterSlot slot = material_slots.get(index);
            if (func_146978_c /*isPointInRegion*/(111 + 17 * (i % 4), 24 + 17 * (i / 4), 16, 16, mousex, mousey)) {
                slot.DrawTooltip(mousex, mousey);
            }
        }

        for (i = 0; i < 8; i++) {
            int index = i + te_router.gui_type_scroll;
            if (index >= type_slots.size()) {
                break;
            }
            FilterSlot slot = type_slots.get(index);
            if (func_146978_c /*isPointInRegion*/(111 + 17 * (i % 4), 70 + 17 * (i / 4), 16, 16, mousex, mousey)) {
                slot.DrawTooltip(mousex, mousey);
            }
        }

        List<TileEntityMaterialRouter.Route> routes = te_router.GetRoutes();
        for (i = 0; i < 4; i++) {
            int index = i + te_router.gui_route_scroll;
            if (index >= routes.size()) {
                break;
            }

            TileEntityMaterialRouter.Route r = routes.get(index);
            int y = 49 + i * 17;
            if (func_146978_c /*isPointInRegion*/(29, y, 16, 16, mousex, mousey)) {
                List<String> tooltip = new ArrayList<String>();
                tooltip.add(StatCollector.translateToLocal("router.material." + r.material));
                drawHoveringText(tooltip, mousex, mousey, fontRendererObj);
            }
            if (func_146978_c /*isPointInRegion*/(46, y, 16, 16, mousex, mousey)) {
                List<String> tooltip = new ArrayList<String>();
                tooltip.add(StatCollector.translateToLocal("router.type." + r.type));
                drawHoveringText(tooltip, mousex, mousey, fontRendererObj);
            }
            if (func_146978_c /*isPointInRegion*/(81, y + 4, 8, 8, mousex, mousey)) {
                List<String> tooltip = new ArrayList<String>();
                tooltip.add("Remove");
                drawHoveringText(tooltip, mousex, mousey, fontRendererObj);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.bindTexture(GUI_TEXTURE);
        int window_x = (width - xSize) / 2;
        int window_y = (height - ySize) / 2;
        drawTexturedModalRect(window_x, window_y, 0, 0, xSize, ySize);

        int i;

        for (i = 0; i < 8; i++) {
            int index = i + te_router.gui_material_scroll;
            if (index >= material_slots.size()) {
                break;
            }
            FilterSlot slot = material_slots.get(index);
            slot.Draw(window_x + 111 + 17 * (i % 4), window_y + 24 + 17 * (i / 4));
        }

        for (i = 0; i < 8; i++) {
            int index = i + te_router.gui_type_scroll;
            if (index >= type_slots.size()) {
                break;
            }
            FilterSlot slot = type_slots.get(index);
            slot.Draw(window_x + 111 + 17 * (i % 4), window_y + 70 + 17 * (i / 4));
        }

        int selected_material = te_router.gui_material_selected;
        int selected_type = te_router.gui_type_selected;

        int material_scroll = te_router.gui_material_scroll;
        int type_scroll = te_router.gui_type_scroll;

        GL11.glEnable(GL11.GL_BLEND);

        if (selected_material >= material_scroll && selected_material < material_scroll + 8) {
            int index = selected_material - material_scroll;
            drawTexturedModalRect(
                    window_x + 111 + 17 * (index % 4), window_y + 24 + 17 * (index / 4), 200, 193, 16, 16);
        }

        if (selected_type >= type_scroll && selected_type < type_scroll + 8) {
            int index = selected_type - type_scroll;
            drawTexturedModalRect(
                    window_x + 111 + 17 * (index % 4), window_y + 70 + 17 * (index / 4), 200, 193, 16, 16);
        }

        List<TileEntityMaterialRouter.Route> routes = te_router.GetRoutes();
        for (i = 0; i < 4; i++) {
            int index = i + te_router.gui_route_scroll;
            if (index >= routes.size()) {
                break;
            }

            TileEntityMaterialRouter.Route r = routes.get(index);
            int y = 49 + i * 17;
            DrawMaterialIcon(window_x + 29, window_y + y, r.material);
            DrawTypeIcon(window_x + 46, window_y + y, r.type);
            GL11.glEnable(GL11.GL_BLEND);

            drawTexturedModalRect(window_x + 63, window_y + y, 200, r.side.index * 16, 16, 16);
            drawTexturedModalRect(window_x + 81, window_y + y + 4, 234, 194, 8, 8);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouse_x, int mouse_y) {
        super.drawGuiContainerForegroundLayer(mouse_x, mouse_y);
        fontRendererObj.drawString("Material Router", 6, 6, 0x404040);
        fontRendererObj.drawString("Inventory", 32, (ySize - 96) + 2, 0x404040);

        fontRendererObj.drawString(
                "Materials(" + (te_router.gui_material_scroll / 8 + 1) + "/"
                        + (int) Math.ceil((double) material_slots.size() / 8) + "):",
                111,
                14,
                0x404040);
        fontRendererObj.drawString(
                "Types(" + (te_router.gui_type_scroll / 8 + 1) + "/" + (int) Math.ceil((double) type_slots.size() / 8)
                        + "):",
                111,
                60,
                0x404040);
    }

    @Override
    protected void mouseClicked(int x, int y, int par3) {
        super.mouseClicked(x, y, par3);

        int i;
        for (i = 0; i < 8; i++) {
            int index = i + te_router.gui_material_scroll;
            if (index >= material_slots.size()) {
                break;
            }
            FilterSlot slot = material_slots.get(index);
            if (func_146978_c /*isPointInRegion*/(111 + 17 * (i % 4), 24 + 17 * (i / 4), 16, 16, x, y)) {
                slot.OnClick();
                return;
            }
        }

        for (i = 0; i < 8; i++) {
            int index = i + te_router.gui_type_scroll;
            if (index >= type_slots.size()) {
                break;
            }
            FilterSlot slot = type_slots.get(index);
            if (func_146978_c /*isPointInRegion*/(111 + 17 * (i % 4), 70 + 17 * (i / 4), 16, 16, x, y)) {
                slot.OnClick();
                return;
            }
        }

        List<TileEntityMaterialRouter.Route> routes = te_router.GetRoutes();
        for (i = 0; i < 4; i++) {
            int index = i + te_router.gui_route_scroll;
            if (index >= routes.size()) {
                break;
            }

            if (func_146978_c /*isPointInRegion*/(81, 49 + i * 17 + 4, 8, 8, x, y)) {
                routes.remove(index);
                te_router.SyncRoutes();
                return;
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id < 6) {
            te_router.GetRoutes()
                    .add(new TileEntityMaterialRouter.Route(
                            material_slots.get(te_router.gui_material_selected).GetName(),
                            type_slots.get(te_router.gui_type_selected).GetName(),
                            TileEntityMaterialRouter.RouteSide.values()[button.id]));
            te_router.SyncRoutes();
        } else if (button.id == material_scroll_left.id) {
            if (te_router.gui_material_scroll > 0) {
                te_router.gui_material_scroll -= 8;
                do_scroll_sync = true;
            }
        } else if (button.id == material_scroll_right.id) {
            if (te_router.gui_material_scroll < material_slots.size() - 8) {
                te_router.gui_material_scroll += 8;
                do_scroll_sync = true;
            }
        } else if (button.id == type_scroll_left.id) {
            if (te_router.gui_type_scroll > 0) {
                te_router.gui_type_scroll -= 8;
                do_scroll_sync = true;
            }
        } else if (button.id == type_scroll_right.id) {
            if (te_router.gui_type_scroll < type_slots.size() - 8) {
                te_router.gui_type_scroll += 8;
                do_scroll_sync = true;
            }
        } else if (button.id == route_scroll_up.id) {
            if (te_router.gui_route_scroll > 0) {
                te_router.gui_route_scroll -= 4;
                do_scroll_sync = true;
            }
        } else if (button.id == route_scroll_down.id) {
            if (te_router.gui_route_scroll < te_router.GetRoutes().size() - 4) {
                te_router.gui_route_scroll += 4;
                do_scroll_sync = true;
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();
        int window_x = (width - xSize) / 2;
        int window_y = (height - ySize) / 2;
        route_buttons = new GuiButtonFoundry[6];
        int i;
        for (i = 0; i < 6; i++) {
            route_buttons[i] = new GuiButtonFoundry(
                            i,
                            window_x + 119 + (i % 3) * 18,
                            window_y + 108 + (i / 3) * 18,
                            16,
                            16,
                            GUI_TEXTURE,
                            200,
                            177,
                            216,
                            177)
                    .SetIconTexture(201, i * 16 + 1, 14, 14);
            buttonList.add(route_buttons[i]);
        }
        material_scroll_left =
                new GuiButtonFoundry(6, window_x + 96, window_y + 28, 12, 25, GUI_TEXTURE, 200, 99, 212, 99);
        buttonList.add(material_scroll_left);
        material_scroll_right =
                new GuiButtonFoundry(7, window_x + 181, window_y + 28, 12, 25, GUI_TEXTURE, 200, 124, 212, 124);
        buttonList.add(material_scroll_right);
        type_scroll_left = new GuiButtonFoundry(8, window_x + 96, window_y + 74, 12, 25, GUI_TEXTURE, 200, 99, 212, 99);
        buttonList.add(type_scroll_left);
        type_scroll_right =
                new GuiButtonFoundry(9, window_x + 181, window_y + 74, 12, 25, GUI_TEXTURE, 200, 124, 212, 124);
        buttonList.add(type_scroll_right);
        route_scroll_up =
                new GuiButtonFoundry(10, window_x + 47, window_y + 24, 25, 12, GUI_TEXTURE, 200, 149, 225, 149);
        buttonList.add(route_scroll_up);
        route_scroll_down =
                new GuiButtonFoundry(11, window_x + 47, window_y + 119, 25, 12, GUI_TEXTURE, 200, 161, 225, 161);
        buttonList.add(route_scroll_down);
    }

    @Override
    public void onGuiClosed() {
        if (do_scroll_sync) {
            te_router.SyncRoutes();
        }
        super.onGuiClosed();
    }
}
