package com.aguadelamiseria.fahrenheit.rank;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link Rank} that is used for the ranks specified in the ranks file configuration
 */
public class RankImpl implements Rank {

    private final int position, cost;
    private final String id, displayName, tag;
    private final List<String> commands;
    private final Set<Player> players;

    public RankImpl(int position, int cost, String id, String displayName, String tag, List<String> commands){
        this.position = position;
        this.cost = cost;
        this.id = id;
        this.displayName = displayName;
        this.tag = tag;
        this.commands = new ArrayList<>(commands);
        this.players = new HashSet<>();
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public List<String> getCommands() {
        return commands;
    }

    @Override
    public Set<Player> getPlayers() {
        return players;
    }
}
