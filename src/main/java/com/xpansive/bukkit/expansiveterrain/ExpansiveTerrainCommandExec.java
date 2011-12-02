package com.xpansive.bukkit.expansiveterrain;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpansiveTerrainCommandExec implements CommandExecutor {
    ExpansiveTerrain plugin;

    public ExpansiveTerrainCommandExec(ExpansiveTerrain plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage("Loading world... Please be paitent!");
            player.teleport(plugin.getExpansiveTerrainWorld().getSpawnLocation());
        } else {
            sender.sendMessage("This command needs to be run as a player!");
        }

        return true;
    }
}
