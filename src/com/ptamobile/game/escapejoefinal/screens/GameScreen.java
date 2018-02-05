package com.ptamobile.game.escapejoefinal.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.ptamobile.game.escapejoefinal.EscapeJoe;
import com.ptamobile.game.escapejoefinal.model.Profile;
import com.ptamobile.game.escapejoefinal.services.DialogHelper;
import com.ptamobile.game.escapejoefinal.services.ManagedPixmapTextureData;
import com.ptamobile.game.escapejoefinal.services.SettingsHelper;
import com.ptamobile.game.escapejoefinal.tween.HowtoAnimator;
import com.ptamobile.game.escapejoefinal.view.World;
import com.ptamobile.game.escapejoefinal.view.World.WorldState;
import com.ptamobile.game.escapejoefinal.view.WorldRenderer;

public class GameScreen extends AbstractScreen {
	
	public static final float VIRTUALWIDTH = EscapeJoe.virtualViewport.getVirtualWidth();
	public static final float VIRTUALHEIGHT = EscapeJoe.virtualViewport.getVirtualHeight();

	private World world;
	private WorldRenderer worldRenderer;
	
	private Texture plumpPng, texture;
	private BitmapFont plump50;
	private Label scoreDisplay;
	private LabelStyle scoreStyle;
	
	private Button pauseButton, resumeButton, quitButton, soundButton, musicButton, okButton, showButton;
	private Image labelPause, dialogBackground, transitionBg;
	private Pixmap pixmap;
	
	private Profile profile;
	private DialogHelper dialogH;
	private SettingsHelper howtoDial;
	private Table scoreTable, gameoverTable, howtoTable;

	private HowtoAnimator howtoH;
	
	private Boolean tableSwitch, tableSwitch2, showads;
	private int optionGO;
	
	private Music gameBGM;
	private Sound explosion;
	
	public GameScreen(EscapeJoe game) {
		super(game);
		
		plumpPng = EscapeJoe.assets.get("ui/plump50_0.png", Texture.class);
		plumpPng.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		plump50 = new BitmapFont(Gdx.files.internal("ui/plump50.fnt"), new TextureRegion(plumpPng), false);
		
		pixmap =  new Pixmap(1,1, Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.fillRectangle(0, 0, 1, 1);
		
		world = new World();
		worldRenderer = new WorldRenderer(world);
		
		howtoH = new HowtoAnimator();
		
		tableSwitch = false;
		tableSwitch2 = false;
		showads = false;
		optionGO = 0;
		
		InputProcessor backProcessor = new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				if(keycode == Keys.BACK)
				{
					switch (world.getState()) {
						case GAMEON:
							tableSwitch = false;
							world.setPauseTime(TimeUtils.nanoTime());
							world.setState(WorldState.PAUSE);						
							break;
	
						case PAUSE:
							
							if (quitButton.getX() <= VIRTUALWIDTH*0.5f-300f && resumeButton.getX() >= -VIRTUALWIDTH*0.5f - 4f)
							{
								quitButton.addAction(Actions.sequence(Actions.moveBy(304f, 0, 0.5f, Interpolation.circle)));
								resumeButton.addAction(Actions.sequence(Actions.moveBy(-304f, 0, 0.5f, Interpolation.circle),
								new Action() {
									@Override
									public boolean act(float delta) {
										tableSwitch = false;
										world.setUnpauseTime(TimeUtils.nanoTime());
										world.setState(WorldState.GAMEON);
										return true;
									}
								}));
							}
							break;
							
						case GAMEOVER:
							break;
					}
				}
				return super.keyDown(keycode);
			}
		};
		
		multiplexer.addProcessor(backProcessor);
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	@Override
	public void show() {
		
		EscapeJoe.adControl.showAds(false);
		
		profile = EscapeJoe.profileManager.retrieveProfile();
		
		explosion = EscapeJoe.assets.get("sounds/explosion.mp3", Sound.class);
		gameBGM = EscapeJoe.assets.get("sounds/gameBGM.ogg", Music.class);
		
		if (profile.isMusicOn())
		{

			gameBGM.setVolume(1f);
			gameBGM.play();
			gameBGM.setLooping(true);
		}
		else
		{	
			gameBGM.setVolume(0f);
			gameBGM.stop();
		}
		
		TextureAtlas atlas = EscapeJoe.assets.get("images/dialogAtlas.pack", TextureAtlas.class);
		Skin skin = EscapeJoe.assets.get("ui/dialog.json", Skin.class);
		
		//gameon
		scoreTable = new Table();
		scoreTable.setPosition(-VIRTUALWIDTH*0.5f, -VIRTUALHEIGHT*0.5f);
		scoreTable.setWidth(VIRTUALWIDTH);
		scoreTable.setHeight(VIRTUALHEIGHT);
		scoreStyle = new LabelStyle(plump50, Color.WHITE);
		scoreDisplay = new Label(String.valueOf(world.getScore()), scoreStyle);
		scoreTable.add(scoreDisplay).expand().top().right().padRight(VIRTUALWIDTH*0.02f);
		
		pauseButton = new Button(skin, "btnPause");
		pauseButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);

				tableSwitch = false;
				world.setPauseTime(TimeUtils.nanoTime());
				world.setState(WorldState.PAUSE);
			}
		});
		
		//gamepause
		resumeButton = new Button(skin, "btnResume");
		quitButton = new Button(skin, "btnQuitP");
		labelPause = new Image(skin, "gamepaused");
		soundButton = new Button(skin, "btnSound");
		musicButton = new Button(skin, "btnMusic");
		soundButton.addListener(new ClickListener() {
		@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);       		
        		
        		if (profile.isSoundOn())
        			profile.setSoundOn(false);
        		else
        			profile.setSoundOn(true);
			}
		});
		musicButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
        		
        		if (profile.isMusicOn())
        		{
        			profile.setMusicOn(false);
        			gameBGM.setVolume(0f);
        			gameBGM.stop();
        		}
        		else
        		{
        			profile.setMusicOn(true);
        			gameBGM.setVolume(1f);
        			gameBGM.play();
        			gameBGM.setLooping(true);
        		}
			}
		});
		
		if (!profile.isSoundOn())
			soundButton.setChecked(true);
		else
			soundButton.setChecked(false);
		
		if (!profile.isMusicOn())
			musicButton.setChecked(true);
		else
			musicButton.setChecked(false);
		
		//howto
		okButton = new Button(skin, "btnOk");
		okButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				tableSwitch = false;
				if (showButton.isChecked())
					profile.setHowto(false);
				world.setState(WorldState.GAMEON);
			}
		});
		showButton = new Button(skin, "btnShow");
	
		texture = new Texture(new ManagedPixmapTextureData(pixmap, pixmap.getFormat(), false)) {
			@Override
			public void dispose() {
				super.dispose();
				getTextureData().consumePixmap().dispose();
			}
		};
		
		dialogBackground = new Image(texture);
		dialogBackground.setPosition(-427,-300);
		dialogBackground.setSize(854, 600);
		dialogBackground.setColor(1f, 1f, 1f, 0.4f);
		
		transitionBg = new Image(texture);
		transitionBg.setPosition(-427,-300);
		transitionBg.setSize(854, 600);
		transitionBg.setColor(1f,1f,1f,0f);
		
		//gameover
		gameoverTable = new Table();
		gameoverTable.setPosition(-VIRTUALWIDTH*0.5f, VIRTUALHEIGHT*0.5f);
		gameoverTable.setWidth(VIRTUALWIDTH);
		gameoverTable.setHeight(VIRTUALHEIGHT);
		dialogH = new DialogHelper("", skin, "default-gameover");
		
		dialogH.button("btnPlay", new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				EscapeJoe.setFirstLaunch(false);
				if (profile.getChoice() == 1)
					profile.setDegree(MathUtils.round(Gdx.input.getRoll()));
				optionGO = 1;
				gameoverTable.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
			}
		});

		dialogH.button("btnQuit", new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				EscapeJoe.setFirstLaunch(false);
				optionGO = 2;
				gameoverTable.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
			}
		});
		
		gameoverTable.add(dialogH).expand();
		
		//howto
		howtoTable = new Table();
		howtoTable.setPosition(-VIRTUALWIDTH*0.5f, -VIRTUALHEIGHT*0.5f);
		howtoTable.setWidth(VIRTUALWIDTH);
		howtoTable.setHeight(VIRTUALHEIGHT);
		howtoDial = new SettingsHelper(skin);
		howtoDial.setBackground("dialoghow");
		
		howtoDial.add(showButton).expand().left().bottom().padLeft(VIRTUALWIDTH*0.07f).padBottom(30f);
		howtoDial.add(okButton).expand().right().bottom().padRight(VIRTUALWIDTH*0.07f).padBottom(10f);
		howtoTable.add(howtoDial).expand();
		
		
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		//delta = Gdx.graphics.getDeltaTime();
		//Gdx.app.log(EscapeJoe.LOG, String.valueOf(delta));
		
		switch (world.getState()) 
		{
			case GAMEHOW:

				world.update(delta);
				worldRenderer.render(delta);
				
				if(!tableSwitch) {
					stage.clear();
					stage.addActor(howtoTable);
					tableSwitch = true;
				}
				stage.act(delta);
				stage.draw();
				
				float tmp = howtoH.getStateTime();
		    	tmp += delta;
		    	howtoH.setStateTime(tmp);
		    	howtoH.setCurrentFrame(howtoH.getExplosionAnimation().getKeyFrame(howtoH.getStateTime(), true));
		    	EscapeJoe.batch.begin();
		    	EscapeJoe.batch.draw(howtoH.getCurrentFrame(), -200f, -96f);
		    	EscapeJoe.batch.end();
				
				break;
				
			case GAMEON:
		
				world.update(delta);
				worldRenderer.render(delta);
				
				if(!tableSwitch) {
					stage.clear();
					stage.addActor(scoreTable);
					stage.addActor(pauseButton);
					pauseButton.setX(-VIRTUALWIDTH*0.49f);
					pauseButton.setY(VIRTUALHEIGHT*0.39f);
					pauseButton.toFront();
					gameBGM.setVolume(1f);
					tableSwitch = true;
					
					
					EscapeJoe.adControl.showAds(false);
					showads = false;
				}
				scoreDisplay.setText(world.getScoreString());
				stage.act(delta);
		        stage.draw();
		       
				break;
				
			case GAMEOVER:

				if (!showads)
				{
					EscapeJoe.adControl.showAds(true);
					showads = true;
				}
				
				world.update(delta);
				worldRenderer.render(delta);
				
				if(!tableSwitch2) {
					
					if (profile.isSoundOn())
					{
						long id = explosion.play();
						explosion.setVolume(id, 1f);
					}
					
					stage.clear();
					profile.notifyScore(world.getScore());
					profile.setPoints(profile.getPoints()+world.getScore());
					
					profile.getLastScores().put(2, profile.getLastScores().get(1));
					profile.getLastScores().put(1, profile.getLastScores().get(0));
					profile.getLastScores().put(0, world.getScore());
					
					checkUnlock();
					
					//System.out.println(profile.getLastScores().get(0) + "-" + profile.getLastScores().get(1) + "-" + profile.getLastScores().get(2));
					
					dialogH.score(world.getScoreString());
					dialogH.bestScore(String.valueOf(profile.getHighScore()));
					
					
					
					if (profile.isFirstPost())
					{
						if (EscapeJoe.actionResolver.getSignedInGPGS())
						{
							EscapeJoe.actionResolver.submitScoreGPGS(profile.getHighScore());
							profile.setFirstPost(false);
						}
					}
					else
					{
						if (EscapeJoe.actionResolver.getSignedInGPGS())
							EscapeJoe.actionResolver.submitScoreGPGS(world.getScore());
					}
					
					
					stage.addActor(gameoverTable);
					//effet bounce
					gameoverTable.addAction(Actions.sequence(Actions.delay(1f), Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
					tableSwitch2 = true;
				}
				
				
				if (gameoverTable.getY() > VIRTUALHEIGHT*0.5f - 10f && optionGO == 1)
				{
					//gameBGM.setVolume(0f);
					//gameBGM.stop();
					stage.addActor(transitionBg);
					EscapeJoe.adControl.showAds(false);
					transitionBg.addAction(Actions.sequence(Actions.alpha(1, 0.15f), Actions.delay(0.1f), new Action() {
						
						@Override
						public boolean act(float delta) {
							// TODO Auto-generated method stub
							game.setScreen(new GameScreen(game));
							return true;
						}
					}));
				}
				else if (gameoverTable.getY() > VIRTUALHEIGHT*0.5f - 10f && optionGO == 2)
				{
					gameBGM.setVolume(0f);
					gameBGM.stop();
					stage.addActor(transitionBg);
					EscapeJoe.adControl.showAds(false);
					transitionBg.addAction(Actions.sequence(Actions.alpha(1f, 0.15f), Actions.delay(0.1f), 
					new Action() {
						
						@Override
						public boolean act(float delta) {
							game.setScreen(new MenuScreen(game));
							return true;
						}
					}));
				}
				
				stage.act(delta);
				stage.draw();

				break;
				
			case PAUSE:
				if (!showads)
				{
					EscapeJoe.adControl.showAds(true);
					showads = true;
				}
				
				worldRenderer.render(delta);
				
				if(!tableSwitch) {
					stage.clear();
					// add actors au stage
					
					stage.addActor(soundButton);
					stage.addActor(musicButton);
					stage.addActor(scoreTable);
					stage.addActor(resumeButton);
					stage.addActor(quitButton);
					stage.addActor(labelPause);
					stage.addActor(dialogBackground);
					
					//initialisation					
					soundButton.setPosition(-VIRTUALWIDTH * 0.41f, VIRTUALHEIGHT * 0.35f); 
					musicButton.setPosition(-VIRTUALWIDTH * 0.49f, VIRTUALHEIGHT * 0.35f);
					
					resumeButton.setPosition(0, -VIRTUALHEIGHT*0.45f);
					quitButton.setPosition(0, -VIRTUALHEIGHT*0.45f);
					
					//transitions
					resumeButton.addAction(Actions.sequence(Actions.alpha(0), Actions.moveBy(-VIRTUALWIDTH*0.5f-304f, 0f), Actions.alpha(1), Actions.moveBy(304f, 0, 0.5f, Interpolation.circle)));
					quitButton.addAction(Actions.sequence(Actions.alpha(0), Actions.moveBy(+VIRTUALWIDTH*0.5f, 0f), Actions.alpha(1), Actions.moveBy(-304f, 0, 0.5f, Interpolation.circle)));
					labelPause.setPosition(-VIRTUALWIDTH*0.23f, -VIRTUALHEIGHT*0.05f);

					scoreTable.toBack();
					dialogBackground.setZIndex(1);
					labelPause.toFront();
					
					resumeButton.addListener(new ClickListener() {
						@Override
						public void clicked(InputEvent event, float x, float y) {
							// TODO Auto-generated method stub
							super.clicked(event, x, y);
							
							if (resumeButton.getX() >= -VIRTUALWIDTH*0.5f - 4f)
							{
								quitButton.addAction(Actions.sequence(Actions.moveBy(304f, 0, 0.5f, Interpolation.circle)));
								resumeButton.addAction(Actions.sequence(Actions.moveBy(-304f, 0, 0.5f, Interpolation.circle),
								new Action() {
									@Override
									public boolean act(float delta) {
										tableSwitch = false;
										world.setUnpauseTime(TimeUtils.nanoTime());
										world.setState(WorldState.GAMEON);
										return true;
									}
								}));
							}
								
						}
					});
					quitButton.addListener(new ClickListener() {
						@Override
						public void clicked(InputEvent event, float x, float y) {
							// TODO Auto-generated method stub
							super.clicked(event, x, y);
							EscapeJoe.setFirstLaunch(false);
							optionGO = 3;
							resumeButton.addAction(Actions.sequence(Actions.moveBy(-304f, 0, 0.5f, Interpolation.circle)));
							quitButton.addAction(Actions.sequence(Actions.moveBy(304f, 0, 0.5f, Interpolation.circle)));
							
						}
					});
					
					tableSwitch = true;
				}
				
				if (resumeButton.getX() < -VIRTUALWIDTH*0.5f - 303f && optionGO == 3)
				{
					gameBGM.setVolume(0f);
					gameBGM.stop();
					stage.addActor(transitionBg);
					
					EscapeJoe.adControl.showAds(false);
					transitionBg.addAction(Actions.sequence(Actions.alpha(1f, 0.15f), Actions.delay(0.1f), 
					new Action() {
						
						@Override
						public boolean act(float delta) {
							profile.getLastScores().put(2, profile.getLastScores().get(1));
							profile.getLastScores().put(1, profile.getLastScores().get(0));
							profile.getLastScores().put(0, world.getScore());
							
							game.setScreen(new MenuScreen(game));
							return true;
						}
					}));
				}
					
				stage.act(delta);
				stage.draw();
				break;
	
			default:
				break;
		}
	}
	
	@Override
	public void hide()
	{
		super.hide();
	}
	@Override
	public void dispose()
	{
		super.dispose();
		texture.dispose();
		worldRenderer.dispose();
	}
	
	@Override
	public void pause()
	{
		super.pause();
		if (!(world.getState() == WorldState.PAUSE || world.getState() == WorldState.GAMEHOW))
			world.setPauseTime(TimeUtils.nanoTime());
	}
	
	@Override
	public void resume()
	{
		super.resume();
		tableSwitch = false;
		if (!(world.getState() == WorldState.GAMEOVER || world.getState() == WorldState.GAMEHOW))
			world.setState(WorldState.PAUSE);
	}
	
	public void checkUnlock() {
		
		//--------TETES
		//-- Street
		if (world.getScore() >= 100)
		{
			profile.getAvailableJoe().put(1, true);
			
			//if (EscapeJoe.actionResolver.getSignedInGPGS())
				//EscapeJoe.actionResolver.unlockAchievementGPGS("CgkIu4-wo74aEAIQAg");
		}
		
		//--Muchajoe
		if (world.getScore() >= 150)
		{
			profile.getAvailableJoe().put(2,true);
			//if (EscapeJoe.actionResolver.getSignedInGPGS())
				//EscapeJoe.actionResolver.unlockAchievementGPGS("CgkIu4-wo74aEAIQAw");
		}
		
		//--piratejoe
		if (world.getScore() >= 225)
		{
			profile.getAvailableJoe().put(3, true);
			//if (EscapeJoe.actionResolver.getSignedInGPGS())
				//EscapeJoe.actionResolver.unlockAchievementGPGS("CgkIu4-wo74aEAIQBA");
		}
		
		//--bunnyjoe
		if (world.getScore() >= 300)
		{
			profile.getAvailableJoe().put(4,  true);
			//if (EscapeJoe.actionResolver.getSignedInGPGS())
				//EscapeJoe.actionResolver.unlockAchievementGPGS("CgkIu4-wo74aEAIQBQ");
		}
		
		//--joecahontas
		if (world.getScore() >= 60)
		{
			profile.getAvailableJoe().put(5,  true);
			//if (EscapeJoe.actionResolver.getSignedInGPGS())
				//EscapeJoe.actionResolver.unlockAchievementGPGS("CgkIu4-wo74aEAIQBQ");
		}
		
		//----------SHIP
		//--submarine
		if (profile.getLastScores().get(0) >= 100 && profile.getLastScores().get(1) >= 100 && profile.getLastScores().get(2) >= 100)
		{
			profile.getAvailableShip().put(1, true);
			//if (EscapeJoe.actionResolver.getSignedInGPGS())
				//EscapeJoe.actionResolver.unlockAchievementGPGS("CgkIu4-wo74aEAIQBg");
		}
		
		//--pirateship
		if (profile.getLastScores().get(0) >= 175 && profile.getLastScores().get(1) >= 175 && profile.getLastScores().get(2) >= 175)
		{
			profile.getAvailableShip().put(2, true);
			//if (EscapeJoe.actionResolver.getSignedInGPGS())
				//EscapeJoe.actionResolver.unlockAchievementGPGS("CgkIu4-wo74aEAIQCQ");
		}
		
		//--carotteship
		if (profile.getLastScores().get(0) >= 225 && profile.getLastScores().get(1) >= 225 && profile.getLastScores().get(2) >= 225)
		{
			profile.getAvailableShip().put(3, true);
			//if (EscapeJoe.actionResolver.getSignedInGPGS())
				//EscapeJoe.actionResolver.unlockAchievementGPGS("CgkIu4-wo74aEAIQCg");
		}
		
		//--cloudyship
		if (profile.getLastScores().get(0) >= 50 && profile.getLastScores().get(1) >= 50 && profile.getLastScores().get(2) >= 50)
		{
			profile.getAvailableShip().put(4, true);
			//if (EscapeJoe.actionResolver.getSignedInGPGS())
				//EscapeJoe.actionResolver.unlockAchievementGPGS("CgkIu4-wo74aEAIQCg");
		}
	}

}
