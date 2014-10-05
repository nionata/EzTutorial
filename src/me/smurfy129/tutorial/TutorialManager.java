package me.smurfy129.tutorial;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;

public class TutorialManager {

	private TutorialManager() { }

	private static TutorialManager instance = new TutorialManager();

	public static TutorialManager getInstance() {
		return instance;
	}

	private ArrayList<Tutorial> tutorials;

	public void setup() {
		if(tutorials != null) {  
			tutorials.clear();
		}

		for(String tutID : SettingsManager.getTutorials().<ConfigurationSection>get("Tutorials.").getKeys(false)) {
			tutorials.add(new Tutorial(tutID));
		}
	}

	public Tutorial getTutorial(String tutorialName) {
		for(Tutorial tut : tutorials) {
			if(tut.getName().equals(tutorialName)) {
				return tut;
			}
		}

		return null;
	}

	public ArrayList<Tutorial> getTutorials() {
		return tutorials;
	}

	public String getLocation(int id) {
		for(Tutorial tut : tutorials) {
			for(String loc : SettingsManager.getTutorials().<ConfigurationSection>get("Tutorials." + tut).getKeys(false)) {
				if(loc.equalsIgnoreCase(id + "")) {
					return loc;
				}
			}
		}

		return null;
	}

}
