package com.xpansive.bukkit.expansiveterrain.structure.tree;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.util.DirectWorld;
import com.xpansive.bukkit.expansiveterrain.util.RandomExt;

public class BushGenerator extends TreeGenerator {

    private int minRadius, maxRadius;
    private double logChance;

    public BushGenerator(WorldState state, String path) {
        super(state);
        FileConfiguration config = state.getConfig();
        minRadius = config.getInt(path + "minradius");
        maxRadius = config.getInt(path + "maxradius");
        logChance = config.getDouble(path + "logchance");
    }

    public boolean generate(int x, int y, int z) {
        DirectWorld world = state.getDirectWorld();
        RandomExt rand = state.getRandomExt();
        double radius = rand.randDouble(minRadius, maxRadius);
        if (canGrow(x, z, (int) radius)) {
            for (int cx = (int) -radius; cx < radius; cx++) {
                for (int cz = (int) -radius; cz < radius; cz++) {
                    for (int cy = 0; cy < radius; cy++) {
                        double dist = Math.sqrt(cx * cx + cz * cz + cy * cy);
                        int highY = world.getHighestBlockY(x + cx, z + cz);
                        if (dist < radius - 2 && rand.percentChance(logChance)) {
                            world.setRawMaterial(x + cx, highY + cy, z + cz, Material.LOG);
                        } else if (dist < radius) {
                            world.setRawMaterial(x + cx, highY + cy, z + cz, Material.LEAVES);
                        }
                    }
                }
            }
        }
        return true;
    }

    private boolean canGrow(int x, int z, double radius) {
        DirectWorld world = state.getDirectWorld();
        int lastY = world.getHighestBlockY(x, z);
        for (int cx = (int) -radius; cx < radius; cx++) {
            for (int cz = (int) -radius; cz < radius; cz++) {
                int cy = world.getHighestBlockY(x + cx, z + cz);
                if (Math.max(cy, lastY) - Math.min(cy, lastY) > 2 || world.getMaterial(x + cx, cy - 1, z + cz) != Material.GRASS) {
                    return false;
                }
                lastY = cy;
            }
        }
        return true;
    }
}
