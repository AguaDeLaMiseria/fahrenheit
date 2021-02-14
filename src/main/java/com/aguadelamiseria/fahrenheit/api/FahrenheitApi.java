package com.aguadelamiseria.fahrenheit.api;

import com.aguadelamiseria.fahrenheit.rank.DefaultRank;
import com.aguadelamiseria.fahrenheit.rank.Rank;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

/**
 * Represents the interface for Fahrenheit providing useful methods for add-on development
 * @version 1.1.0
 * @see Rank
 */
public interface FahrenheitApi {

    /**
     * @param player The player to get the next rank for
     * @return A non-null {@link Rank}
     */
    Rank getPlayerRank(@NotNull Player player);

    /**
     * @param player The player to get the next rank for
     * @return A {@link Rank] which might be null if there isn't any rank next
     */
    Rank getNextRank(@NotNull Player player);

    /**
     * @param id The id of the Rank to search for
     * @return An optional {@link Rank} which is empty if there isn't any rank with the given id
     * @see Optional
     */
    Optional<Rank> getRankById(@NotNull String id);

    /**
     * @return A Set containing all existing {@link Rank}s including {@link DefaultRank}}
     */
    Set<Rank> getRanks();

    /**
     * Changes the rank of the player to a new one without cost and without rewarding
     * @param player The player who will have his rank changed
     * @param newRank Thew new rank the player will have
     */
    void setRank(@NotNull Player player, @NotNull Rank newRank);

    /**
     * Changes the rank of the player to a new one without cost, optionally rewarded
     * @param player The player who will have his rank changed
     * @param newRank Thew new rank the player will have
     * @param reward Whether the player will be rewarded or not
     */
    void setRank(@NotNull Player player, @NotNull Rank newRank, boolean reward);

    /**
     * Changes the rank of the player to a new one, optionally rewarded and optionally charged.
     * If the player has not enough money, it will not be charged, so make sure of this first.
     * @param player The player who will have his rank changed
     * @param newRank Thew new rank the player will have
     * @param reward Whether the player will be rewarded or not
     * @param charged Whether money will be withdrawn from the player or not
     */
    void setRank(@NotNull Player player, @NotNull Rank newRank, boolean reward, boolean charged);
}
