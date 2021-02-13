package com.aguadelamiseria.fahrenheit;

import com.aguadelamiseria.fahrenheit.modules.CoreModule;
import com.aguadelamiseria.fahrenheit.service.Service;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * The JavaPlugin implementation that initializes the dependency injection and starts the core service
 * @see JavaPlugin
 */
public class Fahrenheit extends JavaPlugin {

    @Inject
    @Named("core-service")
    private Service coreService;

    @Override
    public void onEnable() {
        Injector injector = Guice.createInjector(new CoreModule(this));
        injector.injectMembers(this);
        coreService.start();
    }

    @Override
    public void onDisable() {
        coreService.end();
    }
}