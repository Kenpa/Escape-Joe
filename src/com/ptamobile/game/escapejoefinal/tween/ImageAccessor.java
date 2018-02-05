package com.ptamobile.game.escapejoefinal.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ImageAccessor implements TweenAccessor<Image> {

	public static final int POS_XY = 1;
	public static final int ROTATION = 2;
	@Override
	public int getValues(Image target, int tweenType, float[] returnValues) {
		// TODO Auto-generated method stub
		switch(tweenType) {
			case POS_XY:
				returnValues[0] = target.getX();
				returnValues[1]	= target.getY();
				return 2;
			
			case ROTATION:
				returnValues[0] = target.getRotation();
				return 1;
			
			default: assert false; return -1;
		}
		
	}

	@Override
	public void setValues(Image target, int tweenType, float[] newValues) {
		// TODO Auto-generated method stub
		switch(tweenType) {
			case POS_XY : 
				target.setPosition(newValues[0], newValues[1]);
				break;
				
			case ROTATION :
				target.setRotation(newValues[0]);
				break;
				
			default: assert false;
		}
	}

}
