package com.xpansive.bukkit.expansiveterrain.structure.tree;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.structure.tree.TreeGenerator;

public class Tree {
    
    private TreeGenerator generator;
    private double treeChance;
    
    public Tree(WorldState state, TreeGenerator generator, String path) {
        this.generator = generator;
        treeChance = state.getConfig().getDouble(path + "chance");
    }
    
    public TreeGenerator getGenerator() {
        return generator;
    }
    
    public double getTreeChance() {
        return treeChance;
    }
}
