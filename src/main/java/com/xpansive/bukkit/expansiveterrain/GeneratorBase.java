package com.xpansive.bukkit.expansiveterrain;

// All generation related stuff derives from this
public class GeneratorBase {
    protected final WorldState state;
    
    protected GeneratorBase(WorldState state) {
        this.state = state;
    }
}
