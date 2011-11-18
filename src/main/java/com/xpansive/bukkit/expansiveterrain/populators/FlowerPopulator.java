package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

public class FlowerPopulator extends BlockPopulator {

	private final int NUM_STEPS = 10;

	@Override
	public void populate(World world, Random random, Chunk source) {
		if (random.nextInt(100) < 7) { // 7% chance
			int x = (source.getX() << 4) + random.nextInt(16);
			int z = (source.getZ() << 4) + random.nextInt(16);
			Material flowerType = random.nextBoolean() ? Material.RED_ROSE : Material.YELLOW_FLOWER;

			for (int i = 0; i < NUM_STEPS; i++) {
				x += random.nextInt(3) - 1;
				z += random.nextInt(3) - 1;
				int y = world.getHighestBlockYAt(x, z);
				if (world.getBlockAt(x, y - 1, z).getType() == Material.GRASS
						&& world.getBlockAt(x, y - 1, z).getBiome() != Biome.DESERT && random.nextInt(100) > 5) {
					setBlock(world, x, y, z, flowerType);
				}
			}
		}
	}

	private static void setBlock(World w, int x, int y, int z, Material m) {
		w.getBlockAt(x, y, z).setType(m);
	}
}
