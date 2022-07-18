package exter.foundry.api.firearms;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * API for a firearm round. this interface is implemented in the item's class.
 */
public interface IFirearmRound {
    /**
     * Determine which gun this round fits.
     * Possible values are "revolver" for the Revolver, or "shotgun" for the shotgun.
     * @return The type of gun this ammo is used for.
     */
    public String GetRoundType(ItemStack round);

    /**
     * Called when a bullet hits a block.
     * @param round Round that was fired.
     * @param shooter Player or mob, that made the shot.
     * @param from Location the shot originated.
     * @param world World the shot hit.
     * @param x X coordinate of the block hit.
     * @param y Y coordinate of the block hit.
     * @param z Z coordinate of the block hit.
     * @param side Side of the block hit.
     */
    public void OnBulletHitBlock(
            ItemStack round,
            EntityLivingBase shooter,
            Vec3 from,
            World world,
            int x,
            int y,
            int z,
            ForgeDirection side);

    /**
     * Called after a shot hit and damaged an entity
     * This method can be used to apply potion effects in special rounds.
     * @param round Round that was fired.
     * @param entity Entity that the shot hit.
     * @param count How many bullets/pellets the shot hit the entity.
     */
    public void OnBulletDamagedLivingEntity(ItemStack round, EntityLivingBase entity, int count);

    /**
     * Should the round break glass.
     * @param round The Round item.
     * @return Should the round break glass
     */
    public boolean BreakGlass(ItemStack round);

    /**
     * Get the base range of the round.
     * Bullets that hit with a distance below the base range do their base damage.
     * @param round The Round item.
     * @return The base range of the round.
     */
    public double GetBaseRange(ItemStack round);

    /**
     * Get the damage fall-off range of the round.
     * Bullets that hit with a distance above the base range have their damage reduced the further they hit.
     * Bullets that hit further than their base+falloff range do no damage.
     * @param round The Round item.
     * @return The base range of the round.
     */
    public double GetFalloffRange(ItemStack round);

    /**
     * Get the base damage of the round.
     * Note: The base damage is applied per bullet/pellet (revolvers shoot 1 bullet, shotguns shoot 6 pellets).
     * @param round The Round item.
     * @return The base damage of the round.
     */
    public double GetBaseDamage(ItemStack round);

    /**
     * Get the casing that the round uses.
     * @param round The Round item.
     * @return ItemStack of the casing.
     */
    public ItemStack GetCasing(ItemStack round);

    /**
     * Check if the round ignores armor.
     * @param round The Round item.
     * @return true if the round ignores armor.
     */
    public boolean IgnoresArmor(ItemStack round);
}
