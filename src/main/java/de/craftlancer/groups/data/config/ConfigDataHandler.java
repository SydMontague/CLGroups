package de.craftlancer.groups.data.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import de.craftlancer.groups.CLGroups;

public class ConfigDataHandler
{
    private CLGroups plugin;
    private FileConfiguration config;
    private File file;
    
    public ConfigDataHandler(CLGroups plugin, String filename)
    {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), filename);
        this.config = YamlConfiguration.loadConfiguration(file);
    }
    
    public FileConfiguration getConfig()
    {
        return config;
    }
    
    public File getFile()
    {
        return file;
    }
    
    public CLGroups getPlugin()
    {
        return plugin;
    }
    
    public void saveConfig()
    {
        try
        {
            config.save(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
