package net.erbros.HoldGuest;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

class PlayerEventListener implements Listener {
    HoldGuest plugin;
    Misc misc;
    
    PlayerEventListener(HoldGuest plugin) {
    	this.plugin = plugin;
    	this.misc = plugin.getMisc();
    }
    
    
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMove (PlayerMoveEvent event) {
	    //plugin.log.info("active " + plugin.active);
	    if(plugin.active == false)
	        return;
	    
	    Player p = event.getPlayer();
	    //plugin.log.info("cacheVar " + plugin.cacheage);
	    //plugin.log.info("playerCacheAge " + PlayerCache.getLocationCacheAge(p));
	    
	    // Let's ask the admin how often he want us to check he moving.
	    if(!(PlayerCache.getLocationCacheAge(p) > plugin.cacheage * 1000))
	        return;

        // If not, do he have permission to stay outside?
	    if(p.hasPermission("holdguest.nohold")) {
	        PlayerCache.setLocation(p, p.getLocation());
	        //plugin.log.info("Allowed to stay outside of box");
	        return;
	    }
	    
	    //plugin.log.info("vector: " + plugin.vector);
	    //plugin.log.info("curVec: " + p.getLocation().toVector());
	    //plugin.log.info("holdradius: " + plugin.holdradius);
	    //plugin.log.info("world: " + plugin.world.toString());
	    //plugin.log.info("vec: " + plugin.vector.distance(p.getLocation().toVector()));
	    
	    // let's check if the player is inside the field? If he is, no reason to check more.
	    if(plugin.vector.distance(p.getLocation().toVector()) < plugin.holdradius) {
	        if(PlayerCache.getLocationCacheAge(p) > plugin.cacheage * 1000) {
                PlayerCache.setLocation(p, p.getLocation());
                //plugin.log.info("Player was inside box");
	        }
	        return;
	    }
	    
	    // Not working right now.
	    // p.sendMessage( misc.customMessage("keepinside"));
	    p.sendMessage(plugin.keepinside);
	    
	    // Okay, the player is outside the field and have no permission? We should help him inside. 
	    //Let's first check if he recently went outside and we have the old location in cache.
	    if(PlayerCache.getLocation(p).toVector().distance(plugin.vector) < plugin.holdradius - 0.1) {
	        // We should check if the block he will be standing on is solid.
	        if( p.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid() || p.getLocation().getBlock().getRelative(BlockFace.DOWN).isEmpty()) {
	            p.setVelocity( new Vector (0,0,0));
	            p.teleport( plugin.vector.toLocation( plugin.world ) );
	            PlayerCache.setLocationCacheAge(p, System.currentTimeMillis());
	            //plugin.log.info("The cached location wasn't solid.");
	            return;
	        }
            p.setVelocity( new Vector (0,0,0));
	        p.teleport( PlayerCache.getLocation(p));
	        //plugin.log.info("The cached location was inside the box.");
            PlayerCache.setLocationCacheAge(p, System.currentTimeMillis());
            
	        return;
	    }
	    
	    // We will just have to send him to the middle of the spawn
        p.setVelocity( new Vector (0,0,0));
	    p.teleport( plugin.vector.toLocation(plugin.world) );
	    //plugin.log.info("Moved player to middle of zone");
	    
	    return;
	}
}