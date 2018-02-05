package com.ptamobile.game.escapejoefinal.model;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.ptamobile.game.escapejoefinal.EscapeJoe;

public class Obstacle extends Entity implements Poolable {
	
	public enum ObsType { AstSmall, AstBig, Satellite, Cosmonaute, Fusee, Plate, Mineral };
	
	private boolean alive;
	private ObsType type;
	private Polygon[] polygons;
	private ObstacleVertices vertices;
		
	public static final float HIGH_COEF_WIDTH = 1.6f;
	
	public Obstacle() {
		super();
		alive = false;
		vertices = new ObstacleVertices();
	}
	
	public void init(float posX, float posY, float speed, ObsType type) {
		
		switch (type)
		{
			case AstSmall:
				width = 83f*EscapeJoe.RATIO;
				height = 78f*EscapeJoe.RATIO;
				polygons = vertices.getAstSmallVertices();
				break;	
				
			case AstBig:
				width = 130f*EscapeJoe.RATIO;
				height = 121f*EscapeJoe.RATIO;
				polygons = vertices.getAstBigVertices();
				break;
				
			case Satellite:
				width = 156f*EscapeJoe.RATIO;
				height = 102f*EscapeJoe.RATIO;
				polygons = vertices.getSatelliteVertices();
				break;
				
			case Cosmonaute:
				width = 117f*EscapeJoe.RATIO;
				height = 129f*EscapeJoe.RATIO;
				polygons = vertices.getCosmoVertices();
				break;	
				
			case Mineral:
				width = 109f*EscapeJoe.RATIO;
				height = 119f*EscapeJoe.RATIO;
				polygons = vertices.getMineralVertices();
				break;
				
			case Fusee:
				width = 133f*EscapeJoe.RATIO;
				height = 122f*EscapeJoe.RATIO;
				polygons = vertices.getFuseeVertices();
				break;
				
			case Plate:
				width = 107f*EscapeJoe.RATIO;
				height = 101f*EscapeJoe.RATIO;
				polygons = vertices.getPlateVertices();
				break;
	
			default:
				break;
		}
		position.x = posX;
		position.y = posY;
		bounds.set(position.x, position.y, width, height);
		this.speed = speed;
		this.type = type;
		alive = true;
		
		//mettre les polygons à l'echelle	
		for(Polygon polygon : polygons)
		{
			// on verifie qu'on a pas encore multiplié par width
			if(polygon.getVertices()[0] < 1) {
				for (int i = 0; i < polygon.getVertices().length; i++)
				{
					polygon.getVertices()[i] *= width;
				}
			}
			polygon.setOrigin(bounds.width/2, bounds.height/2);
		}
	}
	
	public void update(float delta) {

		position.add(-delta*500*speed, 0f);
		rotation += delta*100*speed;
		
		bounds.x = position.x;
		bounds.y = position.y;
		
		if (isOutOfScreen())
			alive = false;

		for(Polygon polygon : polygons)
		{
			polygon.setPosition(position.x, position.y);
			polygon.setRotation(rotation);
		}
	}

	private boolean isOutOfScreen() {
		if (bounds.x + width*HIGH_COEF_WIDTH <= -EscapeJoe.virtualViewport.getVirtualWidth()*0.5f)
			return true;
		else
			return false;
	}

	public boolean isAlive() {
		return alive;
	}
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	public ObsType getType() {
		return type;
	}
	public void setType(ObsType type) {
		this.type = type;
	}
	public Polygon[] getPolygons() {
		return polygons;
	}
	public void setPolygons(Polygon[] polygons) {
		this.polygons = polygons;
	}
	public void setPosition(float x, float y) {
		position.x = x;
		position.y = y;
	}

	@Override
	public void reset() {
		position.set(0, 0);
		width = 0;
		height = 0;
		speed = 0;
		rotation = 0;
		bounds.set(0,0,0,0);
		alive = false;
	}
	
}
