package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class RandomDistributionPopulator extends BlockPopulator {
	int NUM_BLOCKS = 0;
	int SIZE = 0;
	int CHANCE_PER_1000 = 0;
	Material BLOCK_TYPE = Material.AIR;
	int BLOCK_DATA = 1; //Random between 0 and this value
	Material UNDER_BLOCK_TYPE = Material.AIR;

	public void randomDistribution(World world, Random random, Chunk source) {
		if (random.nextInt(1000) < CHANCE_PER_1000) {
            int x = (source.getX() << 4);
            int z = (source.getZ() << 4);
            for (int i = 0; i < random.nextInt(NUM_BLOCKS); i++) {
                int cx = x + random.nextInt(SIZE);
                int cz = z + random.nextInt(SIZE);
                int y = world.getHighestBlockYAt(cx, cz);
                
                if (world.getBlockAt(cx, y - 1, cz).getType() == UNDER_BLOCK_TYPE) {
                	world.getBlockAt(cx, y, cz).setType(BLOCK_TYPE);
                	
                	if (BLOCK_DATA > 0)
                		world.getBlockAt(cx, y, cz).setData((byte) random.nextInt(BLOCK_DATA));
                }
            }
        }
	}

	@Override
	public void populate(World arg0, Random arg1, Chunk arg2) {
	}

}
