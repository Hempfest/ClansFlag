package com.youtube.hempfest.clansflag;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.sk89q.worldguard.session.SessionManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class IWGClaimCheck {

	public static StateFlag CLAIM_LAND;

	public static boolean isFlagged(Player p) {
		World wrappedWorld = BukkitAdapter.adapt(p.getWorld());
		WorldGuardPlugin plugin = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
		assert plugin != null;
		LocalPlayer wrappedPlayer = plugin.wrapPlayer(p);
		SessionManager sessionManager = WorldGuard.getInstance().getPlatform().getSessionManager();
		RegionQuery query = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
		com.sk89q.worldedit.util.Location wrappedLocation = BukkitAdapter.adapt(p.getLocation());
		LocalPlayer localPlayer = plugin.wrapPlayer(p);
		return !sessionManager.hasBypass(wrappedPlayer, wrappedWorld) && !query.testBuild(wrappedLocation, localPlayer, CLAIM_LAND);
	}

	public static boolean isRegion(Location loc) {
		WorldGuardPlatform platform = WorldGuard.getInstance().getPlatform();
		RegionContainer container = platform.getRegionContainer();
		RegionManager regionManager = container.get(BukkitAdapter.adapt(loc.getWorld()));
		if (regionManager == null) {
			return false;
		}
		ApplicableRegionSet set = regionManager.getApplicableRegions(BukkitAdapter.asBlockVector(loc));
		return set.size() > 0;
	}

}
