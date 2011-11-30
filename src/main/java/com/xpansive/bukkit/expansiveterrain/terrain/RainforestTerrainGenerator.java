package com.xpansive.bukkit.expansiveterrain.terrain;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class RainforestTerrainGenerator extends TerrainGenerator {

    private SimplexNoiseGenerator noise;
    private boolean initalized = false;

    @Override
    public double getHeightMultiplier() {
        return 1;
    }

    @Override
    public void fillColumn(World world, Random random, int worldX, int worldZ, int x, int z, byte[] chunkData) {
        if (!initalized) {
            noise = new SimplexNoiseGenerator(random);
            initalized = true;
        }

        double height = 50;
        height += noise.noise(worldX / 200.0, worldZ / 200.0) * 12; // Gentle hills
        height += noise.noise(worldX / 50.0, worldZ / 50.0) * 4; // Small turbulence
        height += Math.pow(1.12, noise.noise(worldX / 200.0, worldZ / 200.0) * 39); // Exponential mountains

        height = Math.min(height, world.getMaxHeight() - 1);
        height = Math.floor(height);

        for (int y = 0; y <= height; y++) {
            if (y == height) {
                setBlock(chunkData, x, y, z, Material.GRASS);
            } else {
                setBlock(chunkData, x, y, z, Material.DIRT);
            }
        }
    }

}
