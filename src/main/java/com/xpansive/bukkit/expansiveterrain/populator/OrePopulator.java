package com.xpansive.bukkit.expansiveterrain.populator;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.xpansive.bukkit.expansiveterrain.ConfigManager;
import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.util.DirectWorld;
import com.xpansive.bukkit.expansiveterrain.util.RandomExt;

public class OrePopulator extends ExpansiveTerrainPopulator {

    public OrePopulator(WorldState state, String path) {
        super(state);
        FileConfiguration config = state.getConfig();
        minDeposits = config.getInt(path + "mindeposits");
        maxDeposits = config.getInt(path + "maxdeposits");
        changeableBlocks = Arrays.asList(ConfigManager.Util.readMaterialList(config, path + "changeableblocks"));
        ores = ConfigManager.Util.readMaterialList(config, path + "ores");
        maxDepositSize = ConfigManager.Util.readIntList(config, path + "maxdepositsize");
        minDepositSize = ConfigManager.Util.readIntList(config, path + "mindepositsize");
        maxLevels = ConfigManager.Util.readIntList(config, path + "maxlevel");
        chances = ConfigManager.Util.readDoubleList(config, path + "chance");
    }

    private int minDeposits, maxDeposits;
    private Material[] ores;
    private List<Material> changeableBlocks;
    private int[] minDepositSize, maxDepositSize, maxLevels;
    private double[] chances;

    @Override
    public void populate(int chunkX, int chunkZ) {
        RandomExt rand = state.getRandomExt();

        int numDeposits = rand.randInt(minDeposits, maxDeposits);
        for (int i = 0; i < numDeposits; i++) {
            int x = chunkX + rand.randInt(16);
            int z = chunkZ + rand.randInt(16);
            int y = rand.randInt(state.getDirectWorld().getHighestBlockY(x, z));
            int index;

            do {
                index = rand.randInt(ores.length);
            } while (!rand.percentChance(chances[index]));

            if (!placeOre(index, x, y, z)) { // Don't count this if it failed
                i--;
            }
        }
    }

    private boolean placeOre(int index, int x, int y, int z) {
        RandomExt rand = state.getRandomExt();
        DirectWorld world = state.getDirectWorld();

        if (y > maxLevels[index]) {
            return false;
        }
        Material ore = ores[index];
        int length = rand.randInt(minDepositSize[index], maxDepositSize[index]);
        int cx = 0, cz = 0, cy = 0;

        for (int i = 0; i < length; i++) {
            cx += rand.randInt(-1, 1);
            cy += rand.randInt(-1, 1);
            cz += rand.randInt(-1, 1);

            if (changeableBlocks.contains(world.getMaterial(x + cx, y + cy, z + cz))) {
                world.setRawMaterial(x + cx, y + cy, z + cz, ore);
            } else {
                if (i == 0) { // If the first one failed, don't bother trying the rest
                    return false;
                }
            }
        }
        
        return true;
    }
}
