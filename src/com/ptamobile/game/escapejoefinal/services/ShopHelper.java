package com.ptamobile.game.escapejoefinal.services;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.ptamobile.game.escapejoefinal.EscapeJoe;

public class ShopHelper extends Table {
	
	private Texture wetinPng18;
	private BitmapFont wetin18;
	private LabelStyle normalStyle;
	private ArrayList<Image> listJoe;
	private ArrayList<Image> listVaisseaux;
	
	public ShopHelper(Skin skin) {
		super(skin);

		
		wetinPng18 = EscapeJoe.assets.get("ui/wetin18_0.png", Texture.class);
		wetinPng18.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		wetin18 = new BitmapFont(Gdx.files.internal("ui/wetin18.fnt"), new TextureRegion(wetinPng18), false);
		
		normalStyle = new LabelStyle(wetin18, Color.BLACK);
		
		listJoe = new ArrayList<Image>();
		listVaisseaux = new ArrayList<Image>();
	}	
	
	@Override
	public float getPrefWidth() {
		return 653f;
	}
	
	@Override
	public float getPrefHeight() {
		return 378f;
	}

	public ArrayList<Image> getListJoe() {
		return listJoe;
	}

	public void setListJoe(ArrayList<Image> listJoe) {
		this.listJoe = listJoe;
	}

	public ArrayList<Image> getListVaisseaux() {
		return listVaisseaux;
	}

	public void setListVaisseaux(ArrayList<Image> listVaisseaux) {
		this.listVaisseaux = listVaisseaux;
	}

	public LabelStyle getNormalStyle() {
		return normalStyle;
	}

	public void setNormalStyle(LabelStyle normalStyle) {
		this.normalStyle = normalStyle;
	}
	
	

	
}