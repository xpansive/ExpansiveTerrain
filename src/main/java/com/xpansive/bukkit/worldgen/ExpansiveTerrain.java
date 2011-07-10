package com.xpansive.bukkit.worldgen;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;
import com.lennardf1989.bukkitex.MyDatabase;
import com.avaje.ebean.EbeanServer;

public class ExpansiveTerrain extends JavaPlugin {
	public final static String WORLD_NAME = "ExpansiveTerrain";
	private static World genWorld = null;
	ExpansiveTerrainEventListener listener = new ExpansiveTerrainEventListener(this);
	public MyDatabase database;
	Configuration config;

	public void onDisable() {
	}

	public void onEnable() {
		config = new Configuration(new File(WORLD_NAME, "config.cfg"));
        config.load();
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.WORLD_LOAD, listener, Priority.Normal,
				this);
		pm.registerEvent(Event.Type.WORLD_SAVE, listener, Priority.Normal,
				this);
		
		loadConfiguration();
		initializeDatabase();
		
		PluginDescriptionFile desc = this.getDescription();

		System.out.println(desc.getName() + " version " + desc.getVersion()
				+ " is enabled!");

		getCommand("expansive").setExecutor(new ExpansiveTerrainCommandExec());
	}
	
    private void loadConfiguration() {     
        config.setProperty("database.driver", config.getString("database.driver", "org.sqlite.JDBC"));
        config.setProperty("database.url", config.getString("database.url", "jdbc:sqlite:{NAME}/{NAME}.db"));
        config.setProperty("database.username", config.getString("database.username", "root"));
        config.setProperty("database.password", config.getString("database.password", ""));
        config.setProperty("database.isolation", config.getString("database.isolation", "SERIALIZABLE"));
        config.setProperty("database.logging", config.getBoolean("database.logging", false));
        config.setProperty("database.rebuild", config.getBoolean("database.rebuild", true));
        
        config.save();
    }
    
    private void initializeDatabase() {
        database = new MyDatabase(this) {
            protected java.util.List<Class<?>> getDatabaseClasses() {
                List<Class<?>> list = new ArrayList<Class<?>>();
                list.add(Data.class);
        
                return list;
            };
        };
        
        database.initializeDatabase(
                config.getString("database.driver"),
                config.getString("database.url"),
                config.getString("database.username"),
                config.getString("database.password"),
                config.getString("database.isolation"),
                config.getBoolean("database.logging", false),
                config.getBoolean("database.rebuild", true)
            );
        
        config.setProperty("database.rebuild", false);
        config.save();
        
        VoronoiNoise.database = database;
    }
    
    @Override
    public EbeanServer getDatabase() {
        return database.getDatabase();
    }

	public boolean anonymousCheck(CommandSender sender) {
		return sender instanceof Player;
	}

	public static World getExpansiveTerrainWorld() {
		if (genWorld == null) {
			genWorld = Bukkit.getServer().createWorld(WORLD_NAME,
					World.Environment.NORMAL,
					new ExpansiveTerrainChunkGenerator());
		}

		return genWorld;
	}

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
		return new ExpansiveTerrainChunkGenerator();
	}
}