package com.xpansive.bukkit.expansiveterrain.terrain;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.NoiseGenerator;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import com.xpansive.bukkit.expansiveterrain.WorldState;
import com.xpansive.bukkit.expansiveterrain.structure.BedrockGenerator;
import com.xpansive.bukkit.expansiveterrain.structure.CaveGenerator;
import com.xpansive.bukkit.expansiveterrain.structure.LargeStructureGenerator;

public class DesertTerrainGenerator extends TerrainGenerator {
    
    private class DesertTerrainState extends TerrainState {
        private int sandHeight;

        public DesertTerrainState(int height, int sandHeight) {
            super(height);
            this.sandHeight = sandHeight;
        }
    }
    
    public DesertTerrainGenerator(WorldState state) {
        super(state);
        noise = new SimplexNoiseGenerator(state.getBukkitWorld().getSeed());
        structures = new LargeStructureGenerator[] { new CaveGenerator(state, "biome.rainforest.cave"), new BedrockGenerator(state) };
    }

    private NoiseGenerator noise;
    private LargeStructureGenerator[] structures;

    @Override
    public void fillColumn(TerrainState state, int worldX, int worldZ, int x, int z, byte[] chunkData) {
        DesertTerrainState data = (DesertTerrainState) state;
        for (int y = 0; y <= Math.max(data.height, SEA_LEVEL); y++) {
            if (y >= data.height - data.sandHeight && y <= data.height) {
                //if (y >= seaLevel && y <= seaLevel + 1)
                //    setBlock(chunkData, x, y, z, Material.GRASS);
                //else if (y < seaLevel)
                //    setBlock(chunkData, x, y, z, Material.DIRT);
                //else
                    setBlock(chunkData, x, y, z, Material.SAND);
            //} else if (y <= seaLevel && y > data.height) {
            //    setBlock(chunkData, x, y, z, Material.STATIONARY_WATER);
            } else if (y < data.height - data.sandHeight) {
                if (noise.noise(worldX / 65.0, y / 65.0, worldZ / 65.0) > 0)
                    setBlock(chunkData, x, y, z, Material.SANDSTONE);
                else
                    setBlock(chunkData, x, y, z, Material.STONE);
            }
        }

        // Fill in large structures such as caves
        for (LargeStructureGenerator structure : structures) {
            structure.fillColumn(worldX, worldZ, x, z, chunkData);
        }
    }

    @Override
    public TerrainState getHeight(int worldX, int worldZ, int x, int z) {
        World world = state.getBukkitWorld();

        double sandHeight = noise.noise(worldX / 40.0, worldZ / 40.0) * 4 + 5;

        double height = (1 - Math.abs(noise.noise(worldX / 125.0, worldZ / 125.0))) * 18; // Ridges/dunes
        height *= Math.min(1, noise.noise(worldX / 250.0, worldZ / 250.0) + 1); // To make the dunes fade in and out
        height += noise.noise(worldX / 400.0, worldZ / 400.0) * 20 + 15; // Gradual slope
        height += SEA_LEVEL;

        height = Math.min(height, world.getMaxHeight() - 1);
        height = Math.floor(height);

        return new DesertTerrainState((int) height, (int) sandHeight);
    }
}
