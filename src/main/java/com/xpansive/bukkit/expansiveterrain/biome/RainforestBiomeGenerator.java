package com.xpansive.bukkit.expansiveterrain.biome;

import org.bukkit.Material;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.populator.*;
import com.xpansive.bukkit.expansiveterrain.structure.tree.*;
import com.xpansive.bukkit.expansiveterrain.terrain.RainforestTerrainGenerator;
import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator;

public class RainforestBiomeGenerator extends BiomeGenerator {
    private final ExpansiveTerrainPopulator[] populators;
    private final TerrainGenerator terrainGen;

    public RainforestBiomeGenerator(WorldState state) {
        super(state);
        
        terrainGen = new RainforestTerrainGenerator(state);
        
        RainforestTreePopulator tree = new RainforestTreePopulator(state,
                new Tree[] {
                         new Tree(state, new FlatTopTreeGenerator(state, "biome.rainforest.tree.flattoptree."), "biome.rainforest.tree.flattoptree."),
                         new Tree(state, new BushGenerator(state, "biome.rainforest.tree.bush."), "biome.rainforest.tree.bush.")
                });
        FlowerPopulator flower = new FlowerPopulator(state, "biome.rainforest.flower.");
        MelonPopulator melon = new MelonPopulator(state, "biome.rainforest.melon.");
        PumpkinPopulator pumpkin = new PumpkinPopulator(state, "biome.rainforest.pumpkin.");
        WildGrassPopulator wildGrass = new WildGrassPopulator(state, "biome.rainforest.wildgrass.");
        OrePopulator ore = new OrePopulator(state, "biome.rainforest.ore.");

        populators = new ExpansiveTerrainPopulator[] { flower, melon, pumpkin, wildGrass, tree, ore };
    }

    public ExpansiveTerrainPopulator[] getPopulators() {
        return populators;
    }

    public TerrainGenerator getTerrainGenerator() {
        return terrainGen;
    }
    
    public boolean canSpawn(int x, int y, int z) {
        return state.getDirectWorld().getMaterial(x, y, z) == Material.GRASS;
    }
}
