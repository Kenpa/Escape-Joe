package com.ptamobile.game.escapejoefinal.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ptamobile.game.escapejoefinal.EscapeJoe;

public class DialogHelper extends Dialog {
	private Skin skin;
	private LabelStyle scoreStyle;
	private Texture wetinPng;
	private BitmapFont wetin30;
	
	public DialogHelper(String title, Skin skin, String windowStyleName) {
		super(title, skin, windowStyleName);
		this.skin = skin;
		
		initialize();
	}
	
	private void initialize() {
		
		fadeDuration = 100f;
		setModal(true);
		setMovable(false);
		getButtonTable().defaults().height(73f);
		getButtonTable().defaults().expandX().pad(0f, 20f, 20f, 20f);
		getContentTable().defaults().expand().right().padRight(100f);
		getContentTable().padTop(110f).padBottom(30f);
		
		wetinPng = EscapeJoe.assets.get("ui/wetin30_0.png", Texture.class);
		wetinPng.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		wetin30 = new BitmapFont(Gdx.files.internal("ui/wetin30.fnt"), new TextureRegion(wetinPng), false);
		
		scoreStyle = new LabelStyle(wetin30, Color.BLACK);
	}

	public DialogHelper button(String style, InputListener listener) {
		Button btn = new Button(skin, style);
		btn.addListener(listener);
		button(btn);
		return this;	
	}
	
	public DialogHelper score(String score) {
		super.text(new Label((score), scoreStyle));
		return this;
	}
	
	public DialogHelper bestScore(String score) {
		super.getContentTable().row();
		super.text(new Label((score), scoreStyle));
		return this;
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
