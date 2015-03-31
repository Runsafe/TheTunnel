package no.runsafe.thetunnel;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.player.IPlayerCustomEvent;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.event.player.RunsafeCustomEvent;
import no.runsafe.framework.minecraft.player.RunsafePlayer;
import no.runsafe.worldguardbridge.WorldGuardInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SectionHandler implements IPlayerCustomEvent, IConfigurationChanged
{
	public SectionHandler(Section[] sections, WorldGuardInterface worldGuard)
	{
		this.sections = sections;
		this.worldGuard = worldGuard;
	}

	public List<RunsafePlayer> getPlayersInSection(Section section)
	{
		RunsafeWorld world = getTunnelWorld();
		if (world != null)
			return worldGuard.getPlayersInRegion(world, section.getSectionRegion());

		return new ArrayList<RunsafePlayer>();
	}

	private RunsafeWorld getTunnelWorld()
	{
		return tunnelWorld != null ? RunsafeServer.Instance.getWorld(tunnelWorld) : null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void OnPlayerCustomEvent(RunsafeCustomEvent event)
	{
		if (tunnelWorld == null)
			return;

		String eventName = event.getEvent();
		Map<String, String> data = (Map<String, String>) event.getData();
		String worldName = data.get("world");
		String regionName = data.get("region");

		if (!worldName.equals(tunnelWorld))
			return;

		if (eventName.equals("region.enter"))
		{
			// Loop through every section we have loaded.
			for (Section section : sections)
			{
				// Check if we detected the section.
				if (section.getSectionRegion().equals(regionName))
				{
					section.onPlayerEnterSection(event.getPlayer()); // Trigger the enter event for the section.
					// ToDo: Count players in the section and react properly.
				}
			}
		}
		else if (eventName.equals("region.leave"))
		{
			// Loop through every section we have loaded.
			for (Section section : sections)
			{
				// Check if we detected the section.
				if (section.getSectionRegion().equals(regionName))
				{
					section.onPlayerLeaveSection(event.getPlayer()); // Trigger the leave event for the section.
					// ToDo: Count players in the section and react properly.
				}
			}
		}
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		tunnelWorld = configuration.getConfigValueAsString("tunnelWorld");
	}

	private Section[] sections;
	private String tunnelWorld;
	private WorldGuardInterface worldGuard;
}
