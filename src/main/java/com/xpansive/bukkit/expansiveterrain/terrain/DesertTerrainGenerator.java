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
        
        double height = (1 - Math.abs(noise.noise(worldX / 125.0, worldZ / 125.0))) * 15;
        height += noise.noise(worldX / 400.0, worldZ / 400.0) * 20;
        height += 78;
        
        height = Math.min(height, world.getMaxHeight() - 1);
        height = Math.floor(height);
        
        for (int y = 0; y <= height; y++) {
            if (y == height) {
                setBlock(chunkData, x, y, z, Material.SAND);
            } else {
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
