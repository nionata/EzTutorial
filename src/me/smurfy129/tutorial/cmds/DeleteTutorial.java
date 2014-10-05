package me.smurfy129.tutorial.cmds;

import me.smurfy129.tutorial.CommandInfo;
import me.smurfy129.tutorial.SettingsManager;
import me.smurfy129.tutorial.TutorialCommand;
import me.smurfy129.tutorial.TutorialManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@CommandInfo(description = "Deletes a tutorial", permission = "delete.tutorial", usage = "<tutorial name>", aliases = {"delete", "del"}, argLengths = {1, 2})
public class DeleteTutorial extends TutorialCommand {
	
	public void onCommand(Player player, String[] args) {
		
		String tutorialName = args[0];
		
		if(TutorialManager.getInstance().getTutorial(tutorialName) != null) {
			SettingsManager.getTutorials().set("Tutorials." + tutorialName , null);
			SettingsManager.getConfig().set("Tutorials." + tutorialName , null);
			player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "The tutorial " + ChatColor.GRAY + "\"" + tutorialName + "\"" + ChatColor.AQUA + " has been deleted");
		} else {
			player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "The tutorial \"" + ChatColor.GRAY + tutorialName + ChatColor.AQUA + "\" does not exist!");
		}
		
		
	}	
}
