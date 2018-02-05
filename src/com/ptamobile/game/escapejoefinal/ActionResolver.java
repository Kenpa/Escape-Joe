package com.ptamobile.game.escapejoefinal;

public interface ActionResolver {
	
	  public boolean getSignedInGPGS();
	  public void loginGPGS();
	  public void logoutGPGS();
	  public void submitScoreGPGS(int score);
	  public void getLeaderboardGPGS();
	  public void unlockAchievementGPGS(String achievementId);
	
}