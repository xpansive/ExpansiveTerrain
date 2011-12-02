package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.World;
import org.bukkit.block.Biome;

public class BiomeGeneratorFactory {
    private BiomeGenerator RAINFOREST;
    private BiomeGenerator DESERT;

    public BiomeGeneratorFactory(World world) {
        DESERT = new DesertBiomeGenerator(world);
        RAINFOREST = new RainforestBiomeGenerator(world);
    }

    public BiomeGenerator getForBiome(Biome biome) {
        switch (biome) { // This only looks strange while it's in beta :)
        case FOREST: // Since 1.8, there are no longer rainforest biomes. Use forest instead.
            return RAINFOREST;
        case DESERT:
            return DESERT;
        default:
            return RAINFOREST; // TODO: Set generator for unhandled biomes
        }
    }
}
