package com.ptamobile.game.escapejoefinal.services;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class SettingsHelper extends Table {

	public SettingsHelper(Skin skin) {
		super(skin);
		
	}
	
	@Override
	public float getPrefWidth() {
		return 516f;
	}
	
	@Override
	public float getPrefHeight() {
		return 355f;
	}

	
}