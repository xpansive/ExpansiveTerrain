package com.xpansive.bukkit.expansiveterrain.structure;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class CaveGenerator extends LargeStructureGenerator {
    private final SimplexNoiseGenerator[] layers;
    private int numLayers, seaLevel;
    private double density;

    public CaveGenerator(Random random, double density, int seaLevel, int numLayers) {
        this.numLayers = numLayers;
        this.seaLevel = seaLevel;
        this.density = density;
        layers = new SimplexNoiseGenerator[numLayers];
        for (int i = 0; i < numLayers; i++)
            layers[i] = new SimplexNoiseGenerator(random);
    }

    @Override
    public void fillColumn(World world, Random rand, int worldX, int worldZ, int x, int z, byte[] chunkData) {
        int halfSeaLevel = seaLevel / 2;
        for (int i = 0; i < numLayers; i++) {
            SimplexNoiseGenerator noise = layers[i];
            int height = (int) (noise.noise(worldX / 100.0, worldZ / 100.0) * halfSeaLevel + halfSeaLevel); // The level the cave will be placed on
            double threshold = noise.noise(worldX / 20.0, worldZ / 20.0) * 0.3 + 0.35; // This threshold determines the width of the cave
            double cave = Math.abs(noise.noise(worldX / 50.0, worldZ / 50.0)) + height / 768.0; // The cave density, decreasing as Y increases
            boolean placeCave = cave < threshold; // Whether we should place a cave here (cave < threshold)
            placeCave &= noise.noise(worldX / 125.0, worldZ / 125.0) < density; // Make some areas not have caves
            double caveHeightMul = noise.noise(worldX / 75.0, worldZ / 75.0) * 8 + 10; // The cave height multiplier
            double caveHeight = (1 - cave) * caveHeightMul; // The cave height

            if (placeCave) {
                // Clear out the cave
                for (int y = 0; y < caveHeight; y++) {
                    if (y / caveHeightMul - cave > threshold) { // Make it more rounded
                        setBlock(chunkData, x, height + y, z, Material.AIR);
                    }
                }
                // Replace dirt with air above it with grass
                for (int y = 0; y < caveHeight; y++) {
                    if (getBlock(chunkData, x, height + y - 1, z) == Material.DIRT && getBlock(chunkData, x, height + y, z) == Material.AIR) {
                        setBlock(chunkData, x, height + y - 1, z, Material.GRASS);
                    }
                }
            }
        }
    }
}
