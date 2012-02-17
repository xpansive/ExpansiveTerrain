package com.xpansive.bukkit.expansiveterrain;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import com.xpansive.bukkit.expansiveterrain.biome.BiomeGenerator;
import com.xpansive.bukkit.expansiveterrain.populator.PopulatorRunner;
import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator;
import com.xpansive.bukkit.expansiveterrain.terrain.TerrainGenerator.TerrainState;

public class ExpansiveTerrainChunkGenerator extends ChunkGenerator {
    private WorldState state;
    private ConfigManager configManager;
    private ExpansiveTerrain plugin;

    public ExpansiveTerrainChunkGenerator(ConfigManager configManager, ExpansiveTerrain plugin) {
        this.configManager = configManager;
        this.plugin = plugin;
    }

    @Override
    public byte[] generate(World world, Random r, int cx, int cz) {
        initState(world);
        byte[] result = new byte[world.getMaxHeight() * 16 * 16];
        final int radius = 5;
        TerrainState[][] states = new TerrainState[16 + radius * 2][16 + radius * 2];
        TerrainGenerator[][] gens = new TerrainGenerator[16 + radius * 2][16 + radius * 2];

        // Calculate the heights
        for (int x = -radius; x < 16 + radius; x++) {
            for (int z = -radius; z < 16 + radius; z++) {
                Biome biome = world.getBiome(toWorldCoords(x, cx), toWorldCoords(z, cz));
                BiomeGenerator bg = state.getBiomeGenerator().getForBiome(biome);
                gens[x + radius][z + radius] = bg.getTerrainGenerator();
                states[x + radius][z + radius] = gens[x + radius][z + radius].getHeight(toWorldCoords(x, cx), toWorldCoords(z, cz), x, z);
            }
        }
        
        TerrainGenerator.smoothTerrainStates(world, cx, cz, states, radius);
        
        // Smooth the heights and fill in the world
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                gens[x + radius][z + radius].fillColumn(states[x + radius][z + radius], toWorldCoords(x, cx), toWorldCoords(z, cz), x, z, result);
            }
        }
        return result;
    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        initState(world);
        return Arrays.asList((BlockPopulator) new PopulatorRunner(state));
    }

    // I know I could use canSpawn instead, but this gives me more control.
    @Override
    public Location getFixedSpawnLocation(World world, Random r) {
        initState(world);
        int maxSpawnDist = state.getConfig().getInt("world.maxspawndist");
        int x, y, z, tries = 0;
        int maxTries = 1, spawnDist = 1;
        BiomeGenerator bg;
        do {
            if (tries++ > maxTries) {
                spawnDist *= 2;
                maxTries *= 2;
                tries = 0;
            }

            x = state.getRandomExt().randInt(-spawnDist, spawnDist);
            z = state.getRandomExt().randInt(-spawnDist, spawnDist);
            y = world.getHighestBlockYAt(x, z);
            bg = state.getBiomeGenerator().getForBiome(world.getBiome(x, z));

            if (spawnDist > maxSpawnDist) {
                state.getLogger().warning("ExpansiveTerrain gave up trying to find a suitable spawn location! Have fun wherever you end up!");
                break;
            }
        } while (!bg.canSpawn(x, y - 1, z));
        return new Location(world, x, y, z);
    }

    private int toWorldCoords(int chunk, int world) {
        return world * 16 + chunk;
    }

    private void initState(World world) {
        if (state == null) {
            state = new WorldState(world, configManager, plugin);
        }
    }
}
