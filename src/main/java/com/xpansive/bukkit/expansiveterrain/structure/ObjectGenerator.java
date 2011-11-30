package com.xpansive.bukkit.expansiveterrain.structure;

import java.util.Random;

import org.bukkit.World;

public interface ObjectGenerator {

    public boolean generate(World world, Random rand, int x, int y, int z);

}
