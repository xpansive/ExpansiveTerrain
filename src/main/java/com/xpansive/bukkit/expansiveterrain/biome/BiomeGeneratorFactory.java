package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;

public class BiomeGeneratorFactory {
    private BiomeGenerator RAINFOREST;
    private BiomeGenerator DESERT;

    public BiomeGeneratorFactory(FileConfiguration config) {
        DESERT = new DesertBiomeGenerator(config);
        RAINFOREST = new RainforestBiomeGenerator(config);
    }

    public BiomeGenerator getForBiome(Biome biome) {
        switch (biome) { // This only looks strange while it's in beta :)
        case FOREST: // Since 1.8, there are no longer rainforest biomes. Use forest instead.
            return RAINFOREST;
        case DESERT:
        case OCEAN: // Hate the giant oceans? Now they're giant deserts!
            return DESERT;
        default:
            return RAINFOREST; // TODO: Set generator for unhandled biomes
        }
    }
}
