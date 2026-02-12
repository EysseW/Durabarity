package fun.swip.durabilitynotifier.mixin.client;

import fun.swip.durabilitynotifier.DurabilityState;
import net.minecraft.client.gui.Gui;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "setOverlayMessage", at = @At("HEAD"), cancellable = true)
    private void onSetOverlayMessage(Component message, boolean animateColor, CallbackInfo ci) {
        String content = message.getString();

        // DEBUG: This will print EVERY action bar message to the console
        System.out.println("[DurabilityMod] Intercepted message: " + content);

        // Check for our symbols. We use .contains() to be safe.
        if (content.contains("[+] ") || content.contains("[-] ")) {
            System.out.println("[DurabilityMod] Matches durability format! Redirecting to custom HUD.");

            DurabilityState.currentMessage = message;
            DurabilityState.displayTimer = 80; // Increased to 4 seconds for testing

            ci.cancel(); // Hide from vanilla
        }
    }
}