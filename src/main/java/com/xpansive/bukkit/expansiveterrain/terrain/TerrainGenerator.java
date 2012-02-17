package com.xpansive.bukkit.expansiveterrain.terrain;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

import com.xpansive.bukkit.expansiveterrain.GeneratorBase;
import com.xpansive.bukkit.expansiveterrain.WorldState;

public abstract class TerrainGenerator extends GeneratorBase {

    public class TerrainState {
        protected int height;

        protected TerrainState(int height) {
            this.height = height;
        }
    }

    public TerrainGenerator(WorldState state) {
        super(state);

        SEA_LEVEL = state.getConfig().getInt("world.sealevel");
    }

    protected final int SEA_LEVEL;

    public abstract void fillColumn(TerrainState state, int worldX, int worldZ, int x, int z, byte[] chunkData);

    public abstract TerrainState getHeight(int worldX, int worldZ, int x, int z);

    protected void setBlock(byte[] data, int x, int y, int z, Material type) {
        data[(x * 16 + z) * state.getBukkitWorld().getMaxHeight() + y] = (byte) type.getId();
    }

    public static void smoothTerrainStates(World world, int chunkX, int chunkZ, TerrainState[][] states, int radius) {
        final int width = 16, height = 16;

        double[][] biomeBlur = boxBlurBiome(world, chunkX << 4, chunkZ << 4, radius);

        if (biomeBlur == null) { // Nothing to be blurred
            return;
        }

        int[][] data = new int[width + radius * 2][height + radius * 2];

        for (int cx = 0; cx < width + radius * 2; cx++) {
            for (int cz = 0; cz < height + radius * 2; cz++) {
                data[cx][cz] = states[cx][cz].height;
            }
        }

        data = boxBlur1dInt(data, radius);
        data = boxBlur1dInt(data, radius);

        for (int cx = radius; cx < width + radius; cx++) {
            for (int cz = radius; cz < height + radius; cz++) {
                states[cx][cz].height = (int) (states[cx][cz].height + Math.min(biomeBlur[cx][cz] * 4, 1) * (data[cx][cz] - states[cx][cz].height));
            }
        }
    }

    private static double[][] boxBlurBiome(World world, int x, int z, int radius) {
        final int width = 16, height = 16;
        double[][] data = new double[width + radius * 2][height + radius * 2];

        boolean anyChange = false;
        for (int cx = -radius; cx < width + radius; cx++) {
            for (int cz = -radius; cz < height + radius; cz++) {
                Biome current = world.getBiome(x + cx, z + cz);
                changecheck: for (int xx = -1; xx < 2; xx++) {
                    for (int zz = -1; zz < 2; zz++) {
                        if (world.getBiome(x + cx + xx, z + cz + zz) != current) {
                            data[cx + radius][cz + radius] = 1.0;
                            anyChange = true;
                            break changecheck;
                        }
                    }
                }
            }
        }

        // Theres no point in calculating this because there is only one biome in this chunk
        if (!anyChange) {
            return null;
        }

        data = boxBlur1dDouble(data, radius);
        data = boxBlur1dDouble(data, radius);

        return data;
    }

    private static int[][] boxBlur1dInt(int[][] input, int radius) {
        final int width = 16;
        final int height = 16;
        int[][] dest = new int[width + radius * 2][height + radius * 2];
        for (int y = -radius; y < height + radius; y++) {
            int total = 0;
            // Process entire window for first pixel
            for (int kx = -radius; kx <= radius; kx++) {
                total += input[kx + radius][y + radius];
            }
            dest[y + radius][0 + radius] = total / (radius * 2 + 1);

            // Subsequent pixels just update window total
            for (int x = 1; x < width; x++) {
                // Subtract pixel leaving window
                total -= input[x - radius - 1 + radius][y + radius];

                // Add pixel entering window
                total += input[x + radius * 2][y + radius];

                dest[y + radius][x + radius] = total / (radius * 2 + 1);
            }
        }
        return dest;
    }

    private static double[][] boxBlur1dDouble(double[][] input, int radius) {
        final int width = 16;
        final int height = 16;
        double[][] dest = new double[width + radius * 2][height + radius * 2];
        for (int y = -radius; y < height + radius; y++) {
            double total = 0;
            // Process entire window for first pixel
            for (int kx = -radius; kx <= radius; kx++) {
                total += input[kx + radius][y + radius];
            }
            dest[y + radius][0 + radius] = total / (double) (radius * 2 + 1);

            // Subsequent pixels just update window total
            for (int x = 1; x < width; x++) {
                // Subtract pixel leaving window
                total -= input[x - radius - 1 + radius][y + radius];

                // Add pixel entering window
                total += input[x + radius * 2][y + radius];

                dest[y + radius][x + radius] = total / (double) (radius * 2 + 1);
            }
        }
        return dest;
    }
}
