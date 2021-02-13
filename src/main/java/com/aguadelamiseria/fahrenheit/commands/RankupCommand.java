package com.aguadelamiseria.fahrenheit.commands;

import com.aguadelamiseria.fahrenheit.rank.RankManager;
import com.aguadelamiseria.fahrenheit.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Objects;

public class RankupCommand implements CommandExecutor {

    @Inject
    private MessageUtils messageUtils;

    @Inject
    private RankManager rankManager;

    public void register(){
        PluginCommand command = Bukkit.getPluginCommand("rankup");
        Objects.requireNonNull(command);
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        RankManager.Response ranked = rankManager.rankUpPlayer(player);

        if (ranked == RankManager.Response.SUCCESS){
            messageUtils.send(player, "rankup.success");
        } else if (ranked == RankManager.Response.NOT_NEXT){
            messageUtils.send(player, "rankup.not-next");
        } else {
            messageUtils.send(player, "rankup.fail");
        }
        return true;
    }
}
