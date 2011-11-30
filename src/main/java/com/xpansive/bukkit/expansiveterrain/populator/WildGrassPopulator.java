package com.xpansive.bukkit.expansiveterrain.populator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class WildGrassPopulator extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk source) {
        int x = (source.getX() << 4);
        int z = (source.getZ() << 4);
        int y = world.getHighestBlockYAt(x, z);
        int numSteps = 0;

        switch (world.getBlockAt(x, y, z).getBiome()) {
        case RAINFOREST:
            numSteps = 15;
            break;
        case SEASONAL_FOREST:
            numSteps = 5;
            break;
        case FOREST:
            numSteps = 10;
            break;
        case TAIGA:
            numSteps = 5;
            break;
        case PLAINS:
            numSteps = 50;
            break;
        }

        numSteps = random.nextInt(numSteps + 1);

        for (int i = 0; i < numSteps; i++) {
            x += random.nextInt(3) - 1;
            z += random.nextInt(3) - 1;
            y = world.getHighestBlockYAt(x, z);
            Block b = world.getBlockAt(x, y, z);

            if (b.getRelative(0, -1, 0).getType() == Material.GRASS) {
                b.setType(Material.LONG_GRASS);
                b.setData((byte) 1);
            }
        }
    }

}
