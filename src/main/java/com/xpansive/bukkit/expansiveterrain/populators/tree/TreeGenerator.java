package com.xpansive.bukkit.expansiveterrain.populators.tree;

import java.util.Random;

import org.bukkit.World;

public interface TreeGenerator {

    public boolean generate(World world, Random rand, int x, int y, int z);

}
