package com.ptamobile.game.escapejoefinal.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {

	protected float speed;
	protected float rotation;
	protected Vector2 position;
	protected float width;
	protected float height;
	protected Rectangle bounds;
	
	
	public Entity() {
		super();
		speed = 0f;
		rotation = 0f;
		position = new Vector2(0,0);
		width = 0;
		height = 0;
		bounds = new Rectangle(0,0,0,0);
	}

	public Entity(float speed, float rotation, Vector2 position, float width,
			float height, Rectangle bounds) {
		super();
		this.speed = speed;
		this.rotation = rotation;
		this.position = position;
		this.width = width;
		this.height = height;
		this.bounds = bounds;
	}
	
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	public float getRotation() {
		return rotation;
	}
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	public Vector2 getPosition() {
		return position;
	}
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public Rectangle getBounds() {
		return bounds;
	}
	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	
}
