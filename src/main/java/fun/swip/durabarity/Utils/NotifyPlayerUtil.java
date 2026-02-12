package fun.swip.durabarity.Utils;

import fun.swip.durabarity.DurabilityState;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class NotifyPlayerUtil {
    public static void notifyPlayer(Player player, ItemStack stack, boolean increasement) {
        int remainingDurability = stack.getMaxDamage() - stack.getDamageValue();
        int remainingDurabilityPercentage = Math.round((float) remainingDurability / stack.getMaxDamage() * 100);
        Component changeIndicator = (increasement)? Component.literal("[+] ").withStyle(ChatFormatting.GREEN) : Component.literal("[-] ").withStyle(ChatFormatting.RED);
        Component displayName = Component.literal(stack.getDisplayName().getString()).withStyle(ChatFormatting.AQUA);
        Component remainingDurabilityComp = Component.literal(String.valueOf(remainingDurability)).withStyle(ChatFormatting.GREEN);
        Component stillHas = Component.literal(" still has ").withStyle(ChatFormatting.WHITE);
        Component durabilityText = Component.literal(" durability ").withStyle(ChatFormatting.WHITE);
        Component durabilityPercentageComp = Component.literal(" [" + String.valueOf(remainingDurabilityPercentage) + "%]").withStyle(ChatFormatting.GREEN);

        Component message = Component.empty()
                .append(changeIndicator)
                .append(displayName)
                .append(stillHas)
                .append(remainingDurabilityComp)
                .append(durabilityText)
                .append(durabilityPercentageComp);
        player.displayClientMessage(message, true);
        System.out.println(remainingDurability);

        // Store it in our bridge class
        DurabilityState.currentMessage = message;
        DurabilityState.displayTimer = 60; // Start a 3-second countdown (20 ticks = 1s)
    }
}
