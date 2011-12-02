package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.generator.BlockPopulator;

import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator;

public interface BiomeGenerator {
    public BlockPopulator[] getPopulators();

    public TerrainGenerator getTerrainGenerator();
}
