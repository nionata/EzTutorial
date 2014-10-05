package me.smurfy129.tutorial.cmds;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import me.smurfy129.tutorial.CommandInfo;
import me.smurfy129.tutorial.SettingsManager;
import me.smurfy129.tutorial.TutorialCommand;
import me.smurfy129.tutorial.TutorialManager;

@CommandInfo(description = "Sets a new location", permission = "set.location", usage = "<tutorial name>", aliases = {"setlocation", "sloc"}, argLengths = {1, 2})
public class SetLocation extends TutorialCommand {

	@Override
	public void onCommand(Player player, String[] args) {

		String tutorialName = args[0];
		int locationNumber;
		SettingsManager config = SettingsManager.getConfig();
		SettingsManager tutorials = SettingsManager.getTutorials();

		Location loc = player.getLocation();
		double xLocation = loc.getX();
		double yLocation = loc.getY();
		double zLocation = loc.getZ();
		float pitch = loc.getPitch();
		float yaw = loc.getYaw();

		if(TutorialManager.getInstance().getTutorial(tutorialName) != null) {

			locationNumber = TutorialManager.getInstance().getTutorial(tutorialName).getLocations().size() + 1;

			config.set("Tutorials." + tutorialName + "." + locationNumber + ".Message", null);
			config.set("Tutorials." + tutorialName + "." + locationNumber + ".Location Name", "" + locationNumber);
			tutorials.set("Tutorials." + tutorialName + "." + locationNumber + ".X", xLocation);
			tutorials.set("Tutorials." + tutorialName + "." + locationNumber + ".Y", yLocation);
			tutorials.set("Tutorials." + tutorialName + "." + locationNumber + ".Z", zLocation);
			tutorials.set("Tutorials." + tutorialName + "." + locationNumber + ".Pitch", pitch);
			tutorials.set("Tutorials." + tutorialName + "." + locationNumber + ".Yaw", yaw);
			player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "The location has been saved with id: " + ChatColor.GRAY + locationNumber + ChatColor.AQUA + " in tutorial " + ChatColor.GRAY + "\"" + tutorialName + "\"");
		} else {
			player.sendMessage(ChatColor.RED + "Tutorial \"" + tutorialName + "\" does not exist, you have to create a tutorial before you can set a location.");
		}

	}
}
