package com.xpansive.bukkit.expansiveterrain.structure;

import org.bukkit.Material;

import com.xpansive.bukkit.expansiveterrain.WorldState;

public class LakeGenerator extends LargeStructureGenerator {
    
    private int seaLevel;
    
    public LakeGenerator(WorldState state) {
        super(state);
        seaLevel = state.getConfig().getInt("world.sealevel");
    }

    @Override
    public void fillColumn(int worldX, int worldZ, int x, int z, byte[] chunkData) {
        for (int y = 0; y < seaLevel; y++) {
            if (getBlock(chunkData, x, y, z) == Material.AIR) {
                setBlock(chunkData, x, y, z, Material.STATIONARY_WATER);
                // The grass dies anyway, kill it now to prevent lag
                if (getBlock(chunkData, x, y - 1, z) == Material.GRASS) {
                    setBlock(chunkData, x, y - 1, z, Material.DIRT);
                }
            }
        }
    }
}
