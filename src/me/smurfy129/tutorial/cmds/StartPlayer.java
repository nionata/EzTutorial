package me.smurfy129.tutorial.cmds;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import me.smurfy129.tutorial.CommandInfo;
import me.smurfy129.tutorial.Main;
import me.smurfy129.tutorial.TutorialCommand;

@CommandInfo(description = "Starts a target player in a tutorial", permission = "start.player", usage = "<tutorial name> <target player>", aliases = {"startplayer", "sp"}, argLengths = {2, 3})
public class StartPlayer extends TutorialCommand {

	@Override
	public void onCommand(Player player, String[] args) {

		@SuppressWarnings("deprecation")
		Player targetPlayer = Bukkit.getPlayer(args[1]);
		String tutorialName = args[0];
		if(Main.plugin.getConfig().contains("Tutorials." + tutorialName)) {
			if(targetPlayer != player) {
				if(targetPlayer != null) {
					targetPlayer.sendMessage(ChatColor.AQUA + "Attention " + targetPlayer.getDisplayName() + " a staff member is putting you through the tutorial \"" + tutorialName + "\"");
					Main.plugin.startTutorial(targetPlayer, tutorialName);
				} else {
					player.sendMessage(ChatColor.RED + "The target player is not online!");
				}	
			} else {
				player.sendMessage(ChatColor.RED + "You can't start yourself in a tutorial!");
			}
		} else {
			player.sendMessage(ChatColor.RED + "The tutorial \"" + tutorialName + "\" does not exist");
		}

	}

}
