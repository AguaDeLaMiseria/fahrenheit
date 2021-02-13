package com.aguadelamiseria.fahrenheit.rank;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

/**
 * Represents a rank, which is used to organize and reward players based on permissions
 */
public interface Rank {

    /**
     * Gets the position which represents the rank level, where 0 is the lowest possible
     * @return The rank position
     */
    int getPosition();

    /**
     * Gets the cost that will be charged if a player decides to upgrade to this rank
     * @return The rank cost
     */
    int getCost();

    /**
     * Gets the ID which is the key in the rank section of the ranks file configuration
     * @return The ID of this rank
     */
    String getId();

    /**
     * Gets the friendly name to be displayed in the game
     * @return The display name of this rank
     */
    String getDisplayName();

    /**
     * Gets the tag that will be displayed next to the player's name
     * @return The tag of this rank
     */
    String getTag();

    /**
     * Gets a list with the command strings that will be used to reward the player after rank-up
     * @return A list of commands
     */
    List<String> getCommands();

    /**
     * Gets a set containing all the players that belong to this rank, a player can only have one rank at a time
     * @return A set of players
     */
    Set<Player> getPlayers();

    /**
     * Gets the permission that is used to determine whether a player belongs to this rank or not
     * @return This rank permission
     */
    default String getPermission(){
        return "fahrenheit.rank."+getId();
    }

    /**
     * Execute the commands returned by {@link #getCommands()} replacing "%player%" with the player's name
     * @param player The player who will be rewarded when acquiring this rank
     */
    default void reward(Player player) {
        getCommands().forEach(command ->
                Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        command.replace("%player%", player.getName())
                ));
    }
}
