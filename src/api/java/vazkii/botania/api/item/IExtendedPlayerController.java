/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under a
 * Creative Commons Attribution-NonCommercial-ShareAlike 3.0 License
 * (http://creativecommons.org/licenses/by-nc-sa/3.0/deed.en_GB)
 *
 * File Created @ [Aug 6, 2014, 6:02:29 PM (GMT)]
 */
package vazkii.botania.api.item;

/**
 * An interface that defines an instance of PlayerControllerMP with
 * the ability to modify reach. See vazkii.botania.client.core.handler.BotaniaPlayerController
 */
public interface IExtendedPlayerController {

    /**
     * Sets the extra reach the player should have.
     */
    public void setReachDistanceExtension(float f);

    /**
     * Gets the current reach extension.
     */
    public float getReachDistanceExtension();
}
