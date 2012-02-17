package com.xpansive.bukkit.expansiveterrain.populator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.biome.BiomeGenerator;

//This populator will call all of the others based on biome
public class PopulatorRunner extends BlockPopulator {
    private WorldState state;

    public PopulatorRunner(WorldState state) {
        this.state = state;
    }

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int x = chunk.getX() << 4;
        int z = chunk.getZ() << 4;

        Biome biome = world.getBiome(x, z);
        BiomeGenerator generator = state.getBiomeGenerator().getForBiome(biome);
        ExpansiveTerrainPopulator[] populators = generator.getPopulators();

        for (int i = 0; i < populators.length; i++) {
            populators[i].populate(x, z);
        }
    }

}
