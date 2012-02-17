package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.block.Biome;

import com.xpansive.bukkit.expansiveterrain.WorldState;

public class BiomeGeneratorFactory {
    private final BiomeGenerator rainforest;
    private final BiomeGenerator desert;

    public BiomeGeneratorFactory(WorldState state) {
        desert = new DesertBiomeGenerator(state);
        rainforest = new RainforestBiomeGenerator(state);
    }

    public BiomeGenerator getForBiome(Biome biome) {
        switch(biome) {
        case DESERT:
        case DESERT_HILLS:
            return desert;
        default:
            return rainforest;
        }
    }
}
