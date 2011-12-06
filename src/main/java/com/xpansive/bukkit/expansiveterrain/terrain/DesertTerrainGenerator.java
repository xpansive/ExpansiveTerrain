package com.xpansive.bukkit.expansiveterrain.terrain;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.*;

import com.xpansive.bukkit.expansiveterrain.structure.BedrockGenerator;
import com.xpansive.bukkit.expansiveterrain.structure.CaveGenerator;
import com.xpansive.bukkit.expansiveterrain.structure.LargeStructureGenerator;

public class DesertTerrainGenerator extends TerrainGenerator {
    private boolean initalized = false;
    private NoiseGenerator noise;
    private LargeStructureGenerator[] structures;

    @Override
    public void fillColumn(World world, Random random, int worldX, int worldZ, int x, int z, byte[] chunkData) {
        if (!initalized) {
            noise = new SimplexNoiseGenerator(random);
            structures = new LargeStructureGenerator[] {
                    new CaveGenerator(random, 0.5, 60, 2),
                    new BedrockGenerator(random, 0, 4)
            };
            initalized = true;
        }
        
        int seaLevel = 40;
        
        double sandHeight = noise.noise(worldX / 40.0, worldZ / 40.0) * 4 + 5;

        double height = (1 - Math.abs(noise.noise(worldX / 125.0, worldZ / 125.0))) * 18; // Ridges/dunes
        height *= Math.min(1, noise.noise(worldX / 250.0, worldZ / 250.0) + 1); // To make the dunes fade in and out
        height += noise.noise(worldX / 400.0, worldZ / 400.0) * 20 + 15; // Gradual slope
        height -= 20;
        
        height *= calculateHeightInfluence(world, worldX, worldZ);
        height += 60;
        
        height = Math.min(height, world.getMaxHeight() - 1);
        height = Math.floor(height);
        
        for (int y = 0; y <= Math.max(height, seaLevel); y++) {
            if (y > height - sandHeight && y <= height) {
                if (y >= seaLevel && y <= seaLevel + 1)
                    setBlock(chunkData, x, y, z, Material.GRASS);
                else if (y < seaLevel)
                    setBlock(chunkData, x, y, z, Material.DIRT);
                else 
                    setBlock(chunkData, x, y, z, Material.SAND);
            } else if (y <= seaLevel && y > height) {
                setBlock(chunkData, x, y, z, Material.STATIONARY_WATER);
            } else if (y < height - sandHeight) {
                if (noise.noise(worldX / 65.0, y / 65.0, worldZ / 65.0) > 0)
                    setBlock(chunkData, x, y, z, Material.SANDSTONE);
                else
                    setBlock(chunkData, x, y, z, Material.STONE);
            }
        }

        // Fill in large structures such as caves
        for (LargeStructureGenerator structure : structures) {
            structure.fillColumn(world, random, worldX, worldZ, x, z, chunkData);
        }
    }
}
