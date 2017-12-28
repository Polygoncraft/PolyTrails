package com.github.cc007.polytrails.commands;

import com.github.cc007.polytrails.Trails.SimpleParticleTrail;
import com.github.cc007.polytrails.Trails.Trail;
import com.github.cc007.polytrails.Trails.Trails;
import com.github.cc007.polytrails.utils.TrailStore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;

import static org.bukkit.Bukkit.getServer;

public class TrailCommand implements CommandExecutor {

    private Map<UUID, Trail> users;

    public TrailCommand(Plugin parent) {
        users = TrailStore.retrieve();
        getServer().getScheduler().scheduleSyncRepeatingTask(parent, () -> {
                for (Map.Entry<UUID, Trail> p : users.entrySet()) {
                    Player player = Bukkit.getPlayer(p.getKey());
                    if (player != null) {
                        p.getValue().playEffect(player);
                    }
                }
        }, 1, 1);
    }

    public void removeTrail(Player p) {
        users.remove(p.getUniqueId());
        TrailStore.store(users);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String cmdAlias, String[] args) {
        if (!"trail".equalsIgnoreCase(cmd.getName()) || !(sender instanceof Player)) {
            sender.sendMessage("whomst are you");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage("Usage: /trail [effect|pattern] [flags]");
            sender.sendMessage("Unlocked trails: flame");
            return true;
        }
        String arg = args[0];
        switch (arg) {
            case "off":
                users.remove(((Player) sender).getUniqueId());
                break;
            case "list-simple":
                sender.sendMessage("Available trails: " + String.join(", ", SimpleParticleTrail.particleMap.keySet()));
                break;
            default:
                Trail t = Trails.fromString(arg);
                if(t != null && t.hasPermission((Player)sender)) {
                    users.put(((Player)sender).getUniqueId(), t);
                    TrailStore.store(users);
                } else {
                    sender.sendMessage("You don't have permission to do this command.");
                }
        }
        return true;
    }
}
