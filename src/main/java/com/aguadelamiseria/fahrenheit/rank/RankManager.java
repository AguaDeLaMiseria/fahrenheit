package com.aguadelamiseria.fahrenheit.rank;

import com.aguadelamiseria.fahrenheit.api.FahrenheitApi;
import com.aguadelamiseria.fahrenheit.api.events.PlayerRankupEvent;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class RankManager implements Listener, FahrenheitApi {

    @Inject
    @Named("default")
    private Rank defaultRank;

    @Inject
    private Economy economy;

    @Inject
    private Permission permission;

    @Inject
    private void register(JavaPlugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final Set<Rank> ranks = new HashSet<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        handleJoin(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        ranks.forEach(rank -> rank.getPlayers().remove(event.getPlayer())); // Just to be on the safe side
    }

    public void handleJoin(Player player){
        Rank rank = ranks
                .stream()
                .filter(r -> player.hasPermission(r.getPermission()))
                .findFirst().orElse(defaultRank);
        rank.getPlayers().add(player);
    }

    @NotNull
    public Response rankUpPlayer(Player player){
        Rank next = getNextRank(player);
        if (next == null) return Response.NOT_NEXT;
        Rank current = getPlayerRank(player);
        int cost = next.getCost();

        if (!economy.has(Bukkit.getOfflinePlayer(player.getUniqueId()), cost)) return Response.FAIL;

        PlayerRankupEvent rankupEvent = new PlayerRankupEvent(player, current, next);
        Bukkit.getPluginManager().callEvent(rankupEvent);

        if (rankupEvent.isCancelled()) return Response.NOT_NEXT;
        if (next != rankupEvent.getNewRank()) next = rankupEvent.getNewRank();

        economy.withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), cost);
        current.getPlayers().remove(player);
        next.getPlayers().add(player);
        permission.playerRemove(null, player, current.getPermission());
        permission.playerAdd(null, player, next.getPermission());
        next.reward(player);

        return Response.SUCCESS;
    }

    public void load(FileConfiguration configuration){
        ranks.add(defaultRank);
        ConfigurationSection section = configuration.getConfigurationSection("ranks");
        Objects.requireNonNull(section);
        int counter = 1;
        for (String rankId : section.getKeys(false)){
            ConfigurationSection rankSection = section.getConfigurationSection(rankId);
            Objects.requireNonNull(rankSection);
            String displayName = rankSection.getString("name");
            String tag = rankSection.getString("tag");
            List<String> commands = rankSection.getStringList("commands");
            int cost = rankSection.getInt("cost");
            Rank rank = new RankImpl(counter, cost, rankId, displayName, tag, commands);
            ranks.add(rank);
            counter++;
        }
    }

    public Rank getPlayerRank(@NotNull Player player){
        Objects.requireNonNull(player);
        return ranks.stream()
                .filter(rank -> rank.getPlayers().contains(player))
                .findFirst().orElse(null);
    }

    @Nullable
    public Rank getNextRank(@NotNull Player player){
        Objects.requireNonNull(player);
        Rank current = getPlayerRank(player);
        int nextPosition = current.getPosition() + 1;
        Optional<Rank> rankOpt = ranks
                .stream()
                .filter(rank -> rank.getPosition() == nextPosition)
                .findFirst();
        return rankOpt.orElse(null);
    }

    @NotNull
    public Optional<Rank> getRankById(@NotNull String id){
        Objects.requireNonNull(id);
        return ranks.stream()
                .filter(rank -> rank.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    @Override
    public Set<Rank> getRanks() {
        return ranks;
    }

    @Override
    public void setRank(@NotNull Player player, @NotNull Rank newRank) {
        setRank(player, newRank, false, false);
    }

    @Override
    public void setRank(@NotNull Player player, @NotNull Rank newRank, boolean reward) {
        setRank(player, newRank, reward, false);
    }

    @Override
    public void setRank(@NotNull Player player, @NotNull Rank newRank, boolean reward, boolean charged) {
        Objects.requireNonNull(player);
        Objects.requireNonNull(newRank);

        Rank current = getPlayerRank(player);

        PlayerRankupEvent rankupEvent = new PlayerRankupEvent(player, current, newRank);
        Bukkit.getPluginManager().callEvent(rankupEvent);

        if (rankupEvent.isCancelled()) return;
        if (newRank != rankupEvent.getNewRank()) newRank = rankupEvent.getNewRank();

        if (charged){
            int cost = newRank.getCost();
            economy.withdrawPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()), cost);
        }

        current.getPlayers().remove(player);
        newRank.getPlayers().add(player);
        permission.playerRemove(null, player, current.getPermission());
        permission.playerAdd(null, player, newRank.getPermission());
        if (reward) newRank.reward(player);
    }

    public enum Response {
        SUCCESS,
        NOT_NEXT,
        FAIL
    }
}
