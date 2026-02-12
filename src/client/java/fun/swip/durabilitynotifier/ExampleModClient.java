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
				drawContext.drawCenteredString(client.font, DurabilityState.currentMessage, x, y, color);
			}
		});
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (DurabilityState.displayTimer > 0) {
				DurabilityState.displayTimer--;
			}
		});
	}
}