package com.xpansive.bukkit.expansiveterrain.structure;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;

public abstract class LargeStructureGenerator {
    public abstract void fillColumn(World world, Random rand, int worldX, int worldZ, int x, int z, byte[] chunkData);

    protected void setBlock(byte[] data, int x, int y, int z, Material type) {
        if (x < 16 && x >= 0 && z < 16 && z >= 0 && y < 128 && y >= 0)
            data[(x * 16 + z) * 128 + y] = (byte) type.getId();
    }
    
    protected Material getBlock(byte[] data, int x, int y, int z) {
        if (x < 16 && x >= 0 && z < 16 && z >= 0 && y < 128 && y >= 0)
            return Material.getMaterial(data[(x * 16 + z) * 128 + y]);
        return Material.AIR;
    }
}
