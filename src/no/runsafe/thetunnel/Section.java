package no.runsafe.thetunnel;

import no.runsafe.framework.minecraft.player.RunsafePlayer;

public abstract class Section implements ISection
{
	public void onPlayerEnterSection(RunsafePlayer player)
	{
		// Overwrite this.
	}

	public void onPlayerLeaveSection(RunsafePlayer player)
	{
		// Overwrite this.
	}

	public void setLoaded(boolean state)
	{
		loaded = state;
	}

	public boolean isLoaded()
	{
		return loaded;
	}

	private boolean loaded;
}
