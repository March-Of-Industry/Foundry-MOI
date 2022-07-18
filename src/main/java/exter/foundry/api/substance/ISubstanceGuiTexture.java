package exter.foundry.api.substance;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public interface ISubstanceGuiTexture {
    /**
     * Get the texture's location/
     * @return The texture's location.
     */
    public ResourceLocation GetLocation();

    /**
     * Get the X coordinate offset of the substance in the texture.
     * @return The X coordinate offset of the substance in the texture.
     */
    public int GetX();

    /**
     * Get the Y coordinate offset of the substance in the texture.
     * @return The Y coordinate offset of the substance in the texture.
     */
    public int GetY();

    /**
     * Get the color of the substance.
     * @return The color of the substance.
     */
    public int GetColor();
}
