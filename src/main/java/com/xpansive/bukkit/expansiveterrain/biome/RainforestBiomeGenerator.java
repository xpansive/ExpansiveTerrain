package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.Material;
import org.bukkit.generator.BlockPopulator;

import com.xpansive.bukkit.expansiveterrain.populators.*;
import com.xpansive.bukkit.expansiveterrain.populators.tree.*;
import com.xpansive.bukkit.expansiveterrain.terrain.RainforestTerrainGenerator;
import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator;

public class RainforestBiomeGenerator extends BiomeGenerator {
    
    private final BlockPopulator[] populators = new BlockPopulator[] {
            new TreePopulator(
                    new Tree[] {
                            new Tree(
                                    new BushGenerator(
                                            1, // Min bush radius
                                            4, // Max bush radius
                                            75), // Log chance
                                    20, // Tree chance
                                    1, // Min per chunk
                                    1), // Max per chunk
                            new Tree(
                                    new FlatTopTreeGenerator(
                                            12, // Min height
                                            20, // Max height
                                            8, // Min radius
                                            12, // Max radius
                                            20), // Vine chance
                                    5, // Tree chance
                                    1, // Min per chunk
                                    1) // Max per chunk
                            }, 
                    1), //Number of different tree types to try planting per chunk
            new FlowerPopulator(
                    10, // Flower patch chance
                    95, // Chance of individual flowers
                    new Material[] { Material.RED_ROSE, Material.YELLOW_FLOWER }, 
                    20), // Number of flowers (max possible, could be less)
            new MelonPopulator(
                    7, // Chance of a melon patch out of 1000 
                    15, // Max number of melons in a patch
                    5), // Melon patch radius
            new PumpkinPopulator(
                    10, // Chance of a pumpkin patch out of 1000 
                    20, // Max number of pumpkins in a patch
                    5), // Pumpkin patch radius
    };
    
    private final TerrainGenerator terrainGen = new RainforestTerrainGenerator();

    @Override
    public BlockPopulator[] getPopulators() {
        return populators;
    }

    @Override
    public TerrainGenerator getTerrainGenerator() {
        return terrainGen;
    }

}
