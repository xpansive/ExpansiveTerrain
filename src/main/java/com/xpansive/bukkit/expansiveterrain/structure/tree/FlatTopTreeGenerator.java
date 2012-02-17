package com.xpansive.bukkit.expansiveterrain.structure.tree;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.util.DirectWorld;
import com.xpansive.bukkit.expansiveterrain.util.RandomExt;

public class FlatTopTreeGenerator extends TreeGenerator {

    private final int minHeight, maxHeight, minRadius, maxRadius;
    private final double minBranchHeight, maxBranchHeight, vineChance;
    private final int[] vineXPositions, vineZPositions;

    public FlatTopTreeGenerator(WorldState state, String path) {
        super(state);
        FileConfiguration config = state.getConfig();
        minHeight = config.getInt(path + "minheight");
        maxHeight = config.getInt(path + "maxheight");
        minRadius = config.getInt(path + "minradius");
        maxRadius = config.getInt(path + "maxradius");
        vineChance = config.getDouble(path + "vinechance");
        minBranchHeight = config.getDouble(path + "minbranchheight");
        maxBranchHeight = config.getDouble(path + "maxbranchheight");
        vineXPositions = new int[maxRadius * 8];
        vineZPositions = new int[maxRadius * 8];
    }

    public boolean generate(int x, int y, int z) {
        DirectWorld world = state.getDirectWorld();
        RandomExt rand = state.getRandomExt();

        world.setForceChunkLoad(true);

        final int height = rand.randInt(minHeight, maxHeight);
        final int radius = rand.randInt(minRadius, maxRadius);

        // Simple bounds checking
        if (world.getMaterial(x, y - 1, z) != Material.GRASS || (world.getTypeId(x, y, z) | world.getTypeId(x, y + height, z) | world.getTypeId(x + radius, y + height, z) | world.getTypeId(x - radius, y + height, z) | world.getTypeId(x, y + height, z + radius) | world.getTypeId(x, y + height, z - radius)) != 0) {
            return false;
        }

        int vinePositionsIndex = 0;

        // Place the leaves
        for (int cx = -radius; cx < radius + 1; cx++) {
            for (int cz = -radius; cz < radius + 1; cz++) {
                for (int cy = 0; cy < radius / 3; cy++) {
                    final int dist = (int) Math.sqrt(Math.abs(cx * cx + cy * cy * 16 + cz * cz));
                    if (dist < radius) {
                        world.setRawMaterial(x + cx, y + height + cy, z + cz, Material.LEAVES);
                        if (cy == 0) { // Only updating lighting on the bottom leaves saves some time
                            world.updateLighting(x + cx, y + height, z + cz);
                        }

                    } else if (cy == 0 && dist == radius && rand.percentChance(vineChance)) {
                        vineXPositions[vinePositionsIndex] = cx;
                        vineZPositions[vinePositionsIndex++] = cz;
                    }
                }
            }
        }

        // Place the vines on the outside
        for (int i = 0; i < vinePositionsIndex; i++) {
            placeVine(x + vineXPositions[i], y + height, z + vineZPositions[i], rand.randInt(height));
        }

        // Make the branches
        int numBranches = radius;
        for (int i = 0; i < numBranches; i++) {
            double cx = 0, cz = 0;
            double angle = i / (double) numBranches * (Math.PI * 2) + rand.randDouble(0, 1);
            double xStep = Math.sin(angle);
            double zStep = Math.cos(angle);

            int branchLength = radius - 2;
            double branchHeight = rand.randDouble(minBranchHeight, maxBranchHeight);

            for (int j = 0; j < branchLength; j++) {
                cx += xStep;
                cz += zStep;
                // Make the branches rise up as they get longer
                int cy = y + Math.min((int) (height - (branchLength - j) / branchHeight + 1), height);
                world.setRawTypeIdAndData(x + (int) cx, cy, z + (int) cz, Material.LOG.getId(), 1);
                world.updateLighting(x + (int) cx, cy, z + (int) cz);

                if (rand.percentChance(vineChance)) {
                    placeVine(x + (int) cx + rand.randInt(-1, 1), cy, z + (int) cz + rand.randInt(-1, 1), rand.randInt(height));
                }
            }
        }

        world.setForceChunkLoad(false);

        // Make the trunk
        for (int cy = 0; cy < height + 1; cy++) {
            world.setRawTypeIdAndData(x, y + cy, z, Material.LOG.getId(), 1);
        }

        return true;
    }

    private void placeVine(int x, int y, int z, int height) {
        DirectWorld world = state.getDirectWorld();

        // We can't place vines if this block is filled
        if (world.getTypeId(x, y, z) != 0) {
            return;
        }

        // Don't rely on these direction names, they're probably off
        int data = 0;
        int west = world.getTypeId(x, y, z - 1);
        int east = world.getTypeId(x, y, z + 1);
        int south = world.getTypeId(x + 1, y, z);
        int north = world.getTypeId(x - 1, y, z);

        // Set the data based on the surrounding blocks
        if (east != 0 && east != Material.VINE.getId()) {
            data |= 1;
        }
        if (north != 0 && north != Material.VINE.getId()) {
            data |= 2;
        }
        if (west != 0 && west != Material.VINE.getId()) {
            data |= 4;
        }
        if (south != 0 && south != Material.VINE.getId()) {
            data |= 8;
        }

        // If data is 0 there are no blocks to attach to, so don't place a vine
        if (data != 0) {
            for (int cy = 0; cy < height && world.getTypeId(x, y - cy, z) == 0; cy++) {
                world.setRawTypeIdAndData(x, y - cy, z, Material.VINE.getId(), data);
            }
        }
    }
}
