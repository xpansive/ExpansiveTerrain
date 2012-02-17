package com.xpansive.bukkit.expansiveterrain.structure;

import com.xpansive.bukkit.expansiveterrain.GeneratorBase;
import com.xpansive.bukkit.expansiveterrain.WorldState;

public abstract class StructureGenerator extends GeneratorBase {
    
    public StructureGenerator(WorldState state) {
        super(state);
    }

    public abstract boolean generate(int x, int y, int z);
}
