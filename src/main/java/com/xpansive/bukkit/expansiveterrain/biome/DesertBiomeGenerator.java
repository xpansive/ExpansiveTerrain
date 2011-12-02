package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;

import com.xpansive.bukkit.expansiveterrain.Config;
import com.xpansive.bukkit.expansiveterrain.populator.*;
import com.xpansive.bukkit.expansiveterrain.structure.tree.*;
import com.xpansive.bukkit.expansiveterrain.terrain.*;

public class DesertBiomeGenerator implements BiomeGenerator {
    private final BlockPopulator[] populators;
    private final TerrainGenerator terrainGen = new DesertTerrainGenerator();

    public DesertBiomeGenerator(World world) {
        FileConfiguration config = Config.getConfig(world.getWorldFolder().getName());
        CactusPopulator cactus = new CactusPopulator(
                config.getInt("biome.desert.cactus.minheight"),
                config.getInt("biome.desert.cactus.maxheight"),
                config.getInt("biome.desert.cactus.patchradius"),
                config.getInt("biome.desert.cactus.patchchance"),
                config.getInt("biome.desert.cactus.newcactuschance"));
        TreePopulator tree = new TreePopulator(
                new Tree[] {
                        new Tree(
                                new PalmTreeGenerator(
                                        config.getInt("biome.desert.tree.palmtree.minheight"),
                                        config.getInt("biome.desert.tree.palmtree.maxheight")),
                                config.getInt("biome.desert.tree.palmtree.chanceperchunk"),
                                config.getInt("biome.desert.tree.palmtree.minperchunk"),
                                config.getInt("biome.desert.tree.palmtree.maxperchunk"))
                },
                config.getInt("biome.desert.tree.treetypesperchunk"));

        populators = new BlockPopulator[] {
                cactus, tree
        };
    }

    public BlockPopulator[] getPopulators() {
        return populators;
    }

    public TerrainGenerator getTerrainGenerator() {
        return terrainGen;
    }
}
