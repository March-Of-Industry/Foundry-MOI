/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Jan 31, 2014, 3:03:04 PM (GMT)]
 */
package vazkii.botania.api.mana;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;

/**
 * Have an Item implement this to be counted as a lens for the mana spreader.
 */
public interface ILens extends ILensEffect {

    @SideOnly(Side.CLIENT)
    public int getLensColor(ItemStack stack);

    /**
     * Can the source lens be combined with the composite lens? This is called
     * for both the ILens instance of ItemStack.getItem() of sourceLens and compositeLens.
     */
    public boolean canCombineLenses(ItemStack sourceLens, ItemStack compositeLens);

    /**
     * Gets the composite lens in the stack passed in, return null for none.
     */
    public ItemStack getCompositeLens(ItemStack stack);

    /**
     * Sets the composite lens for the sourceLens as the compositeLens, returns
     * the ItemStack with the combination.
     */
    public ItemStack setCompositeLens(ItemStack sourceLens, ItemStack compositeLens);
}
