package fun.swip.durabarity;

import net.minecraft.network.chat.Component;

public class DurabilityState {
    public static Component currentMessage = null;
    public static Component item = null;
    public static Component percentage = null;
    public static Component durability = null;
    public static int displayTimer = 0;
    public static boolean increment = false;
}