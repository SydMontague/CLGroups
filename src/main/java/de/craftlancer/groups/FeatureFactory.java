package de.craftlancer.groups;

import org.bukkit.configuration.file.FileConfiguration;

public class FeatureFactory
{
    
    public static TownFeature getFeature(CLGroups plugin, String key, FileConfiguration config)
    {
        FeatureType type = FeatureType.getFeatureType(config.getString(key + ".type"));
        
        switch (type)
        {
            case ENCHANT_TO_XP:
                // return new EnchantToExpFeature(plugin, key, config);
            case XP_TO_BOTTLE:
                return new ExpToBottleFeature(plugin, key, config);
            default:
                return null;
        }
    }
    
}
