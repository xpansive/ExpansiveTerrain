package com.xpansive.bukkit.expansiveterrain.terrain;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import com.xpansive.bukkit.expansiveterrain.structure.*;

public class RainforestTerrainGenerator extends TerrainGenerator {

    private SimplexNoiseGenerator noise;
    private boolean initalized = false;
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

        double dirtHeight = noise.noise(worldX / 40.0, worldZ / 40.0) * 4 + 5;

        
        double height = noise.noise(worldX / 200.0, worldZ / 200.0) * 12; // Gentle hills
        height += noise.noise(worldX / 50.0, worldZ / 50.0) * 4; // Small turbulence
        height += Math.pow(1.1125, noise.noise(worldX / 200.0, worldZ / 200.0) * 39); // Exponential mountains
        height += 7;
        
        height *= calculateHeightInfluence(world, worldX, worldZ);
        height += 60;

        height = Math.min(height, world.getMaxHeight() - 1);
        height = Math.floor(height);

        for (int y = 0; y <= height; y++) {
            if (y == height) {
                setBlock(chunkData, x, y, z, Material.GRASS);
            } else if (y > height - dirtHeight) {
                setBlock(chunkData, x, y, z, Material.DIRT);
            } else {
                setBlock(chunkData, x, y, z, Material.STONE);
            }
        }

        // Fill in large structures such as caves
        for (LargeStructureGenerator structure : structures) {
            structure.fillColumn(world, random, worldX, worldZ, x, z, chunkData);
        }
    }

}
