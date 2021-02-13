package com.aguadelamiseria.fahrenheit.placeholder;

import com.aguadelamiseria.fahrenheit.rank.Rank;
import com.aguadelamiseria.fahrenheit.rank.RankManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import java.util.Optional;

public class PlaceholderHook extends PlaceholderExpansion {

    @Inject
    private JavaPlugin plugin;

    @Inject
    private RankManager rankManager;

    @Override
    public @NotNull String getIdentifier() {
        return "fahrenheit";
    }

    @Override
    public @NotNull String getAuthor() {
        return "AguaDeLaMiseria";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier){
        if (identifier.isEmpty()) return "";
        String[] parts = identifier.split("_");
        if (!parts[0].equalsIgnoreCase("rank")) return "";

        if (parts.length == 1){
            return rankManager.getPlayerRank(player).getDisplayName();
        } else
        if (parts.length == 2){
            if (parts[1].equalsIgnoreCase("tag")){
                return rankManager.getPlayerRank(player).getTag();
            }
        } else
        if (parts.length == 3){
            if (parts[1].equalsIgnoreCase("cost")){
                Optional<Rank> rank = rankManager.getRankById(parts[2]);
                return rank.map(value -> String.valueOf(value.getCost())).orElse("");
            }
        }
        return "";
    }

}
