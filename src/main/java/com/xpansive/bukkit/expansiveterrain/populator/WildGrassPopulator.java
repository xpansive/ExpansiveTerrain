package com.xpansive.bukkit.expansiveterrain.populator;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.util.DirectWorld;
import com.xpansive.bukkit.expansiveterrain.util.RandomExt;

public class WildGrassPopulator extends ExpansiveTerrainPopulator {

    private int minSteps, maxSteps;
    private double chance;

    public WildGrassPopulator(WorldState state, String path) {
        super(state);
        FileConfiguration config = state.getConfig();
        minSteps = config.getInt(path + "minsteps");
        maxSteps = config.getInt(path + "maxsteps");
        chance = config.getDouble(path + "chance");
    }

    @Override
    public void populate(int chunkX, int chunkZ) {
        RandomExt rand = state.getRandomExt();
        DirectWorld world = state.getDirectWorld();
        
        if (!rand.percentChance(chance)) {
            return;
        }
        
        int x = chunkX + rand.randInt(16);
        int z = chunkZ + rand.randInt(16);

        // Determine the size/steps
        int numSteps = rand.randInt(minSteps, maxSteps);

        // Random walking
        for (int i = 0; i < numSteps; i++) {
            x += rand.randInt(-1, 1);
            z += rand.randInt(-1, 1);
            int y = world.getHighestBlockY(x, z);

            if (world.getMaterial(x, y - 1, z) == Material.GRASS && world.getTypeId(x, y, z) == 0) {
                world.setRawTypeIdAndData(x, y, z, Material.LONG_GRASS.getId(), 1);
            }
        }
    }

}
