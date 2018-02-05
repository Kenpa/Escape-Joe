package com.ptamobile.game.escapejoefinal.tween;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ptamobile.game.escapejoefinal.EscapeJoe;

public class ExplosionAnimator {
	
	private Animation explosionAnimation;
	private TextureRegion[] explosionFrames;
	private TextureRegion currentFrame;

	float stateTime;
	
	public ExplosionAnimator()
	{
		TextureAtlas atlas = EscapeJoe.assets.get("images/gamescreenAtlasxhd.pack", TextureAtlas.class);
		Skin skin = EscapeJoe.assets.get("ui/dialog.json", Skin.class);
		
		explosionFrames = new TextureRegion[10];
		currentFrame = new TextureRegion();
		
		for (int i = 0; i < 10; i++)
		{
			String name = "explosion" + String.valueOf(i);
			explosionFrames[i] = new TextureRegion(atlas.findRegion(name));
		}
		
		explosionAnimation = new Animation(0.070f, explosionFrames);
		stateTime = 0f;
	}

	public Animation getExplosionAnimation() {
		return explosionAnimation;
	}

	public void setExplosionAnimation(Animation explosionAnimation) {
		this.explosionAnimation = explosionAnimation;
	}

	public TextureRegion[] getExplosionFrames() {
		return explosionFrames;
	}

	public void setExplosionFrames(TextureRegion[] explosionFrames) {
		this.explosionFrames = explosionFrames;
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(TextureRegion currentFrame) {
		this.currentFrame = currentFrame;
	}

	public float getStateTime() {
		return stateTime;
	}

	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}

}
