package me.smurfy129.tutorial;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class Tutorial {

	private String name;	
	private Boolean msgs;
	private Boolean locs;
	private Boolean move;
	private Boolean canRewardItem;
	private ItemStack rewardItem;
	private Boolean canRewardMoney;
	private double rewardMoney;
	private Boolean canEndMessage;
	private String endMessage;
	private int delay;

	private ArrayList<Location> locations;
	private ArrayList<String> messages;
	private ArrayList<String> locationNames;

	protected Tutorial(String name) {
		this.name = name;
		this.locations = new ArrayList<Location>();
		this.messages = new ArrayList<String>();
		this.locationNames = new ArrayList<String>();

		msgs = getBooleanValue("Messages");
		locs = getBooleanValue("Locations");
		move = getBooleanValue("Move During Tutorial");
		canRewardItem = getBooleanValue("Reward.Item.Enabled");
		canRewardMoney = getBooleanValue("Reward.Money.Enabled");
		canEndMessage = getBooleanValue("End Message.Enabled");

		for(String tutId : SettingsManager.getTutorials().<ConfigurationSection>get("Tutorials." + name).getKeys(false)) {
			//Path for getting the locations
			String pathL = ("Tutorials." + name + tutId);

			double x = SettingsManager.getTutorials().getDouble(pathL + "X");
			double y = SettingsManager.getTutorials().getDouble(pathL + "Y");
			double z = SettingsManager.getTutorials().getDouble(pathL + "Z");
			double pitch = SettingsManager.getTutorials().getDouble(pathL + "Pitch");
			double yaw = SettingsManager.getTutorials().getDouble(pathL + "Yaw");	
			Location loc = new Location(Main.plugin.getServer().getWorld("world"), x, y, z);
			loc.setPitch((float) pitch);
			loc.setYaw((float) yaw);

			locations.add(loc);

		}

		for(String tutId : SettingsManager.getTutorials().<ConfigurationSection>get("Tutorials." + name).getKeys(false)) {
			messages.add("Tutorials." + name + "." + tutId + "Message");
		}
		
		for(String tutId : SettingsManager.getTutorials().<ConfigurationSection>get("Tutorials." + name).getKeys(false)) {
			locationNames.add("Tutorials." + name + "." + tutId + "Location Name");

		}
		
		String item = SettingsManager.getConfig().getString("Tutorials." + name + ".Reward.Item.Item Name");
		int amount = SettingsManager.getConfig().getInt("Tutorials." + name + ".Reward.Item.Amount");
		rewardItem = new ItemStack(Material.valueOf(item), amount);
		
		rewardMoney = SettingsManager.getConfig().getDouble("Tutorials." + name + ".Reward.Money.Amount");
		
		endMessage = SettingsManager.getConfig().getString("Tutorials." + name + ".End Message.Message");
		
		delay = SettingsManager.getConfig().getInt("Tutorials." + name + ".Delay In Seconds");
		
	}

	private boolean getBooleanValue(String path) {
		boolean b;
		if(SettingsManager.getConfig().getBoolean("Tutorials." + name + "." + path) == true) {
			b = true;
		} else {
			b = false;
		}
		return b;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Location> getLocations() {
		if(locs == true) {
			return locations;
		} else {
			return null;
		}
	}

	public ArrayList<String> getMessages() {
		if(msgs == true) {
			return messages;
		} else {
			return null;
		}
	}
	
	public ArrayList<String> getLocationNames() {
		if(msgs == true) {
			return locationNames;
		} else {
			return null;
		}
	}

	public boolean getCanMove() {
		return move;
	}

	public String getEndMessage() {
		if(canEndMessage == true) {
			return endMessage;
		} else {
			return null;
		}
	}

	public ItemStack getRewardItem() {
		if(canRewardItem == true) {
			return rewardItem;
		} else {
			return null;
		}
	}

	public double getRewardMoney() {
		if(canRewardMoney == true) {
			return rewardMoney;
		} else {
			return 0;
		}
	}
	
	public int getDelay() {
		return delay;
	}
}
