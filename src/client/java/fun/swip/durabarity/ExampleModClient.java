package fun.swip.durabarity;

import eu.midnightdust.lib.config.MidnightConfig;
import fun.swip.durabarity.config.Config;
import fun.swip.durabarity.mixin.client.GuiAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;

import java.util.Locale;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		System.out.println("!!! DURABILITY MOD: CLIENT INIT !!!");
		if (DurabilityState.displayTimer > 0) {
			System.out.println("Rendering Bar: " + DurabilityState.currentMessage.getString());
		}
		MidnightConfig.init("durabarity", Config.class);
		// Initialize the config
		MidnightConfig.init("durabarity", Config.class);

		// Example Event
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			// THE KEY CHECK:
			if (!Config.enabled) {
				return InteractionResult.PASS; // Do nothing if mod is disabled
			}

			// ... Your mod logic here ...
			return InteractionResult.PASS;
		});
		HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
			if (!Config.enabled) { return; }
			Minecraft client = Minecraft.getInstance();
			if (client.player == null) return;

			// Use the SHARED state class
			if (DurabilityState.currentMessage != null && DurabilityState.displayTimer > 0) {
				// Set the message to reflect the player's preference
				String message = DurabilityState.currentMessage.getString();
				String item = DurabilityState.item.getString();
				String durability = DurabilityState.durability.getString();
				String percentage = DurabilityState.percentage.getString();

				String user_message = (message.contains("[+] "))? Config.increment_format : Config.decrement_format;
				String colored = user_message
						.replace("%item%",item)
						.replace("%durability%", durability)
						.replace("%percentage%", percentage)
						.replace('&', '§');

				int screenWidth = client.getWindow().getGuiScaledWidth();
				int screenHeight = client.getWindow().getGuiScaledHeight();

				// Position math
				// The vanilla actionbar is 68 pixels from the center of the screen
				int yOffset = 68;
				if (((GuiAccessor)client.gui).getOverlayMessageTime() > 0) {
					yOffset += 16;
				}

				// 3. Final Coordinates
				int x = screenWidth / 2;
				int y = screenHeight - yOffset;

				int alpha = Math.min(255, (int)(DurabilityState.displayTimer * 10));
				int color = (alpha << 24) | 0xFFFFFF; // Combined with solid white
				drawContext.drawCenteredString(client.font, colored, x, y, color);
			}
		});
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (DurabilityState.displayTimer > 0) {
				DurabilityState.displayTimer--;
			}
		});
	}
}