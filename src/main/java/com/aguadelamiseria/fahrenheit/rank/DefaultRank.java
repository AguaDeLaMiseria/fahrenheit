package com.aguadelamiseria.fahrenheit.rank;

import com.aguadelamiseria.fahrenheit.files.ConfigurationFile;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link Rank} that is used for players that don't have any rank
 */
public class DefaultRank implements Rank {

    private final String defaultId, defaultName, defaultTag;

    private final Set<Player> players;

    @Inject
    public DefaultRank(@Named("config") ConfigurationFile config){
        FileConfiguration configuration = config.get();
        defaultId = configuration.getString("default-rank.id");
        defaultName = configuration.getString("default-rank.name");
        defaultTag = configuration.getString("default-rank.tag");

        players = new HashSet<>();
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public int getCost() {
        return 0;
    }

    @Override
    public String getId() {
        return defaultId;
    }

    @Override
    public String getDisplayName() {
        return defaultName;
    }

    @Override
    public String getTag() {
        return defaultTag;
    }

    @Override
    public List<String> getCommands() {
        return Collections.emptyList();
    }

    @Override
    public Set<Player> getPlayers() {
        return players;
    }

    @Override
    public void reward(Player player) {}
}
