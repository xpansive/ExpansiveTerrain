package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;

import com.xpansive.bukkit.expansiveterrain.populator.*;
import com.xpansive.bukkit.expansiveterrain.structure.tree.*;
import com.xpansive.bukkit.expansiveterrain.terrain.RainforestTerrainGenerator;
import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator;

public class RainforestBiomeGenerator implements BiomeGenerator {
    private final BlockPopulator[] populators;
    private final TerrainGenerator terrainGen = new RainforestTerrainGenerator();

    public RainforestBiomeGenerator(FileConfiguration config) {
        TreePopulator tree = new TreePopulator(
                new Tree[] {
                        new Tree(
                                new BushGenerator(
                                        config.getInt("biome.rainforest.tree.bush.minradius"),
                                        config.getInt("biome.rainforest.tree.bush.maxradius"),
                                        config.getInt("biome.rainforest.tree.bush.logchance")),
                                config.getInt("biome.rainforest.tree.bush.chanceperchunk"),
                                config.getInt("biome.rainforest.tree.bush.minperchunk"),
                                config.getInt("biome.rainforest.tree.bush.maxperchunk")),
                         new Tree(
                                 new FlatTopTreeGenerator(
                                         config.getInt("biome.rainforest.tree.flattoptree.minheight"),
                                         config.getInt("biome.rainforest.tree.flattoptree.maxheight"),
                                         config.getInt("biome.rainforest.tree.flattoptree.minradius"),
                                         config.getInt("biome.rainforest.tree.flattoptree.maxradius"),
                                         config.getInt("biome.rainforest.tree.flattoptree.vinechance")),
                                  config.getInt("biome.rainforest.tree.flattoptree.chanceperchunk"),
                                  config.getInt("biome.rainforest.tree.flattoptree.minperchunk"),
                                  config.getInt("biome.rainforest.tree.flattoptree.maxperchunk"))
                },
                config.getInt("biome.desert.tree.treetypesperchunk"));

        FlowerPopulator flower = new FlowerPopulator(
                config.getInt("biome.rainforest.flower.flowerpatchchance"),
                config.getInt("biome.rainforest.flower.flowerchance"),
                new Material[] { Material.RED_ROSE, Material.YELLOW_FLOWER }, 
                config.getInt("biome.rainforest.flower.flowernum"));

        MelonPopulator melon = new MelonPopulator(
                config.getInt("biome.rainforest.melon.chanceper1000"),
                config.getInt("biome.rainforest.melon.maxnum"),
                config.getInt("biome.rainforest.melon.radius"));

        PumpkinPopulator pumpkin = new PumpkinPopulator(
                config.getInt("biome.rainforest.pumpkin.chanceper1000"),
                config.getInt("biome.rainforest.pumpkin.maxnum"),
                config.getInt("biome.rainforest.pumpkin.radius"));

        populators = new BlockPopulator[] {
                tree, flower, melon, pumpkin
        };
    }

    public BlockPopulator[] getPopulators() {
        return populators;
    }

    public TerrainGenerator getTerrainGenerator() {
        return terrainGen;
    }

}
