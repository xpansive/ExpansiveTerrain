package com.xpansive.bukkit.expansiveterrain.populator;

import com.xpansive.bukkit.expansiveterrain.GeneratorBase;
import com.xpansive.bukkit.expansiveterrain.WorldState;

public abstract class ExpansiveTerrainPopulator extends GeneratorBase {
    
    public ExpansiveTerrainPopulator(WorldState state) {
        super(state);
    }

    /**
     * Populate something at the specified coordinates.
     * 
     * @param chunkX
     *            The X coordinate of the chunk (in world coordinates) to populate.
     * @param chunkZ
     *            The X coordinate of the chunk (in world coordinates) to populate.
     */
    public abstract void populate(int chunkX, int chunkZ);
}
