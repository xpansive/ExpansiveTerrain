package com.xpansive.bukkit.worldgen;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.Location;

public class TreePopulator extends BlockPopulator {

	public void populate(World world, Random random, Chunk source) {
		int x = (source.getX() << 4) + random.nextInt(16);
		int z = (source.getZ() << 4) + random.nextInt(16);
		int y = world.getHighestBlockYAt(x, z);
		
		if (random.nextInt(128) - y <= 64) { //less chance of trees higher up
			world.generateTree(new Location(world, x, y, z), TreeType.BIG_TREE);

		}
	}
}
