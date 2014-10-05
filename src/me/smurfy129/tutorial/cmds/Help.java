package me.smurfy129.tutorial.cmds;

import org.bukkit.entity.Player;
import me.smurfy129.tutorial.CommandInfo;
import me.smurfy129.tutorial.Main;
import me.smurfy129.tutorial.TutorialCommand;

@CommandInfo(description = "Displays the help page", permission = "help", usage = "", aliases = {"help"}, argLengths = {0, 1})
public class Help extends TutorialCommand {

	@Override
	public void onCommand(Player player, String[] args) {
		
		Main.plugin.listCommands(player);

	}
}
