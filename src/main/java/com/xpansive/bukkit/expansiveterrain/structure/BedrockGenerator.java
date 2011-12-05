package com.xpansive.bukkit.expansiveterrain.structure;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.SimplexNoiseGenerator;

public class BedrockGenerator extends LargeStructureGenerator {
    private int minHeight, maxHeight;
    private SimplexNoiseGenerator noise;

    public BedrockGenerator(Random random, int minHeight, int maxHeight) {
        noise = new SimplexNoiseGenerator(random);
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    @Override
    public void fillColumn(World world, Random rand, int worldX, int worldZ, int x, int z, byte[] chunkData) {
        double bedrock = noise.noise(worldX / 60.0, worldZ / 60.0);
        bedrock = ( (bedrock + 1) / 2 ) * (maxHeight - minHeight) + minHeight;
        for (int y = 0; y < bedrock; y++) {
            setBlock(chunkData, x, y, z, Material.BEDROCK);
        }
    }

}
