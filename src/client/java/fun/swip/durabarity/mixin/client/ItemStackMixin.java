package fun.swip.durabarity.mixin.client;

import fun.swip.durabarity.Utils.NotifyPlayerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
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

@Mixin(ClientPacketListener.class)
public abstract class ItemStackMixin {
    @Inject(method = "handleContainerSetSlot", at = @At("HEAD"))
    private <T extends LivingEntity> void notifyOwner(ClientboundContainerSetSlotPacket packet, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        // Container ID 0 is the player's inventory
        // packet.getSlot() is the slot being updated
        if (packet.getContainerId() == 0 && packet.getSlot() == (client.player.getInventory().getSelectedSlot() + 36)) {
            ItemStack newItem = packet.getItem();
            ItemStack oldItem = client.player.getMainHandItem();

            if (!newItem.isEmpty() && !oldItem.isEmpty() && newItem.getItem() == oldItem.getItem()) {
                int newDamage = newItem.getDamageValue();
                int oldDamage = oldItem.getDamageValue();

                if (newDamage != oldDamage) {
                    boolean isRepair = newDamage < oldDamage;
                    NotifyPlayerUtil.notifyPlayer(client.player, newItem, isRepair);
                }
            }
        }
    }
}