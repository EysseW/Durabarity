package fun.swip.durabilityNotifier.mixin;

import fun.swip.durabilityNotifier.Utils.NotifyPlayerUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Inject(method = "applyDamage", at = @At("TAIL"))
    private <T extends LivingEntity> void notifyOwner(int i, @Nullable ServerPlayer serverPlayer, Consumer<Item> consumer, CallbackInfo ci) {
        if (serverPlayer instanceof Player player && !serverPlayer.level().isClientSide()) {
            ItemStack item = serverPlayer.getMainHandItem();
            NotifyPlayerUtil.notifyPlayer(player, item, false);
        }
    }
}