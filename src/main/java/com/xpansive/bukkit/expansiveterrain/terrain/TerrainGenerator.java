package com.xpansive.bukkit.expansiveterrain.terrain;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

public abstract class TerrainGenerator {
    public abstract void fillColumn(World world, Random random, int worldX, int worldZ, int x, int z, byte[] chunkData);

    protected void setBlock(byte[] data, int x, int y, int z, Material type) {
        data[(x * 16 + z) * 128 + y] = (byte) type.getId();
    }

    protected double calculateHeightInfluence(World world, int x, int z) {
        int r = 5;
        int[][] biome = new int[r * 2 + 1][r * 2 + 1];
        int lastBiomeValue = 0;
        Biome lastBiome = null;
        for (int cx = -r; cx <= r; cx++) {
            for (int cz = -r; cz <= r; cz++) {
                Biome worldBiome = world.getBiome(x + cx, z + cz);
                if (lastBiome != worldBiome) {
                    lastBiome = worldBiome;
                    lastBiomeValue = 1 - lastBiomeValue;
                }
                biome[cx + r][cz + r] = lastBiomeValue;
            }
        }
        double total = 0;
        for (int cx = -r; cx <= r; cx++) {
            for (int cz = -r; cz <= r; cz++) {
                 total += biome[cx + r][cz + r];
            }
        }
        total /= (r * 2 + 1) * (r * 2 + 1); 

        double height = Math.abs(0.5 - total) * 2.0;
        return height;
    }
}
