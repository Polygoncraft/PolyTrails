package com.github.cc007.polytrails;

import com.github.cc007.polytrails.commands.TrailCommand;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rik Schaaf aka CC007
 * @version ${project.version}, ${package.getClass().forName("java.time.LocalDateTime").getMethod("now").invoke(null).format($package.Class.forName("java.time.format.DateTimeFormatter").getMethod("ofPattern", $package.Class).invoke(null, "dd MMM yyyy"))}
 */
public class PolyTrails extends JavaPlugin {

    private static Logger log;
    private Plugin vault = null;
    private Permission permission = null;
    private FileConfiguration config = null;
    private File configFile = null;


    @Override
    public void onEnable() {
        saveDefaultConfig();

        /* Setup the logger */
        log = getLogger();

        /* Setup vault permissions */
        vault = getPlugin("Vault");
        if (vault != null) {
            setupPermissions();
        }

        /* Setup command executor */
        TrailCommand t = new TrailCommand(this);
        getCommand("trail").setExecutor(t);
    }


    @Override
    public void onDisable() {
    }

    /**
     * Gets a plugin
     *
     * @param pluginName Name of the plugin to get
     * @return The plugin from name
     */
    protected static Plugin getPlugin(String pluginName) {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(pluginName);
        if (plugin != null && plugin.isEnabled()) {
            return plugin;
        } else {
            log.log(Level.WARNING, "Could not find a plugin named \"{0}\"!", pluginName);
            return null;
        }
    }

    /**
     * Gets the current plugin
     *
     * @return The current plugin
     */
    public static PolyTrails getPlugin() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PolyTrails");
        if (plugin != null && plugin.isEnabled() && plugin instanceof PolyTrails) {
            return (PolyTrails) plugin;
        } else {
            log.log(Level.SEVERE, "This plugin (PolyTrails) has not been enabled yet!");
            return null;
        }
    }

    /**
     * Setup permissions
     *
     * @return whether the permissions were setup successfully
     */
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);

        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }

        if (permission == null) {
            getLogger().log(Level.WARNING, "Could not hook Vault!");
        } else {
            getLogger().log(Level.WARNING, "Hooked Vault!");
        }

        return (permission != null);
    }

    /**
     * Get the vault plugin
     *
     * @return the vault
     */
    public Plugin getVault() {
        return vault;
    }

    /**
     * Get the permissions object
     *
     * @return the permissions object
     */
    public Permission getPermission() {
        return permission;
    }

    /**
     * get the minecraft chat prefix for this plugin
     *
     * @return the minecraft chat prefix for this plugin
     */
    public static String pluginChatPrefix(boolean colored) {
        if (colored) {
            return ChatColor.DARK_BLUE + "[" + ChatColor.BLUE + "PolyTrails" + ChatColor.DARK_BLUE + "]" + ChatColor.RESET + " ";
        } else {
            return "[PolyTrails] ";
        }
    }

    /**
     * Method to reload the config.yml config file
     */
    @Override
    public void reloadConfig() {
        if (configFile == null) {
            configFile = new File(getDataFolder(), "config.yml");
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        // Look for defaults in the jar
        Reader defConfigStream = null;
        try {
            defConfigStream = new InputStreamReader(this.getResource("config.yml"), "UTF8");
        } catch (UnsupportedEncodingException ex) {
            getLogger().log(Level.SEVERE, null, ex);
        }
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            config.setDefaults(defConfig);
        }
    }

    /**
     * Method to get YML content of the config.yml config file
     *
     * @return YML content of the categories.yml config file
     */
    @Override
    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    /**
     * Method to save the config.yml config file
     */
    @Override
    public void saveConfig() {
        if (config == null || configFile == null) {
            return;
        }
        try {
            getConfig().save(configFile);
        } catch (IOException ex) {
            getLogger().log(Level.SEVERE, "Could not save config to " + configFile, ex);
        }
    }

    @Override
    public void saveDefaultConfig() {
        if (configFile == null) {
            configFile = new File(getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }
    }
}
