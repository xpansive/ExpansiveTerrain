package com.xpansive.bukkit.expansiveterrain.terrain;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.structure.*;

public class RainforestTerrainGenerator extends TerrainGenerator {

    private class RainforestTerrainState extends TerrainState {
        private int dirtHeight;

        public RainforestTerrainState(int height, int dirtHeight) {
            super(height);
            this.dirtHeight = dirtHeight;
        }
    }

    public RainforestTerrainGenerator(WorldState state) {
        super(state);
        noise = new SimplexNoiseGenerator(state.getBukkitWorld().getSeed()); // We need to make sure this is always the same for the same world
        structures = new LargeStructureGenerator[] {
                //new LakeGenerator(state),
                new CaveGenerator(state, "biome.rainforest.cave."),
                new BedrockGenerator(state)
        };
    }

    private SimplexNoiseGenerator noise;
    private LargeStructureGenerator[] structures;

    @Override
    public TerrainState getHeight(int worldX, int worldZ, int x, int z) {
        World world = state.getBukkitWorld();

        double dirtHeight = noise.noise(worldX / 40.0, worldZ / 40.0) * 4 + 5;

        double height = noise.noise(worldX / 200.0, worldZ / 200.0) * 12 + 6; // Gentle hills
        height += noise.noise(worldX / 50.0, worldZ / 50.0) * 4; // Small turbulence
        height += Math.pow(1.1125, noise.noise(worldX / 200.0, worldZ / 200.0) * 39); // Exponential mountains
        height += SEA_LEVEL;

        height = Math.min(height, world.getMaxHeight() - 1);

        return new RainforestTerrainState((int) height, (int) dirtHeight);
    }

    @Override
    public void fillColumn(TerrainState state, int worldX, int worldZ, int x, int z, byte[] chunkData) {
        RainforestTerrainState data = (RainforestTerrainState) state;
        for (int y = 0; y <= data.height; y++) {
            if (y == data.height) {
                setBlock(chunkData, x, y, z, Material.GRASS);
            } else if (y > data.height - data.dirtHeight) {
                setBlock(chunkData, x, y, z, Material.DIRT);
            } else {
                setBlock(chunkData, x, y, z, Material.STONE);
            }
        }

        // Fill in large structures such as caves
        for (LargeStructureGenerator structure : structures) {
            structure.fillColumn(worldX, worldZ, x, z, chunkData);
        }
    }
}
