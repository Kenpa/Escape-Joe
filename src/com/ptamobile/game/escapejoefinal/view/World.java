package com.ptamobile.game.escapejoefinal.view;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.TimeUtils;
import com.ptamobile.game.escapejoefinal.EscapeJoe;
import com.ptamobile.game.escapejoefinal.model.Background;
import com.ptamobile.game.escapejoefinal.model.Joe;
import com.ptamobile.game.escapejoefinal.model.Obstacle;
import com.ptamobile.game.escapejoefinal.model.Obstacle.ObsType;
import com.ptamobile.game.escapejoefinal.model.Profile;

public class World {
	
	public static final float INIT_POS_X = EscapeJoe.virtualViewport.getVirtualWidth()*0.5f;
	public static final float VIRTUALWIDTH = EscapeJoe.virtualViewport.getVirtualWidth();
	public static final float VIRTUALHEIGHT = EscapeJoe.virtualViewport.getVirtualHeight();
	
	public static final long TIMETOINC = 800000000;
	public static final long LAST_LIMIT_POPTIME = 593000000;
	
	public static float MIN_HEIGHT_BETWEEN;
	
	public enum WorldState {GAMEON, GAMEOVER, PAUSE, GAMEHOW};
	
	private Profile profile;
	
	private WorldState state;
	private int gameLevel;
	private float worldSpeed, backSpeed;
	private Background bg;
	private Joe joe;
	private final Array<Obstacle> activeObs;
	private final Pool<Obstacle> obstaclePool = new Pool<Obstacle>() {
		@Override
		protected Obstacle newObject() {
			// TODO Auto-generated method stub
			return new Obstacle();
		}
	};
	private long pauseTime;
	private long unpauseTime;
	private long lastPopTime;
	private long limitPopTime;
	
	private String scoreString;
	private int score;
	private int palierScore;
	private long lastScoreInc;
	
	public World() {
		
		profile = EscapeJoe.profileManager.retrieveProfile();
		
		if (EscapeJoe.isFirstLaunch() && profile.isHowto())
			state = WorldState.GAMEHOW;
		else
			state = WorldState.GAMEON;
		

		worldSpeed = 1.0f;
		backSpeed = 1.0f;
		gameLevel = 1;
		bg = new Background(backSpeed/2);
		joe = new Joe();
		activeObs = new Array<Obstacle>();
		
		limitPopTime = 800000000;
		lastPopTime = TimeUtils.nanoTime();

		MIN_HEIGHT_BETWEEN = joe.getHeight() + 5f;
		
		//score
		scoreString = "0";
		score = 0;
		palierScore = 10;
		lastScoreInc = TimeUtils.nanoTime();
	}
	public Background getBg() {
		return bg;
	}
	public void setBg(Background bg) {
		this.bg = bg;
	}
	public Joe getJoe() {
		return joe;
	}
	public void setJoe(Joe joe) {
		this.joe = joe;
	}
	public WorldState getState() {
		return state;
	}
	public void setState(WorldState state) {
		this.state = state;
	}
	public float getWorldSpeed() {
		return worldSpeed;
	}
	public void setWorldSpeed(float worldSpeed) {
		this.worldSpeed = worldSpeed;
	}
	public Array<Obstacle> getActiveObs() {
		return activeObs;
	}
	public long getPauseTime() {
		return pauseTime;
	}
	public void setPauseTime(long pauseTime) {
		this.pauseTime = pauseTime;
	}
	public long getUnpauseTime() {
		return unpauseTime;
	}
	public void setUnpauseTime(long unpauseTime) {
		this.unpauseTime = unpauseTime;
	}
	public long getLimitPopTime() {
		return limitPopTime;
	}
	public void setLimitPopTime(long limitPopTime) {
		this.limitPopTime = limitPopTime;
	}
    public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getScoreString() {
		return scoreString;
	}
	public void setScoreString(String scoreString) {
		this.scoreString = scoreString;
	}
	public void spawnObstacle() {
        
    	int nbObstacle;
    	float random;
    	random = MathUtils.random();
    	
    	switch(gameLevel)
    	{
    	case 1 :
    		if (random < 0.7f)
    			nbObstacle = 1;
    		else
    			nbObstacle = 2;
    		break;
    	case 2:
    		if (random < 0.6f)
    			nbObstacle = 1;
    		else
    			nbObstacle = 2;
    		break;
    	case 3 :
    		if (random < 0.5f)
    			nbObstacle = 1;
    		else
    			nbObstacle = 2;
    		break;
    	case 4 :
    		if (random < 0.45f)
    			nbObstacle = 1;
    		else
    			nbObstacle = 2;
    		break;
    	default :
    		if (random < 0.4f)
    			nbObstacle = 1;
    		else
    			nbObstacle = 2;
    		break;
    	}
    	
    	
    	switch (nbObstacle)
    	{
    	case 1:
        	Obstacle obs = obstaclePool.obtain();
        	int chooseType;
            chooseType = MathUtils.random(0, 2);
            
            popOneObs(obs, chooseType);
	        
            activeObs.add(obs);
            break;
            
    	case 2:
    		Obstacle obs1 = obstaclePool.obtain();
    		Obstacle obs2 = obstaclePool.obtain();
    		int chooseType1, chooseType2;
    		chooseType1 = MathUtils.random(0, 2);
    		chooseType2 = MathUtils.random(0, 2);
    		
    		popTwoObs(obs1, obs2, chooseType1, chooseType2);
    		
            activeObs.add(obs1);
            activeObs.add(obs2);
            break;	
    	}
    	lastPopTime = TimeUtils.nanoTime();
    	pauseTime = 0;
    	unpauseTime = 0;
    }
    
    public void destroyObstacle() {
    	Obstacle obs;
    	int len = activeObs.size;
    	for (int i = len; --i >= 0;)
    	{
    		obs = activeObs.get(i);
    		if (!obs.isAlive())
    		{
    			activeObs.removeIndex(i);
    			obstaclePool.free(obs);
    			
    			//System.out.println("Pool : " + String.valueOf(obstaclePool.getFree()) + " ; Active : " + String.valueOf(activeObs.size) + " ; Total : " + String.valueOf(activeObs.size + obstaclePool.getFree()));
    		}
    	}
    }
    
    public void popOneObs(Obstacle obs, int chooseType) {
    	switch (gameLevel)
    	{
    	case 1:
    		switch (chooseType) 
            {
    			case 0:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.AstSmall);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    			case 1:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.AstBig);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    			case 2:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Satellite);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    		}      
    		break;
    	case 2:
    		switch (chooseType) 
            {
    			case 0:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Plate);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    			case 1:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.AstBig);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    			case 2:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Satellite);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    		}    
    		break;
    	case 3:
    		switch (chooseType) 
            {
    			case 0:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Plate);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    			case 1:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Mineral);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    			case 2:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Satellite);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    		}    
    		break;
    	case 4:
    		switch (chooseType) 
            {
    			case 0:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Plate);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    			case 1:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Mineral);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    			case 2:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Fusee);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    		}    
    		break;
    	default:
    		switch (chooseType) 
            {
    			case 0:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Cosmonaute);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    			case 1:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Mineral);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    			case 2:
    				obs.init(INIT_POS_X, joe.getPosition().y, worldSpeed, ObsType.Fusee);
    				if(obs.getPosition().y + obs.getHeight() > VIRTUALHEIGHT*0.5f)
    					obs.setPosition(obs.getPosition().x, VIRTUALHEIGHT*0.5f - obs.getHeight());
    				break;
    		}    
    		break;
		}      
    }
    public void popTwoObs(Obstacle obs1, Obstacle obs2, int chooseType1, int chooseType2) {
    	
    	switch (gameLevel)
    	{
    	case 1:
    		switch (chooseType1) 
            {
    			case 0:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 78f*EscapeJoe.RATIO), worldSpeed, ObsType.AstSmall);
    				break;
    			case 1:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 121f*EscapeJoe.RATIO), worldSpeed, ObsType.AstBig);
    				break;
    			case 2:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 102f*EscapeJoe.RATIO), worldSpeed, ObsType.Satellite);
    				break;
    		}
            
            switch (chooseType2) 
            {
    			case 0:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 78f*EscapeJoe.RATIO), worldSpeed, ObsType.AstSmall);
    				break;
    			case 1:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 121f*EscapeJoe.RATIO), worldSpeed, ObsType.AstBig);
    				break;
    			case 2:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 102f*EscapeJoe.RATIO), worldSpeed, ObsType.Satellite);
    				break; 				
    		}
    		break;
    	case 2:
    		switch (chooseType1) 
            {
    			case 0:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 101f*EscapeJoe.RATIO), worldSpeed, ObsType.Plate);
    				break;
    			case 1:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 121f*EscapeJoe.RATIO), worldSpeed, ObsType.AstBig);
    				break;
    			case 2:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 102f*EscapeJoe.RATIO), worldSpeed, ObsType.Satellite);
    				break;
    		}
            
            switch (chooseType2) 
            {
    			case 0:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 101f*EscapeJoe.RATIO), worldSpeed, ObsType.Plate);
    				break;
    			case 1:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 121f*EscapeJoe.RATIO), worldSpeed, ObsType.AstBig);
    				break;
    			case 2:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 102f*EscapeJoe.RATIO), worldSpeed, ObsType.Satellite);
    				break; 				
    		}
    		break;
    	case 3:
    		switch (chooseType1) 
            {
    			case 0:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 101f*EscapeJoe.RATIO), worldSpeed, ObsType.Plate);
    				break;
    			case 1:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 119f*EscapeJoe.RATIO), worldSpeed, ObsType.Mineral);
    				break;
    			case 2:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 102f*EscapeJoe.RATIO), worldSpeed, ObsType.Satellite);
    				break;
    		}
            
            switch (chooseType2) 
            {
    			case 0:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 101f*EscapeJoe.RATIO), worldSpeed, ObsType.Plate);
    				break;
    			case 1:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 119f*EscapeJoe.RATIO), worldSpeed, ObsType.Mineral);
    				break;
    			case 2:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 102f*EscapeJoe.RATIO), worldSpeed, ObsType.Satellite);
    				break; 				
    		}
    		break;
    	case 4:
    		switch (chooseType1) 
            {
    			case 0:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 101f*EscapeJoe.RATIO), worldSpeed, ObsType.Plate);
    				break;
    			case 1:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 119f*EscapeJoe.RATIO), worldSpeed, ObsType.Mineral);
    				break;
    			case 2:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 122f*EscapeJoe.RATIO), worldSpeed, ObsType.Fusee);
    				break;
    		}
            
            switch (chooseType2) 
            {
    			case 0:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 101f*EscapeJoe.RATIO), worldSpeed, ObsType.Plate);
    				break;
    			case 1:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 119f*EscapeJoe.RATIO), worldSpeed, ObsType.Mineral);
    				break;
    			case 2:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 122f*EscapeJoe.RATIO), worldSpeed, ObsType.Fusee);
    				break; 				
    		}
    		break;
    	
    	default:
    		switch (chooseType1) 
            {
    			case 0:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 129f*EscapeJoe.RATIO), worldSpeed, ObsType.Cosmonaute);
    				break;
    			case 1:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 119f*EscapeJoe.RATIO), worldSpeed, ObsType.Mineral);
    				break;
    			case 2:
    				obs1.init(INIT_POS_X, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 122f*EscapeJoe.RATIO), worldSpeed, ObsType.Fusee);
    				break;
    		}
            
            switch (chooseType2) 
            {
    			case 0:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 129f*EscapeJoe.RATIO), worldSpeed, ObsType.Cosmonaute);
    				break;
    			case 1:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 119f*EscapeJoe.RATIO), worldSpeed, ObsType.Mineral);
    				break;
    			case 2:
    				obs2.init(VIRTUALWIDTH*0.5f + MathUtils.random(0f,VIRTUALWIDTH*0.07f), MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - 122f*EscapeJoe.RATIO), worldSpeed, ObsType.Fusee);
    				break; 				
    		}
    		break;
    	}
        
        if( obs2.getPosition().y >= obs1.getPosition().y)
        {
        	do{	
        		obs2.setPosition(obs2.getPosition().x, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - obs2.getHeight()));
        		obs1.setPosition(obs1.getPosition().x, MathUtils.random(-VIRTUALHEIGHT*0.5f, obs2.getPosition().y - obs1.getHeight()));
        	} while(obs2.getPosition().y - obs1.getPosition().y + obs2.getHeight() < MIN_HEIGHT_BETWEEN || VIRTUALHEIGHT*0.5f - obs2.getPosition().y + obs2.getHeight() < MIN_HEIGHT_BETWEEN || VIRTUALHEIGHT*0.5f + obs1.getPosition().y < MIN_HEIGHT_BETWEEN/2 );
        }
        
        else
        {
        	do {	
        		obs1.setPosition(obs1.getPosition().x, MathUtils.random(-VIRTUALHEIGHT*0.5f, VIRTUALHEIGHT*0.5f - obs1.getHeight()));
        		obs2.setPosition(obs2.getPosition().x, MathUtils.random(-VIRTUALHEIGHT*0.5f, obs1.getPosition().y - obs2.getHeight()));
        	} while(obs1.getPosition().y - obs2.getPosition().y + obs1.getHeight() < MIN_HEIGHT_BETWEEN || VIRTUALHEIGHT*0.5f - obs1.getPosition().y + obs1.getHeight() < MIN_HEIGHT_BETWEEN || VIRTUALHEIGHT*0.5f + obs2.getPosition().y < MIN_HEIGHT_BETWEEN/2 );
        }
    }
	
	public void update(float delta) {
		
		switch (state) 
		{
			case GAMEON:
				bg.update((backSpeed)/2, joe.isReady());
				joe.update(worldSpeed);
				
				for (Obstacle obs : activeObs)
				{
					obs.update(delta);
					for (int i = 0; i < joe.getPolygons().length ; i++)
					{
						for (int j = 0; j < obs.getPolygons().length ; j++)
						{
							if(Intersector.overlapConvexPolygons(joe.getPolygons()[i], obs.getPolygons()[j]))
								state = WorldState.GAMEOVER;
						}
					}
				}
				
				// pop obstacle à intervalle régulier
				if(TimeUtils.nanoTime() - lastPopTime - (unpauseTime - pauseTime) > limitPopTime*EscapeJoe.RATIO && joe.isReady() && bg.getBgSpeed() >= 0.5f)
					spawnObstacle();
				// gerer la vitesse d'incrementation du score
				if(TimeUtils.nanoTime() - lastScoreInc > TIMETOINC/worldSpeed){
					score++;
					lastScoreInc = TimeUtils.nanoTime();
					scoreString = String.valueOf(score) ;
				}
				// tous les 10points, on augmente la vitesse du world, et du coup, vu que les obs vont aller plus vite, on les fait pop plus vite aussi
				if(score > palierScore && worldSpeed < 1.45f)
				{
					palierScore += 10;
					worldSpeed += 0.0208f;
					backSpeed += 0.025f;
					if(limitPopTime*EscapeJoe.RATIO > LAST_LIMIT_POPTIME*EscapeJoe.RATIO)
					{
							limitPopTime -= 10358000;
					}
				}
				
				if (score >= 0 && score < 30)
					gameLevel = 1;
				else if (score >= 30 && score < 65)
					gameLevel = 2;
				else if (score >= 65 && score < 110)
					gameLevel = 3;
				else if (score >= 110 && score < 160)
					gameLevel = 4;
				else
					gameLevel = 5;
				break;
	
			case GAMEOVER:
				bg.update((backSpeed)/2, joe.isReady());
				for (Obstacle obs : activeObs)
				{
					obs.update(delta);
				}
				break;
				
			case PAUSE:
				
				break;
			
			case GAMEHOW:
				//bg.update(worldSpeed/2);
				
			default:
				break;
		}
		destroyObstacle();
	}

}
