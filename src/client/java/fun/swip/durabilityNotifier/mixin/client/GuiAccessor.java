package fun.swip.durabilityNotifier.mixin.client;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Gui.class)
public interface GuiAccessor {
    // This creates a public 'getter' for the protected 'overlayMessageTime' field
    @Accessor("overlayMessageTime")
    int getOverlayMessageTime();
}