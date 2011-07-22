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

	private final int SHRUB_NUM = 4;
	private final int SHRUB_RADIUS = 4;

	public void populate(World world, Random random, Chunk source) {
		int x = (source.getX() << 4) + random.nextInt(16);
		int z = (source.getZ() << 4) + random.nextInt(16);
		int y = world.getHighestBlockYAt(x, z);

		if (random.nextInt(128) - y <= 64) { // less chance of trees higher up
			for (int i = 0; i < SHRUB_NUM; i++) {
				int cx = x + (-SHRUB_RADIUS + (int) (random.nextDouble() * ((SHRUB_RADIUS - -SHRUB_RADIUS) + 1)));
				int cz = z + (-SHRUB_RADIUS + (int) (random.nextDouble() * ((SHRUB_RADIUS - -SHRUB_RADIUS) + 1)));

				Block b = world.getBlockAt(cx, world.getHighestBlockYAt(cx, cz), cz);
				if (cx != x && cz != z // not where the tree goes
						&& b.getRelative(0, -1, 0).getType() == Material.GRASS) {
					b.setType(Material.LONG_GRASS);

					b.setData((byte) (random.nextInt(100) < 3 ? 0 : 2));
				}
			}
			int treeType = random.nextInt(100);
			
			if (treeType < 15) {
				world.generateTree(new Location(world, x, y, z), TreeType.BIRCH);
			}
			else if (treeType < 30) {
				world.generateTree(new Location(world, x, y, z), TreeType.TREE);
			}
			else {
				world.generateTree(new Location(world, x, y, z), TreeType.BIG_TREE);
			}
		}
	}
}
