package me.smurfy129.tutorial.cmds;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.smurfy129.tutorial.CommandInfo;
import me.smurfy129.tutorial.SettingsManager;
import me.smurfy129.tutorial.TutorialCommand;
import me.smurfy129.tutorial.TutorialManager;

@CommandInfo(description = "Deletes a location", permission = "delete.location", usage = "<tutorial name> <location id>", aliases = {"deletelocation", "delloc", "dloc"}, argLengths = {2, 3})
public class DeleteLocation extends TutorialCommand {

	@Override
	public void onCommand(Player player, String[] args) {

		String tutorialName = args[0];

		if(TutorialManager.getInstance().getTutorial(tutorialName) != null) {
			try {
				int locationNumber = Integer.parseInt(args[1]);

				if(TutorialManager.getInstance().getLocation(locationNumber) != null) {
					SettingsManager.getConfig().set("Tutorials." + tutorialName + "." + locationNumber, null);
					SettingsManager.getTutorials().set("Tutorials." + tutorialName + "." + locationNumber, null);
					player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "The location with id: " + ChatColor.GRAY + locationNumber + ChatColor.AQUA + " has been deleted from tutorial " + ChatColor.GRAY + "\"" + tutorialName + "\"");
				} else {
					player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "The location id: " + ChatColor.GRAY + locationNumber + ChatColor.AQUA + " does not exist!");
				}
			} catch(Exception ex) {
				player.sendMessage(ChatColor.RED + "The location number given is not a vaild integer number!");
			}
		} else {
			player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "The tutorial \"" + ChatColor.GRAY + tutorialName + ChatColor.AQUA + "\" does not exist!");
		}
	}
}
