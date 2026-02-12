package fun.swip.durabilitynotifier.mixin;

import fun.swip.durabilitynotifier.Utils.NotifyPlayerUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceOrb.class)
public class MendingMixin {
    @Inject(method = "repairPlayerItems",at = @At("TAIL"))
    private void onRepairItem(ServerPlayer serverPlayer, int i, CallbackInfoReturnable<Integer> cir) {
        ItemStack itemStack = serverPlayer.getMainHandItem();
        var enchantmentRegistry = serverPlayer.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        var mendingHolder = enchantmentRegistry.getOrThrow(Enchantments.MENDING);
        int mendingLevel = EnchantmentHelper.getItemEnchantmentLevel(mendingHolder, itemStack);
        if (mendingLevel > 0) {
            NotifyPlayerUtil.notifyPlayer(serverPlayer, itemStack, true);
        }
    }
}
