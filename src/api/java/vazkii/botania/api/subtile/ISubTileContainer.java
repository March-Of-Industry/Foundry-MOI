/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Aug 26, 2014, 5:42:16 PM (GMT)]
 */
package vazkii.botania.api.subtile;

/**
 * A TileEntity that implements this contains a SubTileEntity.
 */
public interface ISubTileContainer {

    /**
     * Gets the SubTile in this block. Generally shouldn't return null, but in that
     * case use the fallback DummySubTile.
     */
    public SubTileEntity getSubTile();

    /**
     * Sets the SubTile on this block from it's name.
     */
    public void setSubTile(String name);
}
