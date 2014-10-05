package me.smurfy129.tutorial;

import java.util.logging.Logger;

import me.smurfy129.tutorial.cmds.NewTutorial;
import net.milkbowl.vault.economy.Economy;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	/*
	 * To-Do:
	 * 
	 * look at new tutorial fix problem with placing sign 
	 * 
	 */

	//Initializing - variables and other things being set up
	public static Main plugin;
	TutorialAction tut;
	Logger myLog = Bukkit.getLogger();
	NewTutorial CreTut;
	CommandManager CmdManage;
	
	public static Economy econ = null;
	PluginDescriptionFile pdf = this.getDescription();
	int permsCounter = 0;

	//Logging to console
	public void onEnable() {
		
		if (!setupEconomy()) {
			myLog.info("[EzTutorial] No economy plugin found, money rewards will not be given");
		}
		
		SettingsManager.getConfig();
		SettingsManager.getTutorials();
		
		plugin = this;
		tut = new TutorialAction(this);
		CreTut = new NewTutorial();
		
		getCommand("tutorial").setExecutor(CmdManage = new CommandManager());
		getCommand("tut").setExecutor(CmdManage = new CommandManager());
		getCommand("t").setExecutor(CmdManage = new CommandManager());
		
		getServer().getPluginManager().registerEvents(new PluginListener(), this);
		
		myLog.info("[EzTutorial] has been enabled!");
	}

	public void onDisable() {
		myLog.info("[EzTutorial] has been disabled!");
	}

	//Vault - Hooking up vault and the economy plugin
	public boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		econ = rsp.getProvider();
		return econ != null;
	}
	
	public void listCommands(Player player) {
		
		ChatColor g = ChatColor.GRAY;
		ChatColor a = ChatColor.AQUA;
		
		player.sendMessage(g + "---------- " + a + pdf.getName() + g + " ----------");
		player.sendMessage(g + "Author: " + a + "Smurfy129");
		player.sendMessage(g + "Aliases: " + a + "/tutorial /tut /t");
		player.sendMessage(g + "Sub-Commands: " + a + "(Use an aliase above infront of the sub-command)");
		for(TutorialCommand tcmd : CmdManage.cmds) {
			CommandInfo info = tcmd.getClass().getAnnotation(CommandInfo.class);
			player.sendMessage(g + "- " + a + "(" + StringUtils.join(info.aliases(), ", ").trim() + ") " + info.usage() + g + " - " + info.description());
		}
	}
	
	public void startTutorial(Player player, String tutorialName) {
		tut.startTutorial(player, tutorialName);
	}
}