package com.xpansive.bukkit.expansiveterrain.populators.tree;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;

public class BushGenerator implements TreeGenerator {

    private int minSize, maxSize, logChance;

    public BushGenerator(int minSize, int maxSize, int logChance) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.logChance = logChance;
    }

    public boolean generate(World bukkitWorld, Random rand, int x, int y, int z) {
        // Use NMS code for speed
        net.minecraft.server.World world = ((CraftWorld) bukkitWorld).getHandle();

        int radius = rand.nextInt(maxSize - minSize + 1) + minSize;

        for (int cx = -radius; cx < radius; cx++) {
            for (int cz = -radius; cz < radius; cz++) {
                int dist2d = (int) Math.sqrt(cx * cx + cz * cz);
                if (dist2d <= radius) { // Create the base
                    // If this block is filled, go up until we find one that isn't
                    if (world.getTypeId(x + cx, y, z + cz) != 0) {
                        int cy;
                        for (cy = y; world.getTypeId(x + cx, cy, z + cz) != 0 && cy < world.height; cy++)
                            ;
                        y = cy;
                    }
                    // If we have room to move downwards, do so
                    else if (world.getTypeId(x + cx, y - 1, z + cz) == 0) {
                        int cy;
                        // Iterate until we hit a solid block or we're at the bottom of the world
                        for (cy = y - 1; world.getTypeId(x + cx, cy, z + cz) == 0 && cy > 0; cy--)
                            ;
                        y = cy + 1;
                    }

                    if (world.getTypeId(x + cx, y - 1, z + cz) == Material.GRASS.getId()) {
                        // Logs are placed from the middle of the bush to halfway out
                        if (dist2d < radius / 2 && rand.nextInt(100) < logChance) {
                            world.setTypeId(x + cx, y, z + cz, Material.LOG.getId());
                        } else {
                            world.setTypeId(x + cx, y, z + cz, Material.LEAVES.getId());
                        }
                    }
                }
                for (int cy = 0; cy < radius; cy++) { // Fill in the top
                    int dist3d = (int) Math.sqrt(cx * cx + cz * cz + cy * cy);
                    if (dist3d < radius && world.getTypeId(x + cx, y + cy, z + cz) == 0) {
                        // Logs are placed from the middle of the bush to halfway out
                        if (dist3d < radius / 2 && rand.nextInt(100) < logChance) {
                            world.setTypeId(x + cx, y + cy, z + cz, Material.LOG.getId());
                        } else {
                            world.setTypeId(x + cx, y + cy, z + cz, Material.LEAVES.getId());
                        }
                    }
                }
            }
        }
        return true;
    }
}
