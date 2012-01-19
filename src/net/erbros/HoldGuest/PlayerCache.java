package net.erbros.HoldGuest;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;

class PlayerCache {
    static HashMap<String, Location> playerLocations = new HashMap<String, Location>();
    static HashMap<String, Long> playerLocationCacheAge = new HashMap<String, Long>();
    
    
    protected static void setLocation (Player p, Location l) {
        playerLocations.put(p.getName(), l);
        setLocationCacheAge(p, System.currentTimeMillis());
    }
    
    protected static Location getLocation(Player p) {
        Location l;
        if(!playerLocations.containsKey(p.getName())) {
            l = p.getLocation();
        } else {
            l = playerLocations.get(p.getName());
        }
        return l;
    }

    protected static long getLocationCacheAge(Player p) {
        long age = 0;
        
        if(!playerLocationCacheAge.containsKey(p.getName())) {
            playerLocationCacheAge.put(p.getName(), System.currentTimeMillis());
        } else {
            long millis = playerLocationCacheAge.get(p.getName());
            age = System.currentTimeMillis() - millis;
        }
        
        return age;
    }
    
    protected static void setLocationCacheAge(Player p, long age) {
        playerLocationCacheAge.put(p.getName(), age);
    }
    
    
    
}
