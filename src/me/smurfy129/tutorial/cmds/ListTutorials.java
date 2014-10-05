package me.smurfy129.tutorial.cmds;

import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import me.smurfy129.tutorial.CommandInfo;
import me.smurfy129.tutorial.Tutorial;
import me.smurfy129.tutorial.TutorialCommand;
import me.smurfy129.tutorial.TutorialManager;

@CommandInfo(description = "Displays a list of all the tutorials", permission = "list", usage = "", aliases = {"list"}, argLengths = {0, 1})
public class ListTutorials extends TutorialCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		
		try {
			ArrayList<Tutorial> tutorials = TutorialManager.getInstance().getTutorials();
			
			if(tutorials.size() == 0) {
				
				if(player.hasPermission("tutorial.new")) {
					player.sendMessage(ChatColor.RED + "No tutorials have been created so far, make a new one with /tutorial new <tutorial name>");
				} else {
					player.sendMessage(ChatColor.RED + "There are no tutorials available");
				}
				
			} else {
				player.sendMessage(ChatColor.AQUA + "Tutorials:");
				player.sendMessage("-----------------------------------------------------");
				for(Tutorial tut : tutorials) {
					String s = tut.getName();
					int locsAmount = TutorialManager.getInstance().getTutorial(s).getLocations().size();
					player.sendMessage(ChatColor.GRAY + s + "            Locations: " + locsAmount);
				}
			}

		} catch(Exception ex) {
			if(player.hasPermission("tutorial.new")) {
				player.sendMessage(ChatColor.RED + "No tutorials have been created so far, make a new one with /tutorial new <tutorial name>");
			} else {
				player.sendMessage(ChatColor.RED + "There are no tutorials available");
			}
		}	
	}

}
