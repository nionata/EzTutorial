package me.smurfy129.tutorial.cmds;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.smurfy129.tutorial.CommandInfo;
import me.smurfy129.tutorial.Main;
import me.smurfy129.tutorial.TutorialCommand;

@CommandInfo(description = "Starts yourself in a tutorial", permission = "start", usage = "<tutorial name>", aliases = {"start"}, argLengths = {1, 2})
public class Start extends TutorialCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		
		String tutorialName = args[0];
		
		if(Main.plugin.getConfig().contains("Tutorials." + tutorialName)) {
			player.sendMessage(ChatColor.AQUA + "Attention " + player.getDisplayName() + " a staff member is putting you through the tutorial \"" + tutorialName + "\"");
			Main.plugin.startTutorial(player, tutorialName);
		} else {
			player.sendMessage(ChatColor.RED + "The tutorial \"" + tutorialName + "\" does not exist");
		}
		


		
	}

}
