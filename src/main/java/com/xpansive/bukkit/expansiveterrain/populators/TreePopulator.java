package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.Location;

public class TreePopulator extends BlockPopulator {

    private final int SHRUB_NUM = 4;
    private final int SHRUB_RADIUS = 4;

    public void populate(World world, Random random, Chunk source) {
        int x = (source.getX() << 4) + random.nextInt(16);
        int z = (source.getZ() << 4) + random.nextInt(16);
        int y = world.getHighestBlockYAt(x, z);

        int birchChance = 0, treeChance = 0, bigTreeChance = 0, redwoodChance = 0, tallRedwoodChance = 0, treeNum = 0;

        switch (world.getBiome(x, z)) {
        case RAINFOREST:
            birchChance = 20;
            treeChance = 40;
            bigTreeChance = 100;
            treeNum = 4;
            break;
        case FOREST:
        case SEASONAL_FOREST:
            birchChance = 30;
            treeChance = 60;
            bigTreeChance = 100;
            treeNum = 4;
            break;
        case SWAMPLAND:
            birchChance = 10;
            treeChance = 20;
            bigTreeChance = 25;
            treeNum = 1;
            break;
        case SAVANNA:
            birchChance = 5;
            treeChance = 10;
            treeNum = 1;
            break;
        case SHRUBLAND:
            birchChance = 5;
            treeChance = 15;
            treeNum = 1;
            break;
        case TAIGA:
            redwoodChance = 50;
            tallRedwoodChance = 100;
            treeNum = 4;
            break;
        case PLAINS:
            birchChance = 3;
            treeChance = 6;
            treeNum = 1;
            break;
        case TUNDRA:
            redwoodChance = 5;
            tallRedwoodChance = 10;
            treeNum = 1;
            break;
        }
        treeNum = random.nextInt(treeNum + 1) + 1;
        int shrubNum = random.nextInt(SHRUB_NUM + 1);
        for (int j = 0; j < treeNum; j++) {
            Location[] shrubLoc = new Location[shrubNum];
            x = (source.getX() << 4) + random.nextInt(16);
            z = (source.getZ() << 4) + random.nextInt(16);
            y = world.getHighestBlockYAt(x, z);
            for (int i = 0; i < shrubNum; i++) {
                Location l = new Location(world, 0, 0, 0);
                l.setX(x + (-SHRUB_RADIUS + (int) (random.nextDouble() * ((SHRUB_RADIUS - -SHRUB_RADIUS) + 1))));
                l.setZ(z + (-SHRUB_RADIUS + (int) (random.nextDouble() * ((SHRUB_RADIUS - -SHRUB_RADIUS) + 1))));
                l.setY(world.getHighestBlockYAt(l.getBlockX(), l.getBlockZ()));
                shrubLoc[i] = l;
            }
            int treeType = random.nextInt(100);
            boolean treePlanted = false;

            Location loc = new Location(world, x, y, z);
            if (world.getBiome(x, z) == Biome.TAIGA || world.getBiome(x, z) == Biome.TUNDRA) {
                Block block = world.getBlockAt(x, y, z);
                if (block.getType() == Material.SNOW) {
                    block.setType(Material.SPONGE);
                }
            }
            if (treeType < birchChance) {
                treePlanted = world.generateTree(loc, TreeType.BIRCH);
            } else if (treeType < redwoodChance) {
                treePlanted = world.generateTree(loc, TreeType.REDWOOD);
            } else if (treeType < tallRedwoodChance) {
                treePlanted = world.generateTree(loc, TreeType.TALL_REDWOOD);
            } else if (treeType < treeChance) {
                treePlanted = world.generateTree(loc, TreeType.TREE);
            } else if (treeType < bigTreeChance) {
                treePlanted = world.generateTree(loc, TreeType.BIG_TREE);
            }
            if (treePlanted) {
                for (int i = 0; i < shrubNum; i++) {
                    Location l = shrubLoc[i];
                    Block b = world.getBlockAt(l.getBlockX(), world.getHighestBlockYAt(l.getBlockX(), l.getBlockZ()), l.getBlockZ());
                    if (l.getBlockX() != x && l.getBlockZ() != z && b.getRelative(0, -1, 0).getType() == Material.GRASS) {
                        b.setType(Material.LONG_GRASS);

                        b.setData((byte) (random.nextInt(100) < 3 ? 0 : 2));
                    }
                }
            }
        }
    }
}
