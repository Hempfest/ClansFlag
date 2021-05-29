package com.youtube.hempfest.clansflag;

import com.github.sanctum.clans.util.events.clans.LandPreClaimEvent;
import com.github.sanctum.clans.util.events.clans.LandUnClaimEvent;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClansFlag extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			WorldGuard worldGuard = WorldGuard.getInstance();
			if (!worldGuard.getPlatform().getSessionManager().registerHandler(IWGClaimCheck.factory, null)) {
				getLogger().severe("- Could not register the WG handler !");
				getServer().getPluginManager().disablePlugin(this);
			}
		}
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onLoad() {
			WorldGuard worldGuard = WorldGuard.getInstance();
			FlagRegistry flagRegistry = worldGuard.getFlagRegistry();
			flagRegistry.register(IWGClaimCheck.CAN_CLAIM = new com.sk89q.worldguard.protection.flags.StateFlag("claim-land", false));
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	@EventHandler
	public void onLandPreClaim(LandPreClaimEvent e) {
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			if (IWGClaimCheck.flagged(e.getClaimer())) {
				e.getUtil().sendMessage(e.getClaimer(), e.getUtil().notClaimOwner("&6&oWorldGuard"));
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onLandUnClaim(LandUnClaimEvent e) {
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			if (IWGClaimCheck.flagged(e.getRemover())) {
				e.getUtil().sendMessage(e.getRemover(), e.getUtil().notClaimOwner("&6&oWorldGuard"));
				e.setCancelled(true);
			}
		}
	}

}
