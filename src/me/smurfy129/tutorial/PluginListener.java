package me.smurfy129.tutorial;

import java.util.HashMap;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

// Referenced classes of package me.Smurfy129.Main:
//            Main, SubCommands

public class PluginListener
    implements Listener
{

    public PluginListener()
    {
    }

    public void onSignChange(SignChangeEvent e)
    {
        Player player = e.getPlayer();
        if(Main.plugin.subCmds.playerSign.containsKey(player.getDisplayName()))
        {
            String tutorialName = (String)Main.plugin.subCmds.playerSign.get(player.getDisplayName());
            e.setLine(0, "[EzTutorial]");
            e.setLine(1, (new StringBuilder()).append(ChatColor.WHITE).append("Start").toString());
            e.setLine(2, (new StringBuilder()).append(ChatColor.AQUA).append(tutorialName).toString());
            Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".Reward").append(".Item").append(".Enabled").toString(), Boolean.valueOf(false));
            Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".Reward").append(".Item").append(".Item Name").toString(), "DIAMOND");
            Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".Reward").append(".Item").append(".Amount").toString(), Integer.valueOf(1));
            Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".Reward").append(".Money").append(".Enabled").toString(), Boolean.valueOf(false));
            Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".Reward").append(".Money").append(".Amount").toString(), Integer.valueOf(1000));
            Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".Delay In Seconds").toString(), Integer.valueOf(5));
            Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".Move During Tutorial").toString(), Boolean.valueOf(false));
            Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".End Message").append(".Enabled").toString(), Boolean.valueOf(false));
            Main.plugin.getConfig().set((new StringBuilder("Tutorials.")).append(tutorialName).append(".End Message").append(".Message").toString(), "Thank you for learning more with EzTutorials");
            Main.plugin.saveConfig();
            Main.plugin.subCmds.playerSign.remove(player.getDisplayName());
            player.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("[EzTutorial] ").append(ChatColor.AQUA).append("The tutorial \"").append(tutorialName).append("\" has been saved!").toString());
            if(tutorialName.length() > 15)
                player.sendMessage((new StringBuilder()).append(ChatColor.GRAY).append("[EzTutorial] ").append(ChatColor.AQUA).append("The tutorial name is too long to work with start signs. However npcs and /tutorial startplayer will still work!").toString());
        }
    }

    public void onPlayerInteract(PlayerInteractEvent e)
    {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && (e.getClickedBlock().getState() instanceof Sign))
        {
            Player player = e.getPlayer();
            Sign s = (Sign)e.getClickedBlock().getState();
            String firstLine = s.getLine(0);
            String secondLine = s.getLine(1);
            String tutorialName = ChatColor.stripColor(s.getLine(2));
            if(ChatColor.stripColor(firstLine).equalsIgnoreCase("[EzTutorial]") && ChatColor.stripColor(secondLine).equalsIgnoreCase("Start") && Main.plugin.getConfig().contains((new StringBuilder("Tutorials.")).append(tutorialName).toString()))
                if(player.hasPermission("tutorial.start"))
                    Main.plugin.subCmds.startTutorial(player, tutorialName);
                else
                    player.sendMessage((new StringBuilder()).append(ChatColor.RED).append("You don't have permission to start this tutorial!").toString());
        }
    }

    public void onEntityDamage(EntityDamageByEntityEvent e)
    {
        Entity attacker = e.getDamager();
        if(attacker instanceof Player)
        {
            Player p = (Player)e.getDamager();
            if(e.getEntity() instanceof LivingEntity)
            {
                LivingEntity ent = (LivingEntity)e.getEntity();
                String tutName = ent.getCustomName();
                if(Main.plugin.getConfig().contains((new StringBuilder("Tutorials.")).append(tutName).toString()))
                {
                    e.setCancelled(true);
                    Main.plugin.subCmds.startTutorial(p, tutName);
                }
            }
        }
    }

    public void onPlayerJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        player.setWalkSpeed(0.2F);
        player.removePotionEffect(PotionEffectType.JUMP);
        if(!player.hasPlayedBefore() && Main.plugin.getConfig().getBoolean("Tutorials.New Players Default Tutorial.Enabled"))
        {
            String tutorialName = Main.plugin.getConfig().getString("Tutorials.New Players Default Tutorial.Tutorial Name");
            if(Main.plugin.getConfig().getBoolean("Tutorials.New Players Default Tutorial.Join Message.Enabled"))
            {
                String joinMessage = Main.plugin.getConfig().getString("Tutorials.New Players Default Tutorial.Join Message.Message");
                player.sendMessage((new StringBuilder()).append(ChatColor.AQUA).append(joinMessage).toString());
            }
            Main.plugin.subCmds.startTutorial(player, tutorialName);
        }
    }
}