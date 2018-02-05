package com.ptamobile.game.escapejoefinal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.ptamobile.game.escapejoefinal.screens.SplashScreen;
import com.ptamobile.game.escapejoefinal.services.MultipleVirtualViewportBuilder;
import com.ptamobile.game.escapejoefinal.services.OrthographicCameraWithVirtualViewport;
import com.ptamobile.game.escapejoefinal.services.ProfileManager;
import com.ptamobile.game.escapejoefinal.services.VirtualViewport;

public class EscapeJoe extends Game {
	
	public static final FPSLogger fpslogger = new FPSLogger();
	public static final String LOG = EscapeJoe.class.getSimpleName();
	
	public static final AssetManager assets = new AssetManager();
	public static final ProfileManager profileManager = new ProfileManager();
	
	public static VirtualViewport virtualViewport;
	public static OrthographicCameraWithVirtualViewport camera;
	public static MultipleVirtualViewportBuilder multipleVirtualViewportBuilder;
		
	public static float RATIO; //ratio de l'écran du device
	public static SpriteBatch batch;
	
	public static boolean firstLaunch;
	
	public static ActionResolver actionResolver;
	public static AdControl adControl;
	public static IabInterface iabControl;
	
	public static boolean purchased;

	public EscapeJoe(ActionResolver aResolver, AdControl control, IabInterface iab) {
		actionResolver = aResolver;
		adControl = control;
		iabControl = iab;
		purchased = false;
	};

	@Override
	public void create() {
		Gdx.graphics.setVSync(true);
		Gdx.input.setCatchBackKey(true);
		
		RATIO = 1.76f/((float) Gdx.graphics.getWidth()/ (float) Gdx.graphics.getHeight());
		//Gdx.app.log(EscapeJoe.LOG, String.valueOf(RATIO));
		
		multipleVirtualViewportBuilder = new MultipleVirtualViewportBuilder(800, 480, 854, 600);
		virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		camera = new OrthographicCameraWithVirtualViewport(virtualViewport);
		camera.position.set(0f, 0f, 0f);
		
		batch = new SpriteBatch();
		
		//on essaie de retrouver le profil
		profileManager.retrieveProfile();
		
		firstLaunch = true;
	}

	@Override
	public void dispose() {
		super.dispose();
		assets.clear();
		batch.dispose();

	}

	@Override
	public void render() {
		if (getScreen() != null) getScreen().render(Gdx.graphics.getDeltaTime()); 
			
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		
		virtualViewport = multipleVirtualViewportBuilder.getVirtualViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setVirtualViewport(virtualViewport);
		camera.updateViewport();
		camera.position.set(0f, 0f, 0f);
		
		if(getScreen() == null)
			setScreen(new SplashScreen(this));
	}

	@Override
	public void pause() {
		super.pause();
		
		profileManager.persist();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public static boolean isFirstLaunch() {
		return firstLaunch;
	}

	public static void setFirstLaunch(boolean firstLaunch) {
		EscapeJoe.firstLaunch = firstLaunch;
	}

	public static boolean isPurchased() {
		return purchased;
	}

	public static void setPurchased(boolean purchased) {
		EscapeJoe.purchased = purchased;
	}
	
	
	
	
}
