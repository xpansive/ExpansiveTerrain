package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.Location;

public class TreePopulator extends BlockPopulator {

	private final int SHRUB_RADUIS = 4;

	public void populate(World world, Random random, Chunk source) {
		int x = (source.getX() << 4) + random.nextInt(16);
		int z = (source.getZ() << 4) + random.nextInt(16);
		int y = world.getHighestBlockYAt(x, z);

		if (random.nextInt(128) - y <= 64) { // less chance of trees higher up
			for (int cx = x - SHRUB_RADUIS; cx < x + SHRUB_RADUIS; cx++) {
				for (int cz = z - SHRUB_RADUIS; cz < z + SHRUB_RADUIS; cz++) {
					Block b = world.getBlockAt(cx, world.getHighestBlockYAt(cx, cz), cz);
					if (random.nextInt(100) < 8 && cx != x && cz != z
							&& b.getRelative(0, -1, 0).getType() == Material.GRASS) {
						b.setType(Material.LONG_GRASS);
							
						b.setData((byte)(random.nextInt(100) < 3 ? 0 : 2));
						
					}
				}
			}
			world.generateTree(new Location(world, x, y, z), TreeType.BIG_TREE);
		}
	}
}
