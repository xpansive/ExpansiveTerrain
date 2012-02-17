package com.xpansive.bukkit.expansiveterrain.populator;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.xpansive.bukkit.expansiveterrain.ConfigManager;
import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.util.DirectWorld;
import com.xpansive.bukkit.expansiveterrain.util.RandomExt;

public class FlowerPopulator extends ExpansiveTerrainPopulator {

    public FlowerPopulator(WorldState state, String path) {
        super(state);
        FileConfiguration config = state.getConfig();
        patchChance = config.getDouble(path + "patchchance");
        chance = config.getDouble(path + "chance");
        num = config.getInt(path + "num");
        types = ConfigManager.Util.readMaterialList(config, path + "types");
    }

    private int num;
    private double patchChance, chance;
    private Material[] types;
    
    @Override
    public void populate(int cx, int cz) {
        RandomExt random = state.getRandomExt();
        DirectWorld world = state.getDirectWorld();
        
        // Check if we should plant a flower patch here
        if (random.percentChance(patchChance)) {
            int x = cx + random.randInt(16);
            int z = cz + random.randInt(16);

            // Retrieve a random block type from the list of flower types
            // The flowers array can be weighted if the generator wants it
            Material flowerType = types[random.randInt(types.length)];

            for (int i = 0; i < num; i++) {
                // Move both the x and z by a random amount between -1 and 1
                x += random.randInt(-1, 1);
                z += random.randInt(-1, 1);
                int y = world.getHighestBlockY(x, z);

                // Make sure we're planting it on grass
                if (world.getMaterial(x, y - 1, z) == Material.GRASS && random.percentChance(chance)) {
                    world.setRawMaterial(x, y, z, flowerType);
                }
            }
        }
    }
}
