package it.unibo.osu.model;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import it.unibo.osu.util.*;
//da implementare usando un reader da file + parser 
public class BeatMap {

	//campi obbligatori quindi per ogni beatmap file
	private List<Hitpoint> hitpoints;
	private double hpDrainRate;
	private double circleSize;
	private double overallDifficulty;
	private double approachRate;
	private String background;   
	private String songName;
	private List<List<Double>> breakTimes;
	private double startingTime;
	
	public BeatMap(String fileName) {

		try(BeatmapReader reader = new BeatmapReader(this.getClass().getResourceAsStream(fileName))){
			Map<String, String> map = reader.getOptionMap(BeatmapOptions.DIFFICULTY);
			this.circleSize = Double.parseDouble(map.get("CircleSize"));
			this.overallDifficulty = Double.parseDouble(map.get("OverallDifficulty"));
			this.approachRate = Double.parseDouble(map.get("ApproachRate"));
			this.hpDrainRate = Double.parseDouble(map.get("HPDrainRate"));
			this.hitpoints = reader.getHitpoints();
			this.background = reader.getBakground();
			this.songName = reader.getOptionMap(BeatmapOptions.GENERAL).get("AudioFilename");
			this.breakTimes = reader.getBreakTimes();
			this.startingTime = reader.getStartingTime();
		} catch (IOException e) {
			e.printStackTrace();
		} 		
	}
	
	public List<Hitpoint> getHitpoints() {
		return hitpoints;
	}

	public double getHpDrainRate() {
		return hpDrainRate;
	}

	public double getCircleSize() {
		return circleSize;
	}

	public double getOverallDifficulty() {
		return overallDifficulty;
	}

	public double getApproachRate() {
		return approachRate;
	}
	
	public String getBackground() {
		return this.background;
	}

	public String getSongName() {
		return songName;
	}

	public List<List<Double>> getBreakTimes() {
		return breakTimes;
	}

	public double getStartingTime() {
		return startingTime;
	}

}