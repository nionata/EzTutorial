package me.smurfy129.tutorial;


import java.util.*;
import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

// Referenced classes of package me.Smurfy129.Main:
//            Main

public class SubCommands
{

    public SubCommands(Main plugin)
    {
        playerSign = new HashMap();
        this.plugin = plugin;
    }

    public void newCommand(Player player, String args[])
    {
        String tutorialName = args[1];
        if(Main.plugin.getConfig().contains((new StringBuilder("Tutorials.")).append(tutorialName).toString()))
        {
            player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("This tutorial already exists!").toString());
        } else
        {
            player.getInventory().addItem(new ItemStack[] {
                new ItemStack(Material.SIGN, 1)
            });
            player.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("[EzTutorial] ").append(ChatColor.AQUA).append("Place this sign to save your tutorial!").toString());
            playerSign.put(player.getDisplayName(), tutorialName);
        }
    }

    public void setLocationCommand(Player player, String args[])
    {
        String tutorialName = args[1];
        try
        {
            int locationNumber = Integer.parseInt(args[2]);
            Location loc = player.getLocation();
            if(Main.plugin.getConfig().contains((new StringBuilder("Tutorials.")).append(tutorialName).toString()))
            {
                if(Main.plugin.getConfig().contains((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).toString()))
                {
                    player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("The tutorial \"").append(tutorialName).append("\" already has a location ").append(locationNumber).toString());
                } else
                {
                    double xLocation = loc.getX();
                    double yLocation = loc.getY();
                    double zLocation = loc.getZ();
                    float pitch = loc.getPitch();
                    float yaw = loc.getYaw();
                    Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).append(".Message").append(".Enabled").toString(), Boolean.valueOf(true));
                    Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).append(".Message").append(".Message").toString(), "Default Message");
                    Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).append(".Message").append(".Location Name").toString(), (new StringBuilder()).append(locationNumber).toString());
                    Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).append(".X").toString(), Double.valueOf(xLocation));
                    Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).append(".Y").toString(), Double.valueOf(yLocation));
                    Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).append(".Z").toString(), Double.valueOf(zLocation));
                    Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).append(".Pitch").toString(), Float.valueOf(pitch));
                    Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).append(".Yaw").toString(), Float.valueOf(yaw));
                    Main.plugin.saveConfig();
                    player.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("[EzTutorial] ").append(ChatColor.AQUA).append("Location ").append(ChatColor.GRAY).append(locationNumber).append(ChatColor.AQUA).append(" has been saved in tutorial ").append(ChatColor.GRAY).append("\"").append(tutorialName).append("\"").toString());
                }
            } else
            {
                player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Tutorial \"").append(tutorialName).append("\" does not exist, you have to create a tutorial before you set locations").toString());
            }
        }
        catch(Exception ex)
        {
            player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("The location number given is not a vaild integer number").toString());
        }
    }

    public void delLocationCommand(Player player, String args[])
    {
        String tutorialName = args[1];
        try
        {
            int locationNumber = Integer.parseInt(args[2]);
            if(Main.plugin.getConfig().contains((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).toString()))
            {
                Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(locationNumber).toString(), null);
                Main.plugin.saveConfig();
                player.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("[EzTutorial] ").append(ChatColor.AQUA).append("Location ").append(ChatColor.GRAY).append(locationNumber).append(ChatColor.AQUA).append(" has been deleted from tutorial ").append(ChatColor.GRAY).append("\"").append(tutorialName).append("\"").toString());
            } else
            {
                player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("[EzTutorial] Location ").append(locationNumber).append(" can not be deleted because it doesn't exist").toString());
            }
        }
        catch(Exception ex)
        {
            player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("The location number given is not a vaild integer number").toString());
        }
    }

    public void delTutorial(Player player, String args[])
    {
        String tutorialName = args[1];
        if(Main.plugin.getConfig().contains((new StringBuilder("Tutorials.")).append(tutorialName).toString()))
        {
            Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).toString(), null);
            Main.plugin.saveConfig();
            player.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("[EzTutorial] ").append(ChatColor.AQUA).append("The tutorial ").append(ChatColor.GRAY).append("\"").append(tutorialName).append("\"").append(ChatColor.AQUA).append(" has been deleted").toString());
        } else
        {
            player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("Tutorial \"").append(tutorialName).append("\" can not be deleted because it doesn't exist").toString());
        }
    }

    public void listCommand(Player player, String args[])
    {
        try
        {
            Set tutorials = Main.plugin.getConfig().getConfigurationSection("Tutorials.").getKeys(false);
            if(tutorials.size() == 0)
            {
                if(player.hasPermission("tutorial.new"))
                    player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("No tutorials have been created so far, make a new one with /tutorial new <tutorial name>").toString());
                else
                    player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("There are no tutorials available").toString());
            } else
            {
                player.sendMessage((new StringBuilder()).append(ChatColor.AQUA).append("Tutorials:").toString());
                player.sendMessage("-----------------------------------------------------");
                String s;
                int locsNum;
                for(Iterator iterator = tutorials.iterator(); iterator.hasNext(); player.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append(s).append("            Locations: ").append(locsNum).toString()))
                {
                    s = (String)iterator.next();
                    Set locs = Main.plugin.getConfig().getConfigurationSection((new StringBuilder("Tutorials.")).append(s).toString()).getKeys(false);
                    locsNum = locs.size() - 4;
                }

            }
        }
        catch(Exception ex)
        {
            if(player.hasPermission("tutorial.new"))
                player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("No tutorials have been created so far, make a new one with /tutorial new <tutorial name>").toString());
            else
                player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("There are no tutorials available").toString());
        }
    }

    public void startTutorial(Player player, String tutName)
    {
        String tutorialName = tutName;
        HashMap tutorialLocations = new HashMap();
        Set data = Main.plugin.getConfig().getConfigurationSection((new StringBuilder("Tutorials.")).append(tutorialName).toString()).getKeys(false);
        int dataNum = data.size() - 4;
        Location playerLocation = player.getLocation();
        Long delay = Long.valueOf(Main.plugin.getConfig().getLong((new StringBuilder("Tutorials.")).append(tutorialName).append(".Delay In Seconds").toString()) * 20L);
        if(dataNum == 1)
            player.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("- There will be 1 location we will visit during this tutorial").toString());
        else
            player.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("- There will be ").append(dataNum).append(" locations we will visit during this tutorial").toString());
        for(int i = 1; i <= dataNum; i++)
        {
            double x = Main.plugin.getConfig().getDouble((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(i).append(".X").toString());
            double y = Main.plugin.getConfig().getDouble((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(i).append(".Y").toString());
            double z = Main.plugin.getConfig().getDouble((new StringBuilder("Tutorials.")).append(tutorialName).append(".").append(i).append(".Z").toString());
            Location loc = new Location(player.getWorld(), x, y, z);
            tutorialLocations.put((new StringBuilder()).append(i).toString(), loc);
        }

        if(!Main.plugin.getConfig().getBoolean((new StringBuilder("Tutorials.")).append(tutorialName).append(".Move During Tutorial").toString()))
        {
            player.setWalkSpeed(0.0F);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000, 128));
            player.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("- During the tutorial no walking is allowed").toString());
        }
        (new  Object(dataNum, tutorialName, tutorialLocations, player, playerLocation)     /* anonymous class not found */
    class _anm1 {}

).runTaskTimer(Main.plugin, 0L, delay.longValue());
    }

    HashMap playerSign;
    Main plugin;
}