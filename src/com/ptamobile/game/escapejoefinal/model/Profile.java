package com.ptamobile.game.escapejoefinal.model;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.Serializable;
import com.badlogic.gdx.utils.JsonValue;

public class Profile implements Serializable {
	
	private int highScore;
	private int points;
	
	private int choice;
	private float degree;
	private float degreeCustom;
	private boolean musicOn, soundOn;
	private boolean howto;
	private boolean firstPost;
	
	private int shipskin;
	private int joeskin;
	
	private Map<Integer,Boolean> availableJoe, availableShip;
	private Map<Integer,Integer>lastScores;
	
	public Profile() {
		
		choice = 1;
		highScore = 0;
		degree = 0;
		degreeCustom = 0;
		musicOn = true;
		soundOn = true;
		howto = true;
		firstPost = true;
		
		points = 0;	
		shipskin = 0;
		joeskin = 0;
		
		availableJoe = new HashMap<Integer,Boolean>(32,0.81f);
		availableShip = new HashMap<Integer,Boolean>(32,0.81f);
		lastScores = new HashMap<Integer,Integer>();
		
		lastScores.put(0, 0);
		lastScores.put(1, 0);
		lastScores.put(2, 0);
		
		availableJoe.put(0,true);
		availableShip.put(0,true);
	}
	
	public int getChoice() {
		return choice;
	}
	
	public int getHighScore() {
		return highScore;
	}
	
	public float getDegree() {
		return degree;
	}
	
	public boolean isMusicOn() {
		return musicOn;
	}
	
	public boolean isSoundOn() {
		return soundOn;
	}
	
	public float getDegreeCustom() {
		return degreeCustom;
	}

	public void setDegreeCustom(float degreeCustom) {
		this.degreeCustom = degreeCustom;
	}

	public boolean notifyScore(int score) {			
		
		if (score > getHighScore()) {
			highScore = score;
			
			return true;
		}
		return false;	
	}
	
	public void setChoice(int choice) {
		this.choice = choice;
	}

	public void setDegree(float degree) {
		this.degree = degree;
	}

	public void setMusicOn(boolean musicOn) {
		this.musicOn = musicOn;
	}

	public void setSoundOn(boolean soundOn) {
		this.soundOn = soundOn;
	}
	
	public boolean isHowto() {
		return howto;
	}

	public void setHowto(boolean howto) {
		this.howto = howto;
	}

	public boolean isFirstPost() {
		return firstPost;
	}

	public void setFirstPost(boolean firstPost) {
		this.firstPost = firstPost;
	}
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getShipskin() {
		return shipskin;
	}

	public void setShipskin(int shipskin) {
		this.shipskin = shipskin;
	}

	public int getJoeskin() {
		return joeskin;
	}

	public void setJoeskin(int joeskin) {
		this.joeskin = joeskin;
	}

	
	public Map<Integer, Boolean> getAvailableJoe() {
		return availableJoe;
	}

	public void setAvailableJoe(Map<Integer, Boolean> availableJoe) {
		this.availableJoe = availableJoe;
	}

	public Map<Integer, Boolean> getAvailableShip() {
		return availableShip;
	}

	public void setAvailableShip(Map<Integer, Boolean> availableShip) {
		this.availableShip = availableShip;
	}

	public Map<Integer, Integer> getLastScores() {
		return lastScores;
	}

	public void setLastScores(Map<Integer, Integer> lastScores) {
		this.lastScores = lastScores;
	}

	@Override
	public void write(Json json) {
		// TODO Auto-generated method stub
		json.writeValue("highScore", highScore);
		json.writeValue("choice", choice);
		json.writeValue("degree", degree);
		json.writeValue("degreeCustom", degreeCustom);
		json.writeValue("musicOn", musicOn);
		json.writeValue("soundOn", soundOn);
		json.writeValue("howto", howto);
		json.writeValue("firstPost", firstPost);
		json.writeValue("joeskin", joeskin);
		json.writeValue("shipskin", shipskin);
		json.writeValue("points", points);
		
		json.writeValue("lastScores", lastScores);
		json.writeValue("availableShip", availableShip);
		json.writeValue("availableJoe", availableJoe);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
		highScore = json.readValue("highScore", Integer.class, jsonData);
		choice = json.readValue("choice", Integer.class, jsonData);
		degree = json.readValue("degree", Float.class, jsonData);
		degreeCustom = json.readValue("degreeCustom", Float.class, jsonData);
		musicOn = json.readValue("musicOn", Boolean.class, jsonData);
		soundOn = json.readValue("soundOn", Boolean.class, jsonData);
		howto = json.readValue("howto", Boolean.class, jsonData);
		firstPost = json.readValue("firstPost", Boolean.class, jsonData);
		
		joeskin = json.readValue("joeskin", Integer.class, jsonData);
		shipskin = json.readValue("shipskin", Integer.class, jsonData);
		points = json.readValue("points", Integer.class, jsonData);
		
	    Map<String,Boolean> availableJoe = json.readValue( "availableJoe", HashMap.class, Boolean.class, jsonData);
        for( String pos : availableJoe.keySet()) {
            int intpos = Integer.valueOf( pos );
            boolean available = availableJoe.get( pos);
            this.availableJoe.put( intpos, available);
        }
        
	    Map<String,Boolean> availableShip = json.readValue( "availableShip", HashMap.class, Boolean.class, jsonData);
        for( String pos : availableShip.keySet()) {
            int intpos = Integer.valueOf( pos );
            boolean available = availableShip.get( pos);
            this.availableShip.put( intpos, available);
        }
        
	    Map<String,Integer> lastScores = json.readValue( "lastScores", HashMap.class, Integer.class, jsonData);
        for( String pos : lastScores.keySet()) {
            int intpos = Integer.valueOf( pos );
            Integer score = lastScores.get( pos);
            this.lastScores.put( intpos, score);
        }
		
	}
	
	

}
