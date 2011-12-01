package com.xpansive.bukkit.expansiveterrain.structure.tree;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

public class PalmTreeGenerator implements TreeGenerator {

    public boolean generate(World world, Random rand, int x, int y, int z) {

        // These trees must be planted on sand
        if (world.getBlockTypeIdAt(x, y - 1, z) != Material.GRASS.getId())
            return false;

        int height = 8;
        int numLeaves = 6;
        int numLeafSteps = 5;

        for (int cy = 0; cy < height; cy++) {
            Block b = world.getBlockAt(x, y + cy, z);
            b.setType(Material.LOG);
            b.setData((byte) (cy % 2)); // Alternate between pine and regular logs
        }

        for (int i = 0; i < numLeaves; i++) {
            double cx = 0;
            double cz = 0;

            // Determine the direction
            double xStep = Math.sin(i + rand.nextDouble()) * 0.75;
            double zStep = Math.cos(i + rand.nextDouble()) * 0.75;

            for (int j = 0; j < numLeafSteps; j++) {
                cx += xStep;
                cz += zStep;
                Block b = world.getBlockAt(x + (int) cx, y + height - j / 2, z + (int) cz);
                b.setType(Material.LEAVES);
            }
        }

        return true;
    }
}
