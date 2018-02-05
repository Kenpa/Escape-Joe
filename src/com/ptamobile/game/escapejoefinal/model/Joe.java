package com.ptamobile.game.escapejoefinal.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Polygon;
import com.ptamobile.game.escapejoefinal.EscapeJoe;

public class Joe extends Entity {
	
	private Polygon[] polygons;
	private boolean ready;
	private Profile profile;
	private float realSpeed;
	
	private static final float SPEED_MULTIPLICATOR = 0.00625f;
	private static final float ROTATION_MULTIPLICATOR = 3f;
	
	public Joe() {
		ObstacleVertices vertices = new ObstacleVertices();
		width = 122f*EscapeJoe.RATIO;
		height = 84f*EscapeJoe.RATIO;
		position.x = - EscapeJoe.virtualViewport.getWidth()*0.75f;
		position.y = -height/2;
		bounds.set(position.x, position.y, width, height);
		polygons = vertices.getJoeVertices();
		
		for(Polygon polygon : polygons)
		{
			for (int i = 0; i < polygon.getVertices().length; i++)
			{
				polygon.getVertices()[i] = polygon.getVertices()[i]*width;
			}
			polygon.setOrigin(bounds.width/2, bounds.height/2);
		}
		ready = false;
		
		profile = EscapeJoe.profileManager.retrieveProfile();
		realSpeed = 0f;
	}

	public Polygon[] getPolygons() {
		return polygons;
	}
	public void setPolygons(Polygon[] polygons) {
		this.polygons = polygons;
	}
	public boolean isReady() {
		return ready;
	}
	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public void update(float speed) {
		
		if (!ready)
		{
			position.set(position.x + EscapeJoe.virtualViewport.getVirtualWidth()*0.003f, position.y);
			if(position.x > -EscapeJoe.virtualViewport.getVirtualWidth() *0.45f)
				ready = true;
		}
		
		if (profile.getChoice() == 1)
			calcSpeed(profile.getDegree());
		else
			this.realSpeed = Gdx.input.getAccelerometerX();
		
		position.set(position.x, position.y - EscapeJoe.virtualViewport.getVirtualHeight()*SPEED_MULTIPLICATOR*speed*realSpeed);
		
		if (position.y < - EscapeJoe.virtualViewport.getVirtualHeight()*0.5f)
			position.set(position.x, -EscapeJoe.virtualViewport.getVirtualHeight()*0.5f);
		if (position.y > EscapeJoe.virtualViewport.getVirtualHeight()*0.5f -bounds.height)
			position.set(position.x, EscapeJoe.virtualViewport.getVirtualHeight()*0.5f - bounds.height);
		
		rotation = - realSpeed*ROTATION_MULTIPLICATOR;
		
		bounds.x = position.x;
		bounds.y = position.y;
		
		for (int i = 0; i < polygons.length; i++)
		{
			polygons[i].setPosition(position.x, position.y);
			polygons[i].setRotation(rotation);
		}
	
	}
	
	private float convDeg(float degree) {
		return -degree/9;
	}
	
	private void calcSpeed(float prefDegree) {
		
		if (prefDegree >= -180 && prefDegree <= -90) {
			
			if (Gdx.input.getRoll() - prefDegree > 90 || Gdx.input.getRoll() - prefDegree < - 90)
				this.realSpeed = convDeg(Gdx.input.getRoll() - prefDegree - 360);
			else
				this.realSpeed = convDeg(Gdx.input.getRoll() - prefDegree);
		}
		else if (prefDegree >= 90 && prefDegree <= 180) {
			
			if (Gdx.input.getRoll() - prefDegree > 90 || Gdx.input.getRoll() - prefDegree < - 90)
				this.realSpeed = convDeg(Gdx.input.getRoll() - prefDegree + 360);
			else
				this.realSpeed = convDeg(Gdx.input.getRoll() - prefDegree);		
		}
		else
			this.realSpeed = convDeg(Gdx.input.getRoll() - prefDegree);	
		
		
		if (this.realSpeed > 10)
			this.realSpeed = 10.5f;
		else if (this.realSpeed < -10)
			this.realSpeed = -10.5f;

	}
	
	
}
