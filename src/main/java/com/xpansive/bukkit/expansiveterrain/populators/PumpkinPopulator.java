package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class PumpkinPopulator extends BlockPopulator {

	private final int SIZE = 10;

	@Override
	public void populate(World world, Random random, Chunk source) {
		if (random.nextInt(200) < 1) { // 0.5% chance
			int x = (source.getX() << 4) + random.nextInt(16);
			int z = (source.getZ() << 4) + random.nextInt(16);

			for (int cx = x; cx < x + SIZE; cx++) {
				for (int cz = z; cz < z + SIZE; cz++) {
					if (random.nextInt(100) < 10) { // 10% chance of a pumpkin
						int y = world.getHighestBlockYAt(cx, cz);
						if (world.getBlockAt(cx, y - 1, cz).getType() == Material.GRASS) {
							world.getBlockAt(cx, y, cz).setType(
									Material.PUMPKIN);
							world.getBlockAt(cx, y, cz).setData(
									(byte) random.nextInt(4));
						}
					}
				}
			}
		}
	}
}
