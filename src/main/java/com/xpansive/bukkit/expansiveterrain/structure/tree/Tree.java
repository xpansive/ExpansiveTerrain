package com.xpansive.bukkit.expansiveterrain.structure.tree;

import com.xpansive.bukkit.expansiveterrain.structure.tree.TreeGenerator;

public class Tree {
    
    private TreeGenerator generator;
    private int treeChance, minPerChunk, maxPerChunk;
    
    public Tree(TreeGenerator generator, int treeChance, int minPerChunk, int maxPerChunk) {
        this.generator = generator;
        this.treeChance = treeChance;
        this.minPerChunk = minPerChunk;
        this.maxPerChunk = maxPerChunk;
    }
    
    public TreeGenerator getGenerator() {
        return generator;
    }
    
    public int getTreeChance() {
        return treeChance;
    }
    
    public int getMinPerChunk() {
        return minPerChunk;
    }
    
    public int getMaxPerChunk() {
        return maxPerChunk;
    }
}
