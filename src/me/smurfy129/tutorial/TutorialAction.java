package me.smurfy129.tutorial;

import java.util.ArrayList;

import net.milkbowl.vault.economy.EconomyResponse;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class TutorialAction {

	Main plugin;

	public TutorialAction(Main plugin) {
		this.plugin = plugin;
	}

	public void startTutorial(final Player player, String tutName) {
		
		final String tutorialName = tutName;
		final Tutorial tut = TutorialManager.getInstance().getTutorial(tutorialName);
		final ArrayList<Location> locations = tut.getLocations();
		final ArrayList<String> messages = tut.getMessages();
		final ArrayList<String> locationNames = tut.getLocationNames();
		final ItemStack rewardItem = tut.getRewardItem();
		final double rewardMoney = tut.getRewardMoney();
		final String endMessage = tut.getEndMessage();
		final int delay = tut.getDelay();
		int length = 0;
		final Location playerLocation = player.getLocation();

		if(getValue(locations) == false && getValue(messages) == false) {
			if(player.hasPermission("tutorial.new")) {
				player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "This tutorial is not ready to go yet! You need to create atleast one location or message.");
			} else {
				player.sendMessage(ChatColor.GRAY + "[EzTutorial] " + ChatColor.AQUA + "This tutorial is not ready to use yet!");
			}
			return;
		}

		if(getValue(locations) == true) {
			if(locations.size() == 1) {
				player.sendMessage(ChatColor.GRAY + "- There will be 1 location we will visit during this tutorial");	
			} else {
				player.sendMessage(ChatColor.GRAY + "- There will be " + locations.size() + " locations we will visit during this tutorial");
			}
			length = locations.size();
		}

		if(getValue(messages) == true) {
			if(messages.size() == 1) {
				player.sendMessage(ChatColor.GRAY + "- There will be 1 message we will have during this tutorial");	
			} else {
				player.sendMessage(ChatColor.GRAY + "- There will be " + messages.size() + " messages we will have during this tutorial");	
			}
			length = messages.size();
		}

		if(tut.getCanMove() == false) {
			player.setWalkSpeed(0.0F);
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 10000, 128));
			player.sendMessage(ChatColor.GRAY + "- During the tutorial no walking is allowed");
		} else {
			player.sendMessage(ChatColor.GRAY + "- During the tutorial walking is allowed");
		}
		
		final int finalLength = length;

		new BukkitRunnable() {
			int counter = 0;
			@Override
			public void run() {
				if(counter < finalLength) {
					if(getValue(locations) == true) {
						player.teleport(locations.get(counter));
					}

					if(getValue(messages) == true) {
						player.sendMessage("-------------------(" + locationNames.get(counter) + ")-------------------");
						player.sendMessage(ChatColor.AQUA + messages.get(counter));
					}
					
					counter++;
				} else {
					cancel();

					if(tut.getCanMove() == false) {
						player.setWalkSpeed(0.2F);
						player.removePotionEffect(PotionEffectType.JUMP);
					}
					
					if(getValue(endMessage) == true) {
						player.sendMessage(ChatColor.GRAY + endMessage);	
					}
					
					if(Main.plugin.setupEconomy()) {
						if(getValue(rewardMoney) == true) {
							Player playerName = player;
							EconomyResponse r = Main.econ.depositPlayer(playerName, rewardMoney);
							
							if(r.transactionSuccess()) {
								player.sendMessage(ChatColor.GRAY + "You are rewarded with " + ChatColor.AQUA + rewardMoney + ChatColor.GRAY + " for doing this tutorial");
							}
						}
					}
					
					if(getValue(rewardItem) == true) {
						player.getInventory().addItem(rewardItem);
						player.sendMessage(ChatColor.GRAY + "You are rewarded " + ChatColor.AQUA + rewardItem.getAmount() + " " + rewardItem.getType() + ChatColor.GRAY + " for doing this tutorial!"); 
					}

				}
				
				player.teleport(playerLocation);
			}
		
	}.runTaskTimer(Main.plugin, 0, delay);

}

private boolean getValue(Object o) {
	if(o != null) {
		return true;
	} else {
		return false;
	}
}

}