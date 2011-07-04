package com.xpansive.bukkit.worldgen;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.Material;
import org.bukkit.World;

public class ExpansiveTerrainChunkGenerator extends ChunkGenerator {
	Voronoi v = null;

	public byte[] generate(World world, Random random, int cx, int cz) {
		if (v == null)
			v = new Voronoi(4096, world.getSeed(),
					(int) Math.ceil(50f * (8192 / 512f)));

		byte[] result = new byte[32768];
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				double height = v.get((cx * 16 + x) * 8, (cz * 16 + z) * 8, 9,
						.2f);

				height = Math.max(height, -1);
				height = Math.min(height, 1);

				height = height * 48 + 64;
				int dirtHeight = random.nextInt(3) + 2;
				for (int y = (int) height; y >= 0; y--) {
					int offset = getOffset(x, y, z);

					// cover the bottom layer with bedrock
					if (y == 0)
						result[offset] = (byte) Material.BEDROCK.getId();

					else if (y < (int) height && y > height - dirtHeight)
						result[offset] = (byte) Material.DIRT.getId();

					// top layer gets grass
					else if (y == (int) height)
						result[offset] = (byte) Material.GRASS.getId();

					else
						result[offset] = (byte) Material.STONE.getId();
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
		return Arrays.asList((BlockPopulator) new TreePopulator());
	}

	@Override
	public Location getFixedSpawnLocation(World world, Random random) {
		int x = random.nextInt(250);
		int z = random.nextInt(250);
		int y = world.getHighestBlockYAt(x, z);
		return new Location(world, x, y, z);
	}
}
