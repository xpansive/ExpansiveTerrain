package com.xpansive.bukkit.expansiveterrain.populators;

import java.util.Random;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;

public class MushroomPopulator extends BlockPopulator {

    private final int NUM_STEPS = 10;

    @Override
    public void populate(World world, Random random, Chunk source) {
        if (random.nextInt(100) < 20) {
            int chunkx = random.nextInt(16);
            int chunkz = random.nextInt(16);
            int x = (source.getX() << 4) + chunkx;
            int z = (source.getZ() << 4) + chunkz;
            int start = random.nextInt(127);
            for (int y = start - 1; y != start; y %= 127, y = y < 0 ? 127 : y, y--) {
                Block b = world.getBlockAt(x, y, z);
                if (b.getTypeId() == 0 && world.getBlockTypeIdAt(x, y - 1, z) != 0 && getLightLevel(world, x, y, z) < 12) {

                    int cx = x, cz = z;

                    Material mushroomType = random.nextBoolean() ? Material.BROWN_MUSHROOM : Material.RED_MUSHROOM;
                    for (int i = 0; i < NUM_STEPS; i++) {

                        cx += random.nextInt(3) - 1;
                        cz += random.nextInt(3) - 1;
                        b = world.getBlockAt(cx, y, cz);
                        if (b.getTypeId() == 0 && world.getBlockTypeIdAt(cx, y - 1, cz) != 0 && getLightLevel(world, cx, y, cz) < 12) {
                            b.setType(mushroomType);
                        }
                    }
                    return;
                }
            }
        }
    }

    private static int getLightLevel(World w, int x, int y, int z) {
        return w.getBlockAt(x, y, z).getLightLevel();
    }
}
