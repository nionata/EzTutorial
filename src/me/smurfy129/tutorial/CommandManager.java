package me.smurfy129.tutorial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import me.smurfy129.tutorial.cmds.DeleteLocation;
import me.smurfy129.tutorial.cmds.DeleteTutorial;
import me.smurfy129.tutorial.cmds.Help;
import me.smurfy129.tutorial.cmds.ListTutorials;
import me.smurfy129.tutorial.cmds.NewTutorial;
import me.smurfy129.tutorial.cmds.Reload;
import me.smurfy129.tutorial.cmds.SetLocation;
import me.smurfy129.tutorial.cmds.Start;
import me.smurfy129.tutorial.cmds.StartPlayer;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

public class CommandManager implements CommandExecutor {

	public ArrayList<TutorialCommand> cmds;

	protected CommandManager() {
		cmds = new ArrayList<TutorialCommand>();

		cmds.add(new Help());
		cmds.add(new ListTutorials());
		cmds.add(new Reload());
		cmds.add(new NewTutorial());
		cmds.add(new SetLocation());
		cmds.add(new Start());
		cmds.add(new StartPlayer());
		cmds.add(new DeleteTutorial());
		cmds.add(new DeleteLocation());
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "This command can only be used in game!");
			return false;
		}

		Player player = (Player) sender;

		if((commandLabel.equalsIgnoreCase("tutorial")) || (commandLabel.equalsIgnoreCase("tut")) || (commandLabel.equalsIgnoreCase("t"))) {

			int permsCounter = 0;

			//Iterating through the permissions 
			for(Permission p : Main.plugin.pdf.getPermissions()) {
				if(player.hasPermission(p)) {
					permsCounter++;
				}
			}	

			//Testing if the player has any of the permissions
			if(permsCounter == 0) {
				player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
				return false;
			}

			if(args.length == 0) {
				Main.plugin.listCommands(player);

				return false;
			}

			TutorialCommand wanted = null;

			for(TutorialCommand tcmd : cmds) {
				CommandInfo info = tcmd.getClass().getAnnotation(CommandInfo.class);
				String perm = info.permission();
				int[] argLengths = info.argLengths();
				String usage = info.usage();
				for(String alias : info.aliases()) {
					if(alias.equalsIgnoreCase(args[0])) {
						if(!(player.hasPermission("tutorial." + perm))) {
							player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
							return false;
						}
						
						if(argLengths[0] >= args.length) {
							player.sendMessage(ChatColor.RED + "Missing arguments, try /tutorial (" + StringUtils.join(info.aliases(), ", ").trim() + ") " + usage);
							return false;
						}
						
						if(argLengths[1] < args.length) {
							player.sendMessage(ChatColor.RED + "Too many arguments, try /tutorial (" + StringUtils.join(info.aliases(), ", ").trim() + ") " + usage);
							return false;
						}

						wanted = tcmd;
						break;
					}
				}

			}

			if(wanted == null) {
				player.sendMessage(ChatColor.RED + "Could not find command, try /tutorial help");
				return false;
			} else {
				
				List<String> newArgs = new LinkedList<String>(Arrays.asList(args));
				newArgs.remove(0);
				args = newArgs.toArray(new String[newArgs.size()]);
				
				wanted.onCommand(player, args);
			}
		}

		return false;
	}
}