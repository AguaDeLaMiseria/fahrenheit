package com.aguadelamiseria.fahrenheit.services;

import com.aguadelamiseria.fahrenheit.api.FahrenheitApi;
import com.aguadelamiseria.fahrenheit.commands.RankupCommand;
import com.aguadelamiseria.fahrenheit.files.ConfigurationFile;
import com.aguadelamiseria.fahrenheit.placeholder.PlaceholderHook;
import com.aguadelamiseria.fahrenheit.rank.RankManager;
import com.aguadelamiseria.fahrenheit.service.Service;
import org.bukkit.Bukkit;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * A {@link Service} implementation that starts the components of the plugin and registers the application interface
 */
public class CoreService implements Service {

    @Inject
    private JavaPlugin plugin;

    @Inject
    @Named("ranks")
    private ConfigurationFile rankFile;

    @Inject
    private RankManager rankManager;

    @Inject
    private RankupCommand rankupCommand;

    @Inject
    private PlaceholderHook placeholderHook;

    public void start() {
        placeholderHook.register();
        rankupCommand.register();
        rankManager.load(rankFile.get());

        Bukkit.getServicesManager().register(FahrenheitApi.class, rankManager, plugin, ServicePriority.Highest);
    }

    public void end() {

    }
}
