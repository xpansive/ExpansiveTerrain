package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class PumpkinPopulator extends BlockPopulator {

    int chancePer1000, numPumpkins, depositRadius;

    public PumpkinPopulator(int chancePer1000, int numPumpkins, int depositRadius) {
        this.chancePer1000 = chancePer1000;
        this.numPumpkins = numPumpkins;
        this.depositRadius = depositRadius;
    }

    @Override
    public void populate(World world, Random random, Chunk source) {
        // Check if we should place a pumpkin patch on this chunk
        if (random.nextInt(1000) < chancePer1000) {
            int x = (source.getX() << 4) + random.nextInt(16);
            int z = (source.getZ() << 4) + random.nextInt(16);

            for (int i = 0; i < random.nextInt(numPumpkins); i++) {
                // Pick a random spot within the radius
                int cx = x + random.nextInt(depositRadius * 2) - depositRadius;
                int cz = z + random.nextInt(depositRadius * 2) - depositRadius;
                int y = world.getHighestBlockYAt(cx, cz);

                // If it's grass, place a pumpkin on top
                if (world.getBlockAt(cx, y - 1, cz).getType() == Material.GRASS)
                    world.getBlockAt(cx, y, cz).setType(Material.PUMPKIN);
            }
        }
    }
}
