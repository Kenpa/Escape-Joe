package com.ptamobile.game.escapejoefinal.tween;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ptamobile.game.escapejoefinal.EscapeJoe;

public class HowtoAnimator {

	private Animation howtoAnimation;
	private TextureRegion[] howtoFrames;
	private TextureRegion currentFrame;

	float stateTime;
	
	public HowtoAnimator()
	{
		TextureAtlas atlas = EscapeJoe.assets.get("images/dialogAtlas.pack", TextureAtlas.class);
		Skin skin = EscapeJoe.assets.get("ui/dialog.json", Skin.class);
		
		howtoFrames = new TextureRegion[2];
		currentFrame = new TextureRegion();
		
		for (int i = 0; i < 2; i++)
		{
			String name = "howto" + String.valueOf(i);
			howtoFrames[i] = new TextureRegion(atlas.findRegion(name));
		}
		
		howtoAnimation = new Animation(1.25f, howtoFrames);
		stateTime = 0f;
	}

	public Animation getExplosionAnimation() {
		return howtoAnimation;
	}

	public void setExplosionAnimation(Animation explosionAnimation) {
		this.howtoAnimation = explosionAnimation;
	}

	public TextureRegion[] getExplosionFrames() {
		return howtoFrames;
	}

	public void setExplosionFrames(TextureRegion[] explosionFrames) {
		this.howtoFrames = explosionFrames;
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

