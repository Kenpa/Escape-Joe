package com.ptamobile.game.escapejoefinal.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.ptamobile.game.escapejoefinal.EscapeJoe;
import com.ptamobile.game.escapejoefinal.model.Profile;

public class ProfileManager {

	private static final String PROFILE_DATA_FILE = "data/score.json";
	private Profile profile;
	
	public ProfileManager() {}
	
	public Profile retrieveProfile()
	{
		FileHandle profileDataFile = Gdx.files.local(PROFILE_DATA_FILE);
		
		if (profile != null)
			return profile;
		
		Json json = new Json();
		
		if (profileDataFile.exists()) {
			try {
				String profileAsText = profileDataFile.readString().trim();
				
				if (profileAsText.matches("^[A-Za-z0-9/+=]+$")) {
					profileAsText = Base64Coder.decodeString(profileAsText);
				}
				
				profile = json.fromJson(Profile.class, profileAsText);
			}
			
			catch(Exception e) {
				Gdx.app.error(EscapeJoe.LOG, "Unable to parse profile data file", e);
				
				//solution
				profile = new Profile();
				persist(profile);
			}		
		}
		else {
			profile = new Profile();
			persist(profile);
		}
		return profile;
	}
	
	protected void persist(Profile profile)
	{
		FileHandle profileDataFile = Gdx.files.local(PROFILE_DATA_FILE);
		
		Json json = new Json();
		
		String profileAsText = json.toJson(profile);
		
		profileAsText = Base64Coder.encodeString(profileAsText);
		
		profileDataFile.writeString(profileAsText, false);
	}
	
	public void persist()
	{
		if (profile != null) {
			persist(profile);
		}
	}
}
