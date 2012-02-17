package com.xpansive.bukkit.expansiveterrain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

    private static final String CONFIG_NAME = "expansiveconfig.yml";
    private Map<String, FileConfiguration> configs = new HashMap<String, FileConfiguration>();

    public void loadConfig(ExpansiveTerrain plugin, String worldName) {
        Logger logger = plugin.getServer().getLogger();
        File configFile = new File(worldName, CONFIG_NAME);
        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        InputStream defConfigStream = plugin.getResource("config.yml");

        if (configFile.isDirectory()) {
            logger.severe("ExpansiveTerrain: The location where the config file should be (" + worldName + File.separator + CONFIG_NAME + ") is a directory!");
            return;
        }

        if (!configFile.exists()) {
            try {
                config.load(defConfigStream);
                config.save(configFile);
            } catch (IOException e) {
                logger.severe("ExpansiveTerrain: Unable to create config file for world " + worldName);
                logger.severe("This is bad! Tell someone about it!");
                return;
            } catch (InvalidConfigurationException e) {
                logger.severe("ExpansiveTerrain: Default config is corrupted!");
                logger.severe("This is bad! Tell someone about it!");
                return;
            }
        } else {
            FileConfiguration defaults = YamlConfiguration.loadConfiguration(defConfigStream);
            config.setDefaults(defaults);
            try {
                config.load(configFile);
            } catch (FileNotFoundException e) {
                // Should never happen, we check if it exists, so it has to
                logger.severe(e.toString());
                return;
            } catch (IOException e) {
                logger.severe("ExpansiveTerrain: Unable to load config file for world " + worldName);
                logger.severe(e.getMessage());
                return;
            } catch (InvalidConfigurationException e) {
                logger.severe("ExpansiveTerrain: Unable to load config file for world " + worldName);
                logger.severe("Make sure you have the syntax right!");
                return;
            }
        }

        configs.put(worldName, config);
    }

    public FileConfiguration getConfig(String worldName) {
        return configs.get(worldName);
    }

    public static final class Util {
        private Util() {
        }
        
        public static Material[] readMaterialList(FileConfiguration config, String path) {
            List<?> materialList = config.getStringList(path);
            Material[] materials = new Material[materialList.size()];
            for (int i = 0; i < materialList.size(); i++) {
                materials[i] = Material.valueOf((String) materialList.get(i));
            }
            return materials;
        }

        public static int[] readIntList(FileConfiguration config, String path) {
            List<?> list = config.getIntegerList(path);
            Integer[] integers = list.toArray(new Integer[list.size()]);
            int[] ints = new int[list.size()];
            int i = 0;
            for (Integer integer : integers) {
                ints[i++] = integer;
            }
            return ints;
        }
        
        public static double[] readDoubleList(FileConfiguration config, String path) {
            List<?> list = config.getDoubleList(path);
            Double[] boxedDoubles = list.toArray(new Double[list.size()]);
            double[] doubles = new double[list.size()];
            int i = 0;
            for (Double boxedDouble : boxedDoubles) {
                doubles[i++] = boxedDouble;
            }
            return doubles;
        }
    }
}
