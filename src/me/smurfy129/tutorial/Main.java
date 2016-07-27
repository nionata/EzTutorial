package me.smurfy129.tutorial;

import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.Player;
import org.bukkit.plugin.*;
import org.bukkit.plugin.java.JavaPlugin;

// Referenced classes of package me.Smurfy129.main:
//            SubCommands, PluginListener

public class Main extends JavaPlugin
{

    public Main()
    {
        myLog = Bukkit.getLogger();
    }

    public void onEnable()
    {
        if(!setupEconomy())
            myLog.info("[EzTutorial] No economy plugin found, money rewards will not be given");
        plugin = this;
        subCmds = new SubCommands(this);
        getServer().getPluginManager().registerEvents(new PluginListener(), this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        myLog.info("[EzTutorial] has been enabled!");
    }

    public void onDisable()
    {
        myLog.info("[EzTutorial] has been disabled!");
    }

    public boolean setupEconomy()
    {
        if(getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider rsp = getServer().getServicesManager().getRegistration(net/milkbowl/vault/economy/Economy);
        if(rsp == null)
            return false;
        econ = (Economy)rsp.getProvider();
        return econ != null;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
    {
        if(sender instanceof Player)
        {
            Player player = (Player)sender;
            if(commandLabel.equalsIgnoreCase("tutorial") || commandLabel.equalsIgnoreCase("tut") || commandLabel.equalsIgnoreCase("t"))
                if(player.hasPermission("tutorial"))
                {
                    if(args.length == 0)
                        sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Missing arguments, try /tutorial help").toString());
                    if(args.length == 1)
                        if(args[0].equalsIgnoreCase("help"))
                        {
                            if(player.hasPermission("tutorial.help"))
                            {
                                sender.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("**************************************************************").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("*                                                                           *").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("*     Welcome to the help page for the EzTutorial plugin!      *").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("*                                                                           *").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("**************************************************************").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.AQUA).append("/tutorial help").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.AQUA).append("/tutorial list").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.AQUA).append("/tutorial new <sign/npc> <tutorial name>").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.AQUA).append("/tutorial deltutorial <tutorial name> ").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.AQUA).append("/tutorial dellocation <tutorial name> <location number>").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.AQUA).append("/tutorial setlocation <tutorial name> <location number>").toString());
                                sender.sendMessage((new StringBuilder()).append(ChatColor.AQUA).append("/tutorial startplayer <tutorial name> <target player>").toString());
                            } else
                            {
                                player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("You don't have permission to use this command!").toString());
                            }
                        } else
                        if(args[0].equalsIgnoreCase("list"))
                        {
                            if(player.hasPermission("tutorial.list"))
                                subCmds.listCommand(player, args);
                            else
                                player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("You don't have permission to use this command!").toString());
                        } else
                        {
                            sender.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Wrong arguments, try /tutorial help").toString());
                        }
                    if(args.length == 2)
                        if(args[0].equalsIgnoreCase("deltutorial"))
                        {
                            if(player.hasPermission("tutorial.deltutorial"))
                                subCmds.delTutorial(player, args);
                            else
                                player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("You don't have permission to use this command!").toString());
                        } else
                        if(args[0].equalsIgnoreCase("new"))
                        {
                            if(player.hasPermission("tutorial.new"))
                                subCmds.newCommand(player, args);
                        } else
                        {
                            player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Wrong arguments, try /tutorial help").toString());
                        }
                    if(args.length == 3)
                        if(args[0].equalsIgnoreCase("dellocation"))
                        {
                            if(player.hasPermission("tutorial.dellocation"))
                                subCmds.delLocationCommand(player, args);
                            else
                                player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("You don't have permission to use this command!").toString());
                        } else
                        if(args[0].equalsIgnoreCase("setlocation"))
                        {
                            if(player.hasPermission("tutorial.setlocation"))
                                subCmds.setLocationCommand(player, args);
                            else
                                player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("You don't have permission to use this command!").toString());
                        } else
                        if(args[0].equalsIgnoreCase("startplayer") && player.hasPermission("tutorial.startplayer"))
                        {
                            Player targetPlayer = Bukkit.getPlayer(args[2]);
                            String tutorialName = args[1];
                            if(targetPlayer != player)
                            {
                                if(targetPlayer != null)
                                {
                                    if(plugin.getConfig().contains((new StringBuilder("Tutorials.")).append(tutorialName).toString()))
                                    {
                                        targetPlayer.sendMessage((new StringBuilder()).append(ChatColor.AQUA).append("Attention ").append(targetPlayer.getDisplayName()).append(" a staff member is putting you through the tutorial \"").append(tutorialName).append("\"").toString());
                                        subCmds.startTutorial(targetPlayer, tutorialName);
                                    } else
                                    {
                                        player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("The tutorial \"").append(tutorialName).append("\" does not exist").toString());
                                    }
                                } else
                                {
                                    player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("The target player is not online!").toString());
                                }
                            } else
                            {
                                player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("You can't start yourself in a tutorial!").toString());
                            }
                        }
                } else
                {
                    player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("You don't have permission to use this command!").toString());
                }
        } else
        {
            sender.sendMessage("[EzTutorial] Sorry this command can only be used in-game.");
        }
        return false;
    }

    public static Main plugin;
    SubCommands subCmds;
    Logger myLog;
    public static Economy econ = null;

}