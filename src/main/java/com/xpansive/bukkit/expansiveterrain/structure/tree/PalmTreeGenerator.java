package com.xpansive.bukkit.expansiveterrain.structure.tree;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import com.xpansive.bukkit.expansiveterrain.WorldState;

public class PalmTreeGenerator extends TreeGenerator {

    private int minHeight, maxHeight;

    public PalmTreeGenerator(WorldState state, int minHeight, int maxHeight) {
        super(state);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public boolean generate(int x, int y, int z) {
        return false;/*
        // These trees must be planted on grass
        if (world.getBlockTypeIdAt(x, y - 1, z) != Material.GRASS.getId())
            return false;

        int height = rand.nextInt(maxHeight - minHeight + 1) + minHeight;
        int numLeaves = (int) (height / 1.5);
        int numLeafSteps = height / 2;

        // Do some simple bounds checking
        if (world.getBlockTypeIdAt(x, y, z) != 0 || // The base block
                world.getBlockTypeIdAt(x, y + height, z) != 0 || // The top stem block
                world.getBlockTypeIdAt(x + numLeafSteps, y + height, z) != 0 || // The max possible x
                world.getBlockTypeIdAt(x - numLeafSteps, y + height, z) != 0 || // The min possible x
                world.getBlockTypeIdAt(x, y + height, z + numLeafSteps) != 0 || // The max possible z
                world.getBlockTypeIdAt(x, y + height, z - numLeafSteps) != 0) // The min possible z
            return false;

        // Place the trunk
        for (int cy = 0; cy < height; cy++) {
            Block b = world.getBlockAt(x, y + cy, z);
            if (b.getTypeId() == 0)
                b.setTypeIdAndData(Material.LOG.getId(), (byte) (cy % 2), false); // Alternate between pine and regular logs
        }

        for (int i = 0; i < numLeaves; i++) {
            double cx = 0;
            double cz = 0;

            // Determine the direction of the leaf
            double xStep = Math.sin(i + rand.nextDouble()) * 0.75;
            double zStep = Math.cos(i + rand.nextDouble()) * 0.75;

            for (int j = 0; j < numLeafSteps; j++) {
                // Move the position
                cx += xStep;
                cz += zStep;
                // Place the leaves
                Block b = world.getBlockAt(x + (int) cx, y + height - j / (numLeafSteps / 3), z + (int) cz);
                if (b.getTypeId() == 0)
                    b.setType(Material.LEAVES);
            }
        }

        return true;*/
    }
}
