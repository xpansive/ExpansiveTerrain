package com.xpansive.bukkit.expansiveterrain.terrain;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.*;

public class DesertTerrainGenerator extends TerrainGenerator {
    private boolean initalized = false;
    private NoiseGenerator noise;

    @Override
    public void fillColumn(World world, Random random, int worldX, int worldZ, int x, int z, byte[] chunkData) {
        if (!initalized) {
            noise = new SimplexNoiseGenerator(random);
            initalized = true;
        }
        
        int seaLevel = 55;
        
        double height = (1 - Math.abs(noise.noise(worldX / 125.0, worldZ / 125.0))) * 18; // Ridges/dunes
        height *= Math.min(1, noise.noise(worldX / 250.0, worldZ / 250.0) + 1); // To make the dunes fade in and out
        height += noise.noise(worldX / 400.0, worldZ / 400.0) * 20 + 15; // Gradual slope
        height += seaLevel;
        
        height = Math.min(height, world.getMaxHeight() - 1);
        height = Math.floor(height);
        
        for (int y = 0; y <= Math.max(height, seaLevel); y++) {
            if (y == height) {
                if (y >= seaLevel && y <= seaLevel + 1)
                    setBlock(chunkData, x, y, z, Material.GRASS);
                else if (y < seaLevel)
                    setBlock(chunkData, x, y, z, Material.DIRT);
                else 
                    setBlock(chunkData, x, y, z, Material.SAND);
            } else if (y <= seaLevel && y > height) {
                setBlock(chunkData, x, y, z, Material.STATIONARY_WATER);
            } else if (y < height) {
                setBlock(chunkData, x, y, z, Material.SANDSTONE);
            }
        }
    }

    @Override
    public double getHeightMultiplier() {
        // TODO Auto-generated method stub
        return 0;
    }

}
