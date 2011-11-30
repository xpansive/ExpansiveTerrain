package com.xpansive.bukkit.expansiveterrain.populator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;

import com.xpansive.bukkit.expansiveterrain.biome.BiomeGenerator;

//This class will call all of the other populators
//based on biome because Bukkit doesn't currently
//allow that
public class TerrainPopulator extends BlockPopulator {

    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int x = (chunk.getX() << 4) + 8;
        int z = (chunk.getZ() << 4) + 8;

        Biome biome = world.getBiome(x, z); //The biome in the middle of the chunk
        BiomeGenerator generator = BiomeGenerator.getForBiome(biome);
        BlockPopulator[] populators = generator.getPopulators();
        
        for (int i = 0; i < populators.length; i++) {
            populators[i].populate(world, random, chunk);
        }
    }

}
