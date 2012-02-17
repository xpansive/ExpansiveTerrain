package com.xpansive.bukkit.expansiveterrain.biome;

import java.util.EnumSet;
import java.util.HashMap;

import org.bukkit.block.Biome;

public enum ExpansiveTerrainBiome {
    RAINFOREST(EnumSet.of(Biome.FOREST)),
    DESERT(EnumSet.of(Biome.DESERT));
    
    private final EnumSet<Biome> mcBiomes;
    private static final HashMap<Biome, ExpansiveTerrainBiome> convMap;
    
    private ExpansiveTerrainBiome (EnumSet<Biome> mcBiomes){
        this.mcBiomes = mcBiomes;
    }
    
    public EnumSet<Biome> getMCBiomes() {
        return mcBiomes;
    }
    
    public boolean isForMCBiome(Biome biome) {
        return mcBiomes.contains(biome);
    }
     
    static {
        convMap = new HashMap<Biome, ExpansiveTerrainBiome>();
        for (ExpansiveTerrainBiome val : values()) {
            for (Biome biome : val.mcBiomes) {
                convMap.put(biome, val);
            }
        }
    }
    
    public static ExpansiveTerrainBiome getForMCBiome(Biome biome) {
        return convMap.get(biome);
    }
}
