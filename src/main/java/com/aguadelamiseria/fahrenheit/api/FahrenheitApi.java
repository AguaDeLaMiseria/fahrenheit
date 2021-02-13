package com.aguadelamiseria.fahrenheit.api;

import com.aguadelamiseria.fahrenheit.rank.DefaultRank;
import com.aguadelamiseria.fahrenheit.rank.Rank;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

/**
 * Represents the interface for Fahrenheit providing useful methods for add-on development
 * @version 1.0.0
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
}
