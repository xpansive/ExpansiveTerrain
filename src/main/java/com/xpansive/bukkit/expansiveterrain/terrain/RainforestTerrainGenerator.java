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
    public void fillColumn(World world, Random random, int cx, int cz, int x, int z, byte[] chunkData) {
        if (!initalized) {
            noise = new SimplexNoiseGenerator(random);
            initalized = true;
        }

        double height = 50;
        height += noise.noise((cx * 16 + x) / 200.0, (cz * 16 + z) / 200.0) * 12; // Gentle hills
        height += noise.noise((cx * 16 + x) / 50.0, (cz * 16 + z) / 50.0) * 4; // Small turbulence
        height += Math.pow(1.12, noise.noise((cx * 16 + x) / 200.0, (cz * 16 + z) / 200.0) * 39); // Exponential mountains

        height = Math.min(height, world.getMaxHeight() - 1);
        height = Math.floor(height);

        for (int y = 0; y <= height; y++) {
            if (y == height) {
                chunkData[getChunkDataOffset(x, y, z)] = (byte) Material.GRASS.getId();
            } else {
                chunkData[getChunkDataOffset(x, y, z)] = (byte) Material.DIRT.getId();
            }
        }
    }

}
