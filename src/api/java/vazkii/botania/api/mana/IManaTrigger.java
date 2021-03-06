/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [May 16, 2014, 7:52:53 PM (GMT)]
 */
package vazkii.botania.api.mana;

import net.minecraft.world.World;
import vazkii.botania.api.internal.IManaBurst;

/**
 * Have a block implement this class to make it do something when a mana burst collides with it.
 */
public interface IManaTrigger {

    public void onBurstCollision(IManaBurst burst, World world, int x, int y, int z);
}
