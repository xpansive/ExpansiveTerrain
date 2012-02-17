package com.xpansive.bukkit.expansiveterrain.structure;

import org.bukkit.Material;

import com.xpansive.bukkit.expansiveterrain.GeneratorBase;
import com.xpansive.bukkit.expansiveterrain.WorldState;

public abstract class LargeStructureGenerator extends GeneratorBase {

    public LargeStructureGenerator(WorldState state) {
        super(state);
    }

    public abstract void fillColumn(int worldX, int worldZ, int x, int z, byte[] chunkData);

    protected void setBlock(byte[] data, int x, int y, int z, Material type) {
        if (inBounds(x, y, z)) {
            data[(x * 16 + z) * state.getNmsWorld().height + y] = (byte) type.getId();
        }
    }

    protected Material getBlock(byte[] data, int x, int y, int z) {
        if (inBounds(x, y, z)) {
            return Material.getMaterial(data[(x * 16 + z) * state.getNmsWorld().height + y]);
        }
        return Material.AIR;
    }

    private boolean inBounds(int x, int y, int z) {
        return (x & 15) == x && (z & 15) == z && y < state.getNmsWorld().height && y >= 0;
    }
}
