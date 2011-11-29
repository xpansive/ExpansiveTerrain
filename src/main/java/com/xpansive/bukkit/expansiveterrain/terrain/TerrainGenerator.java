package com.xpansive.bukkit.expansiveterrain.terrain;

import java.util.Random;

import org.bukkit.World;

public abstract class TerrainGenerator {
    public abstract void fillColumn(World world, Random random, int cx, int cz, int x, int z, byte[] chunkData);
    
    public abstract double getHeightMultiplier();
    
    public static int getChunkDataOffset(int x, int y, int z) {
        return (x * 16 + z) * 128 + y;
    }
}
