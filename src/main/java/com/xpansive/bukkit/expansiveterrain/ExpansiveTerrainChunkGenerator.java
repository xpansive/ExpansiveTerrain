package com.xpansive.bukkit.expansiveterrain;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.*;
import com.xpansive.bukkit.expansiveterrain.util.VoronoiNoise;
import com.xpansive.bukkit.expansiveterrain.populators.*;

public class ExpansiveTerrainChunkGenerator extends ChunkGenerator {
	VoronoiNoise v;
	int lastRandom;

	public byte[] generate(World world, Random random, int cx, int cz) {

		if (v == null)
			v = new VoronoiNoise(random);

		byte[] result = new byte[32768];
		int[][] voronoiBuf = v.genChunks(cx * 16, cz * 16, 16, 16, 2);

		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int height = voronoiBuf[x][z] / 17;
				height *= Math.min(PerlinNoiseGenerator.getNoise(((double) (cx * 16 + x)) / 50,
						((double) (cz * 16 + z)) / 50, 3, 2, .7) + 1, 1);

				height += 32;

				height = Math.min(height, 127);
				int dirtHeight = random.nextInt(5) + 3;
				int snowHeight = random.nextInt(4);
				for (int y = 0; y <= height; y++) {
					int offset = getOffset(x, y, z);

					// cover the bottom layer with bedrock
					if (y == 0)
						result[offset] = (byte) Material.BEDROCK.getId();

					// top layer gets grass
					else if (y == (int) height)
						result[offset] = (byte) Material.GRASS.getId();

					else if (y > height - dirtHeight)
						result[offset] = (byte) Material.DIRT.getId();

					else
						result[offset] = (byte) Material.STONE.getId();

					if (y > 80 - snowHeight) {
						if (y + 1 < 128)
							result[getOffset(x, y + 1, z)] = (byte) Material.SNOW.getId();
					}
				}
			}
		}

		return result;

	}

	int getOffset(int x, int y, int z) {
		return (x * 16 + z) * 128 + y;
	}

	public boolean canSpawn(World world, int x, int z) {
		return true;
	}

	public List<BlockPopulator> getDefaultPopulators(World world) {
		return Arrays.asList((BlockPopulator) new PumpkinPopulator(), new FlowerPopulator(), new SandPopulator(),
				new TreePopulator(), new SnowPopulator(), new OrePopulator(), new MushroomPopulator(),
				new WildGrassPopulator());
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		int x = random.nextInt(250) - 250;
		int z = random.nextInt(250) - 250;
		int y = world.getHighestBlockYAt(x, z);
		return new Location(world, x, y, z);
	}
}
