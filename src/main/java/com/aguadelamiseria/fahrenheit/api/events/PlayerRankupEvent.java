package com.aguadelamiseria.fahrenheit.api.events;

import com.aguadelamiseria.fahrenheit.rank.Rank;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * API Event which is called whenever decides to rank-up and has the requisites to do it
 */
public class PlayerRankupEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Rank oldRank;

    private Rank newRank;
    private boolean cancelled;

    public PlayerRankupEvent(Player player, Rank oldRank, Rank newRank){
        this.player = player;
        this.oldRank = oldRank;
        this.newRank = newRank;
    }

    /**
     * @return The player who decides to rank-up
     */
    @NotNull
    public Player getPlayer(){
        return player;
    }

    /**
     * @return The current {@link Rank} the player has, can not be modified
     */
    @NotNull
    public Rank getOldRank(){
        return oldRank;
    }

    /**
     * @return The {@link Rank} the player will have if the event is not cancelled
     */
    @NotNull
    public Rank getNewRank(){
        return newRank;
    }

    /**
     * Changes the rank the player will rank-up to a different one
     * @param rank The new rank the player will have
     */
    public void setNewRank(@NotNull Rank rank){
        Objects.requireNonNull(rank);
        this.newRank = rank;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
