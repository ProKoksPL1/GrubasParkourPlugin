package aybici.parkourplugin.utils;

import net.md_5.bungee.api.ChatColor;

public class ChatUtil {

    public static String fixColor(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
