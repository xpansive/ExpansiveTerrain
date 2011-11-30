package com.xpansive.bukkit.expansiveterrain.populator;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class FlowerPopulator extends BlockPopulator {

    private int flowerPatchChance, plantFlowerChance, numSteps;
    private Material[] flowers;

    public FlowerPopulator(int flowerPatchChance, int plantFlowerChance,
            Material[] flowers, int numSteps) {
        this.flowerPatchChance = flowerPatchChance;
        this.plantFlowerChance = plantFlowerChance;
        this.flowers = flowers;
        this.numSteps = numSteps;
    }

    @Override
    public void populate(World world, Random random, Chunk source) {
        // Check if we should plant a flower patch here
        if (random.nextInt(100) < flowerPatchChance) {
            int x = (source.getX() << 4) + random.nextInt(16);
            int z = (source.getZ() << 4) + random.nextInt(16);

            // Retrieve a random block type from the list of flower types
            // The flowers array can be weighted if the generator wants it
            Material flowerType = flowers[random.nextInt(flowers.length)];

            for (int i = 0; i < numSteps; i++) {
                // Move both the x and z by a random amount between -1 and 1
                x += random.nextInt(3) - 1;
                z += random.nextInt(3) - 1;
                int y = world.getHighestBlockYAt(x, z);

                // Make sure we're planting it on grass
                if (world.getBlockAt(x, y - 1, z).getType() == Material.GRASS
                        && random.nextInt(100) < plantFlowerChance) {
                    world.getBlockAt(x, y, z).setType(flowerType);
                }
            }
        }
    }
}
