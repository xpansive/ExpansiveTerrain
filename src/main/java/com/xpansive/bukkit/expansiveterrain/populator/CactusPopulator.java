package com.xpansive.bukkit.expansiveterrain.populator;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.util.DirectWorld;
import com.xpansive.bukkit.expansiveterrain.util.RandomExt;

public class CactusPopulator extends ExpansiveTerrainPopulator {

    private int minHeight, maxHeight, radius;
    private double chance, newChance;

    public CactusPopulator(WorldState state, String path) {
        super(state);
        FileConfiguration config = state.getConfig();
        minHeight = config.getInt(path + "minheight");
        maxHeight = config.getInt(path + "maxheight");
        radius = config.getInt(path + "radius");
        chance = config.getDouble(path + "chance");
        newChance = config.getDouble(path + "newchance");
    }

    @Override
    public void populate(int cx, int cz) {
        RandomExt random = state.getRandomExt();
        DirectWorld world = state.getDirectWorld();

        // Determine if we should plant anything at all
        boolean plantNewCactus = random.percentChance(chance);
        int x = cx + random.randInt(16);
        int z = cz + random.randInt(16);

        while (plantNewCactus) {
            // Pick some random coordinates
            x += random.randInt(-radius, radius);
            z += random.randInt(-radius, radius);

            int y = world.getHighestBlockY(x, z);

            // Check if we're planting it on sand
            if (world.getMaterial(x, y - 1, z) == Material.SAND) {
                // Determine the height of the cactus
                int height = random.randInt(minHeight, maxHeight);

                for (int i = 0; i < height; i++) {
                    // Cacti can only be planted if there are no blocks adjacent to them
                    if ((world.getTypeId(x + 1, y, z) | world.getTypeId(x - 1, y, z) | world.getTypeId(x, y, z + 1) | world.getTypeId(x, y, z - 1)) == 0) {
                        world.setRawMaterial(x, y + i, z, Material.CACTUS);
                    }
                }
            }
            // Determine if we should continue, planting more cacti in this patch
            plantNewCactus = random.percentChance(newChance);
        }
    }

}
