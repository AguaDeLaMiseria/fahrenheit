package com.aguadelamiseria.fahrenheit.files;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ConfigurationFile {

    private final JavaPlugin plugin;
    private final String name;
    private FileConfiguration fileConfiguration;

    public ConfigurationFile(JavaPlugin plugin, String name){
        this.plugin = plugin;
        this.name = name;
        load();
    }

    public void load(){
        File file = new File(plugin.getDataFolder(), name);

        if (!file.exists()){
            file.getParentFile().mkdirs();
            plugin.saveResource(name,false);
        }

        this.fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration get(){
        return fileConfiguration;
    }
}
