package net.erbros.HoldGuest;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.World;



public class HoldGuest extends JavaPlugin {
    protected double holdradius;
    protected double cacheage;
    protected Vector vector;
    protected double x;
    protected double y;
    protected double z;
    protected World world;
    protected boolean active = true;
    String keepinside = "";

    protected Misc misc = null;
    protected PlayerCache cache = null;
	protected PlayerEventListener pEL = new PlayerEventListener(this);
	protected HoldGuestCommandExecutor myExecutor;
	
	// Getting some logging done.
	protected final Logger log = Logger.getLogger("Minecraft");
	
	
	@Override
    public void onDisable() {
	}

	@Override
    public void onEnable() {
        getDataFolder().mkdirs();
        // Lets get the misc class.
        misc = new Misc(this);
        // Get ready for the commands
        myExecutor = new HoldGuestCommandExecutor(this);
        getCommand("holdguest").setExecutor(myExecutor);

        PluginManager pm = this.getServer().getPluginManager();
        // Register player move events.
		pm.registerEvents(pEL, this);
		
		
	}
	
	public Misc getMisc() {
	    return misc;
	}
	
	
	
}
