package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

public class CactusPopulator extends BlockPopulator {

	@Override
	public void populate(World world, Random random, Chunk source) {
		boolean plantNewCactus = random.nextInt(100) < 30;
		int x = (source.getX() << 4) + random.nextInt(16);
		int z = (source.getZ() << 4) + random.nextInt(16);
		while (plantNewCactus) {
			x += random.nextInt(10) - 5;
			z += random.nextInt(10) - 5;
			if (world.getBiome(x, z) == Biome.DESERT) {
				int y = world.getHighestBlockYAt(x, z);
				Block b = world.getBlockAt(x, y, z);
				
				if (b.getRelative(BlockFace.DOWN).getType() == Material.SAND) {
					int height = random.nextInt(2) + 2; // 2 - 3
					
					for (int i = 0; i < height; i++) {
						if (b.getRelative(BlockFace.NORTH).getTypeId() == 0 &&
								b.getRelative(BlockFace.EAST).getTypeId() == 0 &&
								b.getRelative(BlockFace.SOUTH).getTypeId() == 0 &&
								b.getRelative(BlockFace.WEST).getTypeId() == 0)
						b.getRelative(0, i, 0).setType(Material.CACTUS);
					}
				}
			}
			plantNewCactus = random.nextInt(100) < 80;
		}
	}

}
