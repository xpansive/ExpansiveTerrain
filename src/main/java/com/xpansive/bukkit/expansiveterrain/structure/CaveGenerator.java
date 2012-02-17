package com.xpansive.bukkit.expansiveterrain.structure;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.noise.SimplexNoiseGenerator;

import com.xpansive.bukkit.expansiveterrain.WorldState;

public class CaveGenerator extends LargeStructureGenerator {
    private final SimplexNoiseGenerator[] layers;
    private int numLayers, seaLevel;
    private double density, levelScale, widthScale, widthMul, widthAdd, caveScale, densityScale, heightScale, heightMul, heightAdd;

    public CaveGenerator(WorldState state, String path) {
        super(state);
        FileConfiguration config = state.getConfig();
        seaLevel = config.getInt("world.sealevel");
        numLayers = config.getInt(path + "numlayers");
        density = config.getDouble(path + "density");
        levelScale = config.getDouble(path + "levelscale");
        widthScale = config.getDouble(path + "widthscale");
        widthMul = config.getDouble(path + "widthmul");
        widthAdd = config.getDouble(path + "widthadd");
        caveScale = config.getDouble(path + "cavescale");
        densityScale = config.getDouble(path + "densityscale");
        heightScale = config.getDouble(path + "heightscale");
        heightMul = config.getDouble(path + "heightmul");
        heightAdd = config.getDouble(path + "heightadd");

        layers = new SimplexNoiseGenerator[numLayers];
        for (int i = 0; i < numLayers; i++) {
            layers[i] = new SimplexNoiseGenerator(state.getBukkitWorld().getSeed() * i);
        }
    }

    @Override
    public void fillColumn(int worldX, int worldZ, int x, int z, byte[] chunkData) {
        for (int i = 0; i < numLayers; i++) {
            SimplexNoiseGenerator noise = layers[i];
            int height = (int) (noise.noise(worldX / levelScale, worldZ / levelScale) * (seaLevel / 2) + seaLevel / 2); // The level the cave will be placed on
            height += (int) (noise.noise(worldX / 100.0, worldZ / 100.0) * 10); // Just some small variation, maybe make this configurable?
            double heightFade = (seaLevel - height) / (double)(seaLevel);
            
            double threshold = (noise.noise(worldX / widthScale, worldZ / widthScale) * widthMul + widthAdd); // This threshold determines the width of the cave
            double cave = Math.abs(noise.noise(worldX / caveScale, worldZ / caveScale)); // The cave density

            boolean placeCave = cave < threshold; // Whether we should place a cave here
            placeCave &= noise.noise(worldX / densityScale, worldZ / densityScale) < density * heightFade; // Make some areas not have caves

            double caveHeightMul = noise.noise(worldX / heightScale, worldZ / heightScale) * heightMul + heightAdd; // The cave height multiplier
            double caveHeight = (1 - cave) * caveHeightMul; // The cave height

            if (placeCave) {
                // Clear out the cave
                for (int y = 0; y < caveHeight; y++) {
                    if (y / caveHeightMul - cave > threshold) { // Make it more rounded
                        if (getBlock(chunkData, x, height + y, z) != Material.STATIONARY_WATER) {
                            setBlock(chunkData, x, height + y, z, Material.AIR);
                        }
                    }
                }
                // Some fixes for when caves collide with other things
                for (int y = 0; y < caveHeight; y++) {
                    if (getBlock(chunkData, x, height + y - 1, z) == Material.DIRT && getBlock(chunkData, x, height + y, z) == Material.AIR) {
                        setBlock(chunkData, x, height + y - 1, z, Material.GRASS);
                    }
                    if (getBlock(chunkData, x, height + y, z) == Material.STATIONARY_WATER) {
                        setBlock(chunkData, x, height + y, z, Material.WATER);
                    } else if (getBlock(chunkData, x, height + y, z) == Material.STATIONARY_LAVA) {
                        setBlock(chunkData, x, height + y, z, Material.LAVA);
                    }
                }
            }
        }
    }
}
