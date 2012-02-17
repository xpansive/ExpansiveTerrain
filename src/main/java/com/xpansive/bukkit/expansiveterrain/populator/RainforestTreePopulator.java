package com.xpansive.bukkit.expansiveterrain.populator;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.structure.tree.Tree;
import com.xpansive.bukkit.expansiveterrain.util.RandomExt;

// A "brute force" tree generator that keeps trying to plant trees until there's no room left
public class RainforestTreePopulator extends ExpansiveTerrainPopulator {
    // The max number of failed attempts to plant trees
    private static final int MAX_FAILS = 3;

    private final Tree[] trees;

    public RainforestTreePopulator(WorldState state, Tree[] trees) {
        super(state);
        this.trees = trees.clone();
    }

    @Override
    public void populate(int chunkX, int chunkZ) {
        RandomExt rand = state.getRandomExt();
        for (int fails = 0; fails < MAX_FAILS;) {
            Tree tree = trees[rand.randInt(trees.length)];
            if (rand.percentChance(tree.getTreeChance())) {
                int x = chunkX + rand.randInt(16);
                int z = chunkZ + rand.randInt(16);
                int y = state.getDirectWorld().getHighestBlockY(x, z);
                if (!tree.getGenerator().generate(x, y, z)) {
                    //fails++;
                }
                
            }
            return;
        }
    }
}
