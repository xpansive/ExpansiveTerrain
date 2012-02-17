package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.Material;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.populator.CactusPopulator;
import com.xpansive.bukkit.expansiveterrain.populator.ExpansiveTerrainPopulator;
import com.xpansive.bukkit.expansiveterrain.terrain.DesertTerrainGenerator;
import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator;

public class DesertBiomeGenerator extends BiomeGenerator {
    private final ExpansiveTerrainPopulator[] populators;
    private final TerrainGenerator terrainGen;

    public DesertBiomeGenerator(WorldState state) {
        super(state);
        terrainGen = new DesertTerrainGenerator(state);
        CactusPopulator cactus = new CactusPopulator(state, "biome.desert.cactus.");
        /*TreePopulator tree = new TreePopulator(
                new Tree[] {
                        new Tree(
                                new PalmTreeGenerator(
                                        config.getInt("biome.desert.tree.palmtree.minheight"),
                                        config.getInt("biome.desert.tree.palmtree.maxheight")),
                                config.getInt("biome.desert.tree.palmtree.chanceperchunk"),
                                config.getInt("biome.desert.tree.palmtree.minperchunk"),
                                config.getInt("biome.desert.tree.palmtree.maxperchunk"))
                },
                config.getInt("biome.desert.tree.treetypesperchunk"));*/

        populators = new ExpansiveTerrainPopulator[] { cactus };
    }

    public ExpansiveTerrainPopulator[] getPopulators() {
        return populators;
    }

    public TerrainGenerator getTerrainGenerator() {
        return terrainGen;
    }
    
    public boolean canSpawn(int x, int y, int z) {
        return state.getDirectWorld().getMaterial(x, y, z) == Material.SAND;
    }
}
