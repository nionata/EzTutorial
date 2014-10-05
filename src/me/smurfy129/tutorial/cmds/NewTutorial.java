package me.smurfy129.tutorial.cmds;

import java.util.HashMap;
import me.smurfy129.tutorial.CommandInfo;
import me.smurfy129.tutorial.TutorialCommand;
import me.smurfy129.tutorial.TutorialManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandInfo(description = "Creates a new tutorial", permission = "new", usage = "<tutorial name>", aliases = {"new"}, argLengths = {1, 2})
public class NewTutorial extends TutorialCommand {
	
	public HashMap<String, String> playerSign = new HashMap<String, String>();
	
	public void onCommand(Player player, String[] args) {
		
		String tutorialName = args[0];
		
		if(TutorialManager.getInstance().getTutorial(tutorialName) != null) {
			player.sendMessage(ChatColor.RED + "This tutorial already exists!");
		} else {
			player.getInventory().addItem(new ItemStack(Material.SIGN , 1));
			player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "Place this sign to save your tutorial!");
			playerSign.put(player.getDisplayName(), tutorialName);
		}
				
	}
}
