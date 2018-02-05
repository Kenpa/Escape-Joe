package com.ptamobile.game.escapejoefinal.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

public class Background {
	
	private Texture bgTexture;
	private Sprite bgScroll;
	private float bgSpeed;
	private float scrollTimer;
	
	public Background(float speed) {
		int random = MathUtils.random(0,1);
		if (random == 1)
			bgTexture = new Texture(Gdx.files.internal("images/bg.png"));
		else
			bgTexture = new Texture(Gdx.files.internal("images/bg2.png"));
		bgTexture.setWrap(TextureWrap.Repeat, TextureWrap.Repeat);
		bgScroll = new Sprite(bgTexture);
		bgScroll.setPosition(-427,-300);
		bgScroll.setSize(854,600);
		bgSpeed = 0f;
		scrollTimer = 0.0f;
	}
	
	public Texture getBgTexture() {
		return bgTexture;
	}
	public void setBgTexture(Texture bgTexture) {
		this.bgTexture = bgTexture;
	}
	public Sprite getBgScroll() {
		return bgScroll;
	}
	public void setBgScroll(Sprite bgScroll) {
		this.bgScroll = bgScroll;
	}
	public float getBgSpeed() {
		return bgSpeed;
	}
	public void setBgSpeed(float bgSpeed) {
		this.bgSpeed = bgSpeed;
	}
	public float getScrollTimer() {
		return scrollTimer;
	}
	public void setScrollTimer(float scrollTimer) {
		this.scrollTimer = scrollTimer;
	}
	
	public void backgroundScrolling(float delta)
	{
		scrollTimer += delta*bgSpeed;
		if (scrollTimer >1.0f)
			scrollTimer = 0.0f;
		
		bgScroll.setU(scrollTimer);
		bgScroll.setU2(scrollTimer+1);

	}
	
	public void update(float speed, boolean ready)
	{
		if (ready)
		{
			if (this.bgSpeed >= speed)
				this.bgSpeed = speed;
			else
				this.bgSpeed = this.bgSpeed + 0.005f;
		}
		else
			this.bgSpeed = this.bgSpeed + 0.003f;
	}
	
	public void reset(float speed) {
		this.bgSpeed = speed;
	}
}
