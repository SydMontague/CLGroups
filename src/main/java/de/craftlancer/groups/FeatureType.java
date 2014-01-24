package de.craftlancer.groups;

public enum FeatureType
{
    XP_TO_BOTTLE,
    ENCHANT_TO_XP;
    
    public static FeatureType getFeatureType(String s)
    {
        for (FeatureType type : values())
            if (type.name().equalsIgnoreCase(s))
                return type;
        
        return null;
    }
}
