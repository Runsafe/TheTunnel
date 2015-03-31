package no.runsafe.thetunnel;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.thetunnel.sections.TestSection;
import no.runsafe.worldguardbridge.WorldGuardInterface;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void pluginSetup()
	{
		addComponent(getFirstPluginAPI(WorldGuardInterface.class));

		// Sections
		addComponent(TestSection.class);

		// Handlers
		addComponent(SectionHandler.class);
	}
}
