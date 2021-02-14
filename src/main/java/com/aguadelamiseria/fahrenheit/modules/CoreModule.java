package com.aguadelamiseria.fahrenheit.modules;

import com.aguadelamiseria.fahrenheit.Fahrenheit;
import com.aguadelamiseria.fahrenheit.files.ConfigurationFile;
import com.aguadelamiseria.fahrenheit.placeholder.PlaceholderHook;
import com.aguadelamiseria.fahrenheit.rank.DefaultRank;
import com.aguadelamiseria.fahrenheit.rank.Rank;
import com.aguadelamiseria.fahrenheit.rank.RankManager;
import com.aguadelamiseria.fahrenheit.service.Service;
import com.aguadelamiseria.fahrenheit.services.CoreService;
import com.aguadelamiseria.fahrenheit.utils.MessageUtils;
import me.yushust.inject.Binder;
import me.yushust.inject.Module;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreModule implements Module {

    private final Fahrenheit fahrenheit;

    public CoreModule(Fahrenheit fahrenheit){
        this.fahrenheit = fahrenheit;
    }

    @Override
    public void configure(Binder binder) {

        binder.bind(Fahrenheit.class).toInstance(fahrenheit);
        binder.bind(JavaPlugin.class).toInstance(fahrenheit);

        binder.bind(Service.class).named("core-service").to(CoreService.class).singleton();

        binder.bind(Economy.class).toProvider(() -> {
            ServicesManager manager = fahrenheit.getServer().getServicesManager();
            RegisteredServiceProvider<Economy> rspEc = manager.getRegistration(Economy.class);
            if (rspEc == null) {
                System.out.println("Economy plugin not found");
                fahrenheit.getServer().getPluginManager().disablePlugin(fahrenheit);
                return null;
            }
            return rspEc.getProvider();
        });

        binder.bind(Permission.class).toProvider(() -> {
            ServicesManager manager = fahrenheit.getServer().getServicesManager();
            RegisteredServiceProvider<Permission> rspPe = manager.getRegistration(Permission.class);
            if (rspPe == null) {
                System.out.println("Permission plugin not found");
                fahrenheit.getServer().getPluginManager().disablePlugin(fahrenheit);
                return null;
            }
            return rspPe.getProvider();
        });

        binder.bind(ConfigurationFile.class).named("config")
                .toInstance(new ConfigurationFile(fahrenheit, "config.yml"));

        binder.bind(ConfigurationFile.class).named("ranks")
                .toInstance(new ConfigurationFile(fahrenheit, "ranks.yml"));

        binder.bind(MessageUtils.class).singleton();
        binder.bind(PlaceholderHook.class).singleton();

        binder.bind(Rank.class).named("default").to(DefaultRank.class).singleton();
        binder.bind(RankManager.class).singleton();
    }
}