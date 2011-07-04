
package com.xpansive.bukkit.worldgen;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpansiveTerrainCommandExec implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            player.sendMessage("Loading world... Please be paitent!");
            player.teleport(ExpansiveTerrain.getExpansiveTerrainWorld().getSpawnLocation());
        } else {
            sender.sendMessage("This command needs to be run as a player!");
        }

        return true;
    }
}
