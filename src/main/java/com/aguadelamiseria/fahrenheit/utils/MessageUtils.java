package com.aguadelamiseria.fahrenheit.utils;

import com.aguadelamiseria.fahrenheit.files.ConfigurationFile;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;

public class MessageUtils {

    @Inject
    @Named("config")
    private ConfigurationFile msgConfig;

    public void send(Player player, String id){
        String message = msgConfig.get().getString("messages."+id);
        if (message == null || message.isEmpty()) return;
        player.sendMessage(format(PlaceholderAPI.setPlaceholders(player, message)));
    }

    public static String format(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

}
