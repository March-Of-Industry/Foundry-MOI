/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 22, 2014, 5:04:30 PM (GMT)]
 */
package vazkii.botania.api.mana;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;

public class ManaNetworkEvent extends Event {

    public final TileEntity tile;
    public final ManaBlockType type;
    public final Action action;

    public ManaNetworkEvent(TileEntity tile, ManaBlockType type, Action action) {
        this.tile = tile;
        this.type = type;
        this.action = action;
    }

    public static void addCollector(TileEntity tile) {
        ManaNetworkEvent event = new ManaNetworkEvent(tile, ManaBlockType.COLLECTOR, Action.ADD);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void removeCollector(TileEntity tile) {
        ManaNetworkEvent event = new ManaNetworkEvent(tile, ManaBlockType.COLLECTOR, Action.REMOVE);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void addPool(TileEntity tile) {
        ManaNetworkEvent event = new ManaNetworkEvent(tile, ManaBlockType.POOL, Action.ADD);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void removePool(TileEntity tile) {
        ManaNetworkEvent event = new ManaNetworkEvent(tile, ManaBlockType.POOL, Action.REMOVE);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public enum ManaBlockType {
        POOL,
        COLLECTOR
    }

    public enum Action {
        REMOVE,
        ADD
    }
}
