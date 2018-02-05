package com.ptamobile.game.escapejoefinal.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ptamobile.game.escapejoefinal.EscapeJoe;
import com.ptamobile.game.escapejoefinal.tween.ImageAccessor;

public class AbstractScreen implements Screen {
	
	protected final EscapeJoe game;
	protected final Stage stage;
	protected final InputMultiplexer multiplexer;
	protected TweenManager tweenManager;
	
	public AbstractScreen(EscapeJoe game)
	{
		this.game = game;
		stage = new Stage(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, EscapeJoe.batch);
		
		multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(multiplexer);
		
		tweenManager = new TweenManager();
		Tween.registerAccessor(Image.class, new ImageAccessor());
		
		stage.setCamera(EscapeJoe.camera);
		
	}

	@Override
	public void render(float delta) {
	
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        
        EscapeJoe.camera.update();
        EscapeJoe.batch.setProjectionMatrix(EscapeJoe.camera.combined);
        
        tweenManager.update(delta);
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		dispose();
		Gdx.gl.glClearColor( 0f, 0f, 0f, 1f );
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
