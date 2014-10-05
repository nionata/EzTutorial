package me.smurfy129.tutorial.cmds;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.smurfy129.tutorial.CommandInfo;
import me.smurfy129.tutorial.Main;
import me.smurfy129.tutorial.TutorialCommand;

@CommandInfo(description = "It reloads the config", permission = "reload", usage = "", aliases = {"reload"}, argLengths = {0, 1})
public class Reload extends TutorialCommand{

	@Override
	public void onCommand(Player player, String[] args) {
		
		Main.plugin.reloadConfig();
		player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "The config reloaded succesfully!");
		
	}

}
