package com.xpansive.bukkit.expansiveterrain.populator;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.util.DirectWorld;
import com.xpansive.bukkit.expansiveterrain.util.RandomExt;

public class MelonPopulator extends ExpansiveTerrainPopulator {

    private int numMelons, depositRadius;
    private double chance;

    public MelonPopulator(WorldState state, String path) {
        super(state);
        FileConfiguration config = state.getConfig();
        chance = config.getDouble(path + "chance");
        numMelons = config.getInt(path + "maxnum");
        depositRadius = config.getInt(path + "radius");
    }

    @Override
    public void populate(int cx, int cz) {
        RandomExt random = state.getRandomExt();
        DirectWorld world = state.getDirectWorld();
        
        // Check if we should place a melon patch on this chunk
        if (random.percentChance(chance)) {
            int x = cx + random.randInt(16);
            int z = cz + random.randInt(16);
            int num = random.randInt(numMelons);
            
            for (int i = 0; i < num; i++) {
                // Pick a random spot within the radius
                int kx = x + random.randInt(-depositRadius, depositRadius);
                int kz = z + random.randInt(-depositRadius, depositRadius);
                int y = world.getHighestBlockY(kx, kz);

                // If it's grass, place a melon on top
                if (world.getMaterial(kx, y - 1, kz) == Material.GRASS) {
                    world.setRawMaterial(kx, y, kz, Material.MELON_BLOCK);
                }
            }
        }
    }
}
