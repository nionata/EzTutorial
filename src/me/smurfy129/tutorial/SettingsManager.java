package me.smurfy129.tutorial;

import java.io.File;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettingsManager {
	
	//Creating- instances of SettingsManager, two new yml's
	private static final SettingsManager 
	tutorials = new SettingsManager("tutorials"),
	configuration = new SettingsManager("config");
	
	//Getters and Setters for the config files
	public static SettingsManager getConfig() {
		return configuration;
	}
	
	public static SettingsManager getTutorials() {
		return tutorials;
	}
	
	private File file; 	//Actual file
	private FileConfiguration config; //Configuration instance of the file
	
	
	//Loading- in the files, creating new ones from hard copies if they don't exist
	private SettingsManager(String fileName) {
		file = new File(Main.plugin.getDataFolder(), fileName + ".yml");
		
		if(!file.exists()) {
			try {
				Main.plugin.saveResource(fileName + ".yml", false);
			} catch(Exception e) {
				Main.plugin.myLog.severe("[EzTutorial] " + fileName + ".yml could not be created! Error!");
				e.printStackTrace();
			}
		}
		
		config = YamlConfiguration.loadConfiguration(file);
	}
	
    //Methods- used with the individual Configs
	public void set(String path, Object value) {
		config.set(path, value);
		save();
	}	

	public boolean contains(String path) {
		return config.contains(path);
	}

	public ConfigurationSection createSection(String path) {
		ConfigurationSection section = config.createSection(path);
		save();
		return section;
	}

	@SuppressWarnings("unchecked")
	public <T> T get(String path) {
		return (T) config.get(path);
	}

	public String getString(String path) {
		return config.getString(path);
	}
	
	public int getInt(String path) {
		return config.getInt(path);
	}
	
	public double getDouble(String path) {
		return config.getDouble(path);
	}
	
	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}
	
	public void reload() {
		config = YamlConfiguration.loadConfiguration(file);
		Main.plugin.myLog.info(String.format("[%s] " + file.getName() + " has been reloaded!", Main.plugin.getDescription().getName()));
	}

	public void save() {
		try {
			config.save(file);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}