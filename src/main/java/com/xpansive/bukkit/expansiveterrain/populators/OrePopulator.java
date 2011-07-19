package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class OrePopulator extends BlockPopulator {
	
	private final int DEPOSITS_PER_CHUNK = 100; //this is the max, its usually less than this

	private final Material[] CHANGEABLE_BLOCKS = new Material[] {
			Material.STONE, Material.DIRT };

	private final Material[] ORES = new Material[] { Material.COAL_ORE,
			Material.IRON_ORE, Material.GOLD_ORE, Material.DIAMOND_ORE,
			Material.REDSTONE_ORE, Material.LAPIS_ORE };

	private final int[] MAX_DEPOSIT_SIZE = new int[] { 
			4, /* Coal */ 3, /* Iron */ 3, /* Gold */
			2, /* Diamond */ 3, /* Redstone */ 2, /* Lapis */
	};

	private final int[] MAX_LEVEL = new int[] { 
			128, /* Coal */ 35, /* Iron */ 20, /* Gold */
			10, /* Diamond */ 15, /* Redstone */ 10, /* Lapis */
	};

	@Override
	public void populate(World world, Random random, Chunk source) {
		for (int i = 0; i < DEPOSITS_PER_CHUNK; i++) {
			int x = (source.getX() << 4) + random.nextInt(16);
			int z = (source.getZ() << 4) + random.nextInt(16);
			int y = random.nextInt(world.getHighestBlockYAt(x, z));

			int index = random.nextInt(ORES.length);
			int xsize = random.nextInt(MAX_DEPOSIT_SIZE[index] + 1);
			int ysize = random.nextInt(MAX_DEPOSIT_SIZE[index] + 1);
			int zsize = random.nextInt(MAX_DEPOSIT_SIZE[index] + 1);
			Material ore = ORES[index];

			if (y > MAX_LEVEL[index])
				continue;

			for (int cx = x; cx < x + xsize; cx++) {
				for (int cy = y; cy < y + ysize; cy++) {
					for (int cz = z; cz < z + zsize; cz++) {
						Block type = world.getBlockAt(cx, cy, cz);
						boolean placeOre = false;
						for (Material mat : CHANGEABLE_BLOCKS)
							placeOre |= mat.getId() == type.getTypeId();

						if (!placeOre || random.nextDouble() > .5)
							continue;

						setBlock(world, cx, cy, cz, ore);
					}
				}
			}
		}
	}

	private void setBlock(World w, int x, int y, int z, Material m) {
		w.getBlockAt(x, y, z).setType(m);
	}
}
