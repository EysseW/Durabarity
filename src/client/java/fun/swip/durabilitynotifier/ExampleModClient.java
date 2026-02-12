package fun.swip.durabilitynotifier;

import fun.swip.durabilitynotifier.mixin.client.GuiAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		System.out.println("!!! DURABILITY MOD: CLIENT INIT !!!");
		if (DurabilityState.displayTimer > 0) {
			System.out.println("Rendering Bar: " + DurabilityState.currentMessage.getString());
		}
		HudRenderCallback.EVENT.register((drawContext, renderTickCounter) -> {
			Minecraft client = Minecraft.getInstance();
			if (client.player == null) return;

			// Use the SHARED state class
			if (DurabilityState.currentMessage != null && DurabilityState.displayTimer > 0) {
				System.out.println(DurabilityState.currentMessage);
				int screenWidth = drawContext.guiWidth();// client.getWindow().getGuiScaledWidth();
				int screenHeight = drawContext.guiHeight(); //client.getWindow().getGuiScaledHeight();

				// Position math
				int x = screenWidth / 2;
				int y = screenHeight / 2;
				y += (int) (((double) screenHeight / 8) * 2.75);
				if (((GuiAccessor)client.gui).getOverlayMessageTime() > 0) {
					y -= screenHeight / 40;
				}

				drawContext.drawCenteredString(client.font, DurabilityState.currentMessage, x, y,0xFFFFFFFF);

			}
		});
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (DurabilityState.displayTimer > 0) {
				DurabilityState.displayTimer--;
			}
		});
	}
}