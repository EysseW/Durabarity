package fun.swip.durabarity.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.resources.Identifier;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/** * Every option in a MidnightConfig class has to be public and static.
 * The config class also has to extend MidnightConfig.
 */
public class Config extends MidnightConfig {
    public static final String GENERAL = "General";
    @Entry(category = GENERAL, name="Enabled") public static boolean enabled = true;
    @Entry(category = GENERAL, name="Increment message format") public static String increment_format = "&a[+] &b%item% &fhas &a%durability% &fleft &a[%percentage%]";
    @Entry(category = GENERAL, name="Decrement message format") public static String decrement_format = "&c[-] &b%item% &fhas &a%durability% &fleft &a[%percentage%]";
}