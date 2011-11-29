package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.generator.BlockPopulator;

import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator;

public class DesertBiomeGenerator extends BiomeGenerator {

    @Override
    public BlockPopulator[] getPopulators() {
        return new BlockPopulator[0];
    }

    @Override
    public TerrainGenerator getTerrainGenerator() {
        return null;
    }

}
