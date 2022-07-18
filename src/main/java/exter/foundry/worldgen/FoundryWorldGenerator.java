package exter.foundry.worldgen;

import cpw.mods.fml.common.IWorldGenerator;
import java.util.Random;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

public class FoundryWorldGenerator implements IWorldGenerator {
    @Override
    public void generate(
            Random random,
            int chunkX,
            int chunkZ,
            World world,
            IChunkProvider chunkGenerator,
            IChunkProvider chunkProvider) {
        WordGenOre.Generate(random, chunkX, chunkZ, world, chunkGenerator, chunkProvider);
    }
}
