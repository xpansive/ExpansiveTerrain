package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;

public class PumpkinPopulator extends RandomDistributionPopulator {
    public PumpkinPopulator() {
        NUM_BLOCKS = 20;
        SIZE = 16;
        CHANCE_PER_1000 = 5;
        BLOCK_TYPE = Material.PUMPKIN;
        BLOCK_DATA = 5;
        UNDER_BLOCK_TYPE = Material.GRASS;
    }

    @Override
    public void populate(World world, Random random, Chunk source) {
        randomDistribution(world, random, source);
    }
}
