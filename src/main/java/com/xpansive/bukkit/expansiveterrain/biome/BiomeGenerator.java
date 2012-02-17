package com.xpansive.bukkit.expansiveterrain.biome;

import com.xpansive.bukkit.expansiveterrain.GeneratorBase;
import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.populator.ExpansiveTerrainPopulator;
import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator;

public abstract class BiomeGenerator extends GeneratorBase {
    
    protected BiomeGenerator(WorldState state) {
        super(state);
    }

    public abstract ExpansiveTerrainPopulator[] getPopulators();

    public abstract TerrainGenerator getTerrainGenerator();
    
    public abstract boolean canSpawn(int x, int y, int z);
}
