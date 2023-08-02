package ru.quantum_emperor.disable_portals;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.quantum_emperor.disable_portals.events.PortalActivationEvent;

public class ExampleMod implements ModInitializer {

	public static final String ID = "disable_portals";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		UseBlockCallback.EVENT.register(new PortalActivationEvent());
		LOGGER.info("Hello Fabric world!");
	}
}
