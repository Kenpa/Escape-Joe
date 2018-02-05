package com.ptamobile.game.escapejoefinal.screens;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ptamobile.game.escapejoefinal.EscapeJoe;

public class SplashScreen extends AbstractScreen {
	
	private TextureAtlas atlas;
	private Image splashImage;
	
	public SplashScreen(EscapeJoe game) {
		super(game);
	}
	
	public void show() {
		EscapeJoe.assets.load("ui/menu.json", Skin.class, new SkinLoader.SkinParameter("images/menuAtlas.pack"));
		EscapeJoe.assets.load("ui/game.json", Skin.class, new SkinLoader.SkinParameter("images/gamescreenAtlasxhd.pack"));
		EscapeJoe.assets.load("ui/dialog.json", Skin.class, new SkinLoader.SkinParameter("images/dialogAtlas.pack"));
		EscapeJoe.assets.load("ui/wetin30_0.png", Texture.class);
		EscapeJoe.assets.load("ui/wetin18_0.png", Texture.class);
		EscapeJoe.assets.load("ui/plump50_0.png", Texture.class);
		EscapeJoe.assets.load("sounds/menuBGM.ogg", Music.class);
		EscapeJoe.assets.load("sounds/gameBGM.ogg", Music.class);
		EscapeJoe.assets.load("sounds/explosion.mp3", Sound.class);
		
		EscapeJoe.adControl.showAds(false);
		
		atlas = new TextureAtlas("images/splashscreen.pack");
		splashImage = new Image(atlas.findRegion("logo"));
		splashImage.getColor().a = 0f;
		splashImage.setPosition(-427f, -300f);	
		splashImage.addAction(sequence(fadeIn(1f),delay(0.5f),fadeOut(1f),
				new Action() {
					@Override
					public boolean act(float delta) {
						game.setScreen(new MenuScreen(game));
						//EscapeJoe.actionResolver.loginGPGS();
						return true;
					}
				}));
		
		stage.addActor(splashImage);
				
	}
	
	@Override
	public void render(float delta)
	{
		super.render(delta);
		if (EscapeJoe.assets.update()) {	
			stage.act(delta);
	        stage.draw();
		}
	}
	
	@Override
	public void hide() {
		super.hide();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		atlas.dispose();
	}

}
