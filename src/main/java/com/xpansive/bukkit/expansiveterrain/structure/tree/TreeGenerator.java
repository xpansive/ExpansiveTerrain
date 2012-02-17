package com.xpansive.bukkit.expansiveterrain.structure.tree;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.structure.StructureGenerator;

// Just here to distinguish between StructureGenerator for now
public abstract class TreeGenerator extends StructureGenerator {

    public TreeGenerator(WorldState state) {
        super(state);
    }
}
