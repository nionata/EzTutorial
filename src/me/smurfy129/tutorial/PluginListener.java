package me.smurfy129.tutorial;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffectType;

public class PluginListener implements Listener{
	
	SettingsManager config = SettingsManager.getConfig();

	@EventHandler
	public void onSignChange(SignChangeEvent e) {
		Player player = e.getPlayer();
		if(Main.plugin.CreTut.playerSign.containsKey(player.getDisplayName())) {
			String tutorialName = Main.plugin.CreTut.playerSign.get(player.getDisplayName());
			e.setLine(0, "[EzTutorial]");
			e.setLine(1, ChatColor.WHITE + "Start");
			e.setLine(2, ChatColor.AQUA + tutorialName);	
			config.set("Tutorials." + tutorialName + ".Messages", false);
			config.set("Tutorials." + tutorialName + ".Locations", false);
			config.set("Tutorials." + tutorialName + ".Reward" + ".Item" +".Enabled", false);
			config.set("Tutorials." + tutorialName + ".Reward" + ".Item" +".Enabled", false);
			config.set("Tutorials." + tutorialName + ".Reward" + ".Item" + ".Item Name", "DIAMOND");
			config.set("Tutorials." + tutorialName + ".Reward" + ".Item" + ".Amount", 1);
			config.set("Tutorials." + tutorialName + ".Reward" + ".Money" +".Enabled", false);
			config.set("Tutorials." + tutorialName + ".Reward" + ".Money" + ".Amount", 1000);
			config.set("Tutorials." + tutorialName + ".Delay In Seconds", 5);
			config.set("Tutorials." + tutorialName + ".Move During Tutorial", false);
			config.set("Tutorials." + tutorialName + ".End Message" + ".Enabled", false);
			config.set("Tutorials." + tutorialName + ".End Message" + ".Message", "Thank you for learning more with EzTutorials");
			Main.plugin.CreTut.playerSign.remove(player.getDisplayName());
			player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "The tutorial \"" + tutorialName + "\" has been saved!");
			if(tutorialName.length() > 15) {
				player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "The tutorial name is too long to work with start signs. However npcs and /tutorial startplayer will still work!" );
			}
		} else {
			player.sendMessage("ur not here");
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(e.getClickedBlock().getState() instanceof Sign) {
				Player player = e.getPlayer();
				Sign s = (Sign) e.getClickedBlock().getState();
				String firstLine = s.getLine(0);
				String secondLine = s.getLine(1);
				String tutorialName = ChatColor.stripColor(s.getLine(2));
				if (ChatColor.stripColor(firstLine).equalsIgnoreCase("[EzTutorial]")) {
					if(ChatColor.stripColor(secondLine).equalsIgnoreCase("Start")) {
						if(Main.plugin.getConfig().contains("Tutorials." + tutorialName)) {
							if(player.hasPermission("tutorial.start.sign")) {
								Main.plugin.tut.startTutorial(player, tutorialName);
							} else {
								player.sendMessage(ChatColor.RED + "You don't have permission to start this tutorial!");
							}
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		Entity attacker = e.getDamager();
		if(attacker instanceof Player) {
			Player p = (Player) e.getDamager();
			if(e.getEntity() instanceof LivingEntity) {
				LivingEntity ent = (LivingEntity) e.getEntity();
				String tutName = ent.getCustomName();
				if(Main.plugin.getConfig().contains("Tutorials." + tutName)) {
					if(p.hasPermission("tutorial.start.npc")) {
						e.setCancelled(true);
						Main.plugin.tut.startTutorial(p, tutName);
					} else {
						e.setCancelled(true);
						p.sendMessage(ChatColor.RED + "You don't have permission to start this tutorial!");
					}
				}
			}
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		player.setWalkSpeed(0.2F);
		player.removePotionEffect(PotionEffectType.JUMP);

		if(player.hasPlayedBefore() == false) {
			if(Main.plugin.getConfig().getBoolean("Tutorials." + "New Players Default Tutorial" + ".Enabled") == true) {
				String tutorialName = Main.plugin.getConfig().getString("Tutorials." + "New Players Default Tutorial" + ".Tutorial Name");
				if(Main.plugin.getConfig().getBoolean("Tutorials." + "New Players Default Tutorial" + ".Join Message" + ".Enabled") == true) {
					String joinMessage = Main.plugin.getConfig().getString("Tutorials." + "New Players Default Tutorial" + ".Join Message" + ".Message");
					player.sendMessage(ChatColor.AQUA + joinMessage);
				}
				Main.plugin.tut.startTutorial(player, tutorialName);
			}
		}
	}

}
