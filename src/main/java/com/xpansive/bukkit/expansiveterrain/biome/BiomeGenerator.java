package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator;

public abstract class BiomeGenerator {
    
    private static final BiomeGenerator RAINFOREST = new RainforestBiomeGenerator();
    private static final BiomeGenerator DESERT = new DesertBiomeGenerator();
    
    public static BiomeGenerator getForBiome(Biome biome) {
        switch(biome) {
            case FOREST: // Because of 1.8, there are no longer rainforest biomes. Use forest instead.
                return RAINFOREST;
            case DESERT:
                return DESERT;
            default:
                return DESERT; //TODO: Set generator for unhandled biomes
        }
    }
    
    public abstract BlockPopulator[] getPopulators();
    
    public abstract TerrainGenerator getTerrainGenerator();
    
}
