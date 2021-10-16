package com.youtube.hempfest.clansflag;

import com.github.sanctum.clans.events.core.LandPreClaimEvent;
import com.github.sanctum.clans.events.core.LandUnClaimEvent;
import com.github.sanctum.labyrinth.LabyrinthProvider;
import com.github.sanctum.labyrinth.event.custom.Subscribe;
import com.github.sanctum.labyrinth.event.custom.Vent;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ClansFlag extends JavaPlugin {

	@Override
	public void onEnable() {
		LabyrinthProvider.getInstance().getEventMap().subscribe(this, this);
	}

	@Override
	public void onLoad() {
		WorldGuard worldGuard = WorldGuard.getInstance();
		FlagRegistry flagRegistry = worldGuard.getFlagRegistry();
		flagRegistry.register(IWGClaimCheck.CLAIM_LAND = new StateFlag("claim-land", false));
	}

	@Override
	public void onDisable() {
		// Plugin shutdown logic
	}

	@Subscribe(priority = Vent.Priority.HIGHEST)
	public void onLandPreClaim(LandPreClaimEvent e) {
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			if (IWGClaimCheck.isFlagged(e.getClaimer())) {
				e.getUtil().sendMessage(e.getClaimer(), e.getUtil().notClaimOwner("&6&oWorldGuard"));
				e.setCancelled(true);
			}
		}
	}

	@Subscribe(priority = Vent.Priority.HIGHEST)
	public void onLandUnClaim(LandUnClaimEvent e) {
		if (Bukkit.getPluginManager().isPluginEnabled("WorldGuard")) {
			if (IWGClaimCheck.isFlagged(e.getRemover())) {
				e.getUtil().sendMessage(e.getRemover(), e.getUtil().notClaimOwner("&6&oWorldGuard"));
				e.setCancelled(true);
			}
		}
	}

}
