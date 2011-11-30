package com.xpansive.bukkit.expansiveterrain.populator;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

import com.xpansive.bukkit.expansiveterrain.structure.tree.*;

public class TreePopulator extends BlockPopulator {

    private Tree[] trees;
    private int numTreeTypes;

    public TreePopulator(Tree[] trees, int numTreeTypes) {
        this.trees = trees;
        this.numTreeTypes = numTreeTypes;
    }

    public void populate(World world, Random random, Chunk source) {
        int x = (source.getX() << 4);
        int z = (source.getZ() << 4);
        for (int i = 0; i < numTreeTypes; i++) {
            int index = random.nextInt(trees.length);
            Tree tree = trees[index];
            TreeGenerator generator = tree.getGenerator();
            int numTrees = random.nextInt(tree.getMaxPerChunk() - tree.getMinPerChunk() + 1) + tree.getMinPerChunk();

            for (int j = 0; j < numTrees; j++) {
                if (random.nextInt(100) < tree.getTreeChance()) {
                    int treeX = x + random.nextInt(16);
                    int treeZ = z + random.nextInt(16);
                    generator.generate(world, random, treeX, world.getHighestBlockYAt(treeX, treeZ), treeZ);
                }
            }
        }
    }
}
