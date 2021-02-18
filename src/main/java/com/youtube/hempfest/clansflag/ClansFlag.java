package com.youtube.hempfest.clansflag;

import com.youtube.hempfest.clans.util.events.clans.LandPreClaimEvent;
import com.youtube.hempfest.clans.util.events.clans.LandUnClaimEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClansFlag extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		// Plugin startup logic
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			com.sk89q.worldguard.WorldGuard worldGuard = com.sk89q.worldguard.WorldGuard.getInstance();
			if (!worldGuard.getPlatform().getSessionManager().registerHandler(ClanHandler.factory, null)) {
				getLogger().severe("- Could not register the WG handler !");
				getServer().getPluginManager().disablePlugin(this);
			}
		}
	}

	@Override
	public void onLoad() {
			com.sk89q.worldguard.WorldGuard worldGuard = com.sk89q.worldguard.WorldGuard.getInstance();
			com.sk89q.worldguard.protection.flags.registry.FlagRegistry flagRegistry = worldGuard.getFlagRegistry();
			flagRegistry.register(ClanHandler.CAN_CLAIM = new com.sk89q.worldguard.protection.flags.StateFlag("claim-land", false));
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	@EventHandler
	public void onLandPreClaim(LandPreClaimEvent e) {
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			if (!ClanHandler.canClaim(e.getClaimer())) {
				e.getUtil().sendMessage(e.getClaimer(), e.getUtil().notClaimOwner("&6&oWorldGuard"));
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onLandUnClaim(LandUnClaimEvent e) {
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			if (!ClanHandler.canClaim(e.getRemover())) {
				e.getUtil().sendMessage(e.getRemover(), e.getUtil().notClaimOwner("&6&oWorldGuard"));
				e.setCancelled(true);
			}
		}
	}

}
