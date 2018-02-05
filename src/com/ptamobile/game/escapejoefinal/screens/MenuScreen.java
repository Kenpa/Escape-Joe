package com.ptamobile.game.escapejoefinal.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ptamobile.game.escapejoefinal.EscapeJoe;
import com.ptamobile.game.escapejoefinal.model.Profile;
import com.ptamobile.game.escapejoefinal.services.ManagedPixmapTextureData;
import com.ptamobile.game.escapejoefinal.services.ShopHelper;
import com.ptamobile.game.escapejoefinal.tween.ImageAccessor;

public class MenuScreen extends AbstractScreen {
	
	private static final float VIRTUALWIDTH = EscapeJoe.virtualViewport.getVirtualWidth();
	private static final float VIRTUALHEIGHT = EscapeJoe.virtualViewport.getVirtualHeight();
	
	private Music menuBGM;

	private Button btnPlay, btnLead, btnMusic, btnSound, btnSettings, btnSignIn, btnSignOut, btnFb, btnRate, btnPrev, btnNext, btnValide, btnUnlock, btnJoe, btnShip, btnPurchase, btnShopQuit, btn5000, btn10000, btn25000;
	private ButtonGroup shopChoice;
	private Image joe, bgMenu, dialogBackground, locked, unlocked, checkTrue1, checkTrue2, checkFalse1, checkFalse2, t_wesh, t_pirate, t_bunny, t_normal, t_eljoe, t_jocahontas, v_cloudy, v_normal, v_carot, v_pirate, v_sousmarin;
	
	private Texture texture;
	private Pixmap pixmap;
	
	private Label points, skinName, objectif;
	
	private ShopHelper shopH;
	private Table settingsTable, labelTable;
	
	private Boolean settingsOn, tableSwitch, firstTime, purchaseOn;
	private int shopMode, joePos, shipPos;
	
	private Profile profile;
	
	public MenuScreen(EscapeJoe game) 
	{
		super(game);
		
		pixmap =  new Pixmap(1,1, Format.RGBA8888);
		pixmap.setColor(Color.BLACK);
		pixmap.fillRectangle(0, 0, 1, 1);
		
		InputProcessor backProcessor = new InputAdapter() {
			@Override
			public boolean keyDown(int keycode) {
				// TODO Auto-generated method stub
				
				if(keycode == Keys.BACK) {
					
					if (settingsOn)
					{
		    			btnPrev.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			btnNext.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			btnJoe.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			btnShip.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			btnPurchase.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			btnShopQuit.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			locked.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			unlocked.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			checkTrue1.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			checkFalse1.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			checkTrue2.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			checkFalse2.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		        		
		    			btn5000.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			btn10000.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			btn25000.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
		    			
		    			settingsTable.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2),
		    				new Action() {
								
								@Override
								public boolean act(float delta) {
									// TODO Auto-generated method stub
					        		settingsTable.remove();
					        		dialogBackground.remove();
					        		btnNext.remove();
					        		btnPrev.remove();
					        		btnShip.remove();
					        		btnJoe.remove();
					        		btnPurchase.remove();
					        		btnShopQuit.remove();
					        		locked.remove();
					        		unlocked.remove();
					        		checkTrue1.remove();
					        		checkTrue2.remove();
					        		checkFalse1.remove();
					        		checkFalse2.remove();
					        		
					        		btn5000.remove();
					        		btn10000.remove();
					        		btn25000.remove();
					        		
					        		tableSwitch = true;
					        		settingsOn = false;
									return true;
							}
						}));
					}
					else
					{
						Gdx.app.exit();
					}
				}
				return super.keyDown(keycode);
			}
		};
		
		multiplexer.addProcessor(backProcessor);
		Gdx.input.setInputProcessor(multiplexer);
		
		firstTime = true;
		settingsOn = false;
		tableSwitch = false;
		
		shopMode = 1;
		purchaseOn = false;
		
	}
	
	@Override
	public void show() 
	{	
		super.show();
		
		EscapeJoe.adControl.showAds(false);
		
		profile = EscapeJoe.profileManager.retrieveProfile();
		
		joePos = profile.getJoeskin();
		shipPos = profile.getShipskin();
		
		if(!Gdx.input.isPeripheralAvailable(Peripheral.Compass))
			profile.setChoice(2);
		else
			profile.setChoice(1);
		
		menuBGM = EscapeJoe.assets.get("sounds/menuBGM.ogg", Music.class);
		
		if (profile.isMusicOn())
		{
			menuBGM.setVolume(1f);
			menuBGM.play();
			menuBGM.setLooping(true);
		}
		else
		{	
			menuBGM.setVolume(0f);
			menuBGM.stop();
		}
		
		TextureAtlas atlas = EscapeJoe.assets.get("images/menuAtlas.pack", TextureAtlas.class);
		Skin skin = EscapeJoe.assets.get("ui/menu.json", Skin.class);
		TextureAtlas atlas2 = EscapeJoe.assets.get("images/dialogAtlas.pack", TextureAtlas.class);
		Skin skin2 = EscapeJoe.assets.get("ui/dialog.json", Skin.class);

		bgMenu = new Image(atlas.findRegion("fond"));
		bgMenu.setPosition(-427f, -300f);	
				
		joe = new Image(atlas.findRegion("joe"));
		joe.setPosition(-VIRTUALWIDTH * 0.45f, - VIRTUALHEIGHT*0.4f);
		
		t_wesh = new Image(atlas2.findRegion("t_wesh"));
		t_bunny = new Image(atlas2.findRegion("t_bunny"));
		t_normal = new Image(atlas2.findRegion("t_normal"));
		t_pirate = new Image(atlas2.findRegion("t_pirate"));
		t_eljoe = new Image(atlas2.findRegion("t_eljoe"));
		t_jocahontas = new Image(atlas2.findRegion("t_jocahontas"));
		
		v_normal = new Image(atlas2.findRegion("v_normal"));
		v_sousmarin = new Image(atlas2.findRegion("v_sousmarin"));
		v_carot = new Image(atlas2.findRegion("v_carot"));
		v_pirate = new Image(atlas2.findRegion("v_pirate"));
		v_cloudy = new Image(atlas2.findRegion("v_cloudy"));
		
		locked = new Image(atlas2.findRegion("locked"));
		locked.setPosition(-170f, VIRTUALHEIGHT - 107f);
		unlocked = new Image(atlas2.findRegion("unlocked"));
		unlocked.setPosition(-180f, VIRTUALHEIGHT -107f);
		
		checkTrue1 = new Image(atlas2.findRegion("checktrue"));
		checkTrue1.setPosition(-284f,  VIRTUALHEIGHT + 178f);
		checkFalse1 = new Image(atlas2.findRegion("checkfalse"));
		checkFalse1.setPosition(-284f,  VIRTUALHEIGHT + 178f);
		checkTrue2 = new Image(atlas2.findRegion("checktrue"));
		checkTrue2.setPosition(-77f,  VIRTUALHEIGHT + 178f);
		checkFalse2 = new Image(atlas2.findRegion("checkfalse"));
		checkFalse2.setPosition(-77f,  VIRTUALHEIGHT + 178f);
		
        Tween.to(joe, ImageAccessor.POS_XY, 3f).target(- VIRTUALWIDTH * 0.45f, -VIRTUALHEIGHT*0.3f).ease(TweenEquations.easeInOutQuad).repeatYoyo(Tween.INFINITY, 0f).start(tweenManager);
        
        //menuscreen buttons
        btnPlay = new Button(skin, "btnPlay");		
		btnLead = new Button(skin, "btnLead");		
		btnMusic = new Button(skin, "btnMusic");
		btnMusic.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
        		
        		if (profile.isMusicOn())
        		{
        			profile.setMusicOn(false);
        			menuBGM.setVolume(0f);
        			menuBGM.stop();
        		}
        		else
        		{
        			profile.setMusicOn(true);
        			menuBGM.setVolume(1f);
        			menuBGM.play();
        			menuBGM.setLooping(true);
        		}
			}
		});
		btnSound = new Button(skin, "btnSound");
		btnSound.addListener(new ClickListener() {
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
		
		if (!profile.isSoundOn())
			btnSound.setChecked(true);
		else
			btnSound.setChecked(false);
		
		if (!profile.isMusicOn())
			btnMusic.setChecked(true);
		else
			btnMusic.setChecked(false);
		
		btnSignIn = new Button(skin, "btnSignin");
		btnSignOut = new Button(skin, "btnSignout");
		
		btnFb = new Button(skin, "btnFb");
		btnFb.setPosition(-VIRTUALWIDTH * 0.48f, -VIRTUALHEIGHT* 0.5f + 10f);
		btnFb.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				Gdx.net.openURI("http://on.fb.me/1u47JyC");
			}
		});
		btnRate = new Button(skin, "btnRate");
		btnRate.setPosition(-VIRTUALWIDTH * 0.4f, -VIRTUALHEIGHT*0.5f + 10f);
		btnRate.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				Gdx.net.openURI("http://bit.ly/1u3ZToy");
			}
		});
		
		btnSettings = new Button(skin, "btnSettings");
		btnSettings.setPosition(VIRTUALWIDTH*0.41f, VIRTUALHEIGHT*0.35f);
		btnSettings.addListener(new ClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);

				shopMode = 1;
				settingsOn = true;
				tableSwitch = true;
				purchaseOn = false;
				
				btnJoe.setChecked(true);
				shopH.getCells().get(1).setWidget(shopH.getListJoe().get(joePos));
				
				correspLock(shopMode, joePos);
				correspDesc(shopMode, joePos);
				disableSelect(joePos, shipPos);
				
				btnNext.setVisible(true);
				btnNext.setTouchable(Touchable.enabled);
				btnPrev.setVisible(true);
				btnPrev.setTouchable(Touchable.enabled);
				
				btn5000.setVisible(false);
				btn10000.setVisible(false);
				btn25000.setVisible(false);
				btn5000.setTouchable(Touchable.disabled);
				btn10000.setTouchable(Touchable.disabled);
				btn25000.setTouchable(Touchable.disabled);
			}
		});

		btnMusic.setPosition(-VIRTUALWIDTH * 0.4f, VIRTUALHEIGHT * 0.35f); 
		btnSound.setPosition(-VIRTUALWIDTH * 0.48f, VIRTUALHEIGHT * 0.35f);
		
		btnSignIn.setPosition(VIRTUALWIDTH * 0.5f - 175f, -VIRTUALHEIGHT* 0.5f + 10f);
		btnSignOut.setPosition(VIRTUALWIDTH * 0.5f - 175f, -VIRTUALHEIGHT* 0.5f + 10f);
		
		btnSignIn.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				EscapeJoe.actionResolver.loginGPGS();
			}
		});
		btnSignOut.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				EscapeJoe.actionResolver.logoutGPGS();
			}
		});
		
		btnPlay.setPosition(0, - VIRTUALHEIGHT*0.35f);
		btnPlay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				if (profile.getChoice() == 1)
					profile.setDegree(MathUtils.round(Gdx.input.getRoll()));
				
				menuBGM.setVolume(0f);
				menuBGM.stop();
				game.setScreen(new GameScreen(game));
			}
		});
		
		btnLead.setPosition(VIRTUALWIDTH*0.22f, -VIRTUALHEIGHT*0.35f);
		btnLead.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				if (EscapeJoe.actionResolver.getSignedInGPGS())
				{
					EscapeJoe.actionResolver.getLeaderboardGPGS();
				}
				else
				{
					EscapeJoe.actionResolver.loginGPGS();
					
					if (EscapeJoe.actionResolver.getSignedInGPGS())
					{
						EscapeJoe.actionResolver.getLeaderboardGPGS();
					}
				}
			}
		});
		
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
		dialogBackground.setColor(1f, 1f, 1f, 0.7f);
		
	
		//SETTINGS
		settingsTable = new Table();
		settingsTable.setPosition(-VIRTUALWIDTH*0.5f, VIRTUALHEIGHT*0.5f);
		settingsTable.setWidth(VIRTUALWIDTH);
		settingsTable.setHeight(VIRTUALHEIGHT);
		
		labelTable = new Table();
		labelTable.setWidth(260f);
		labelTable.setHeight(160f);
		
		shopH = new ShopHelper(skin2);
		shopH.setBackground("dialog");
		
		//ajout skins
		shopH.getListJoe().add(t_normal);
		shopH.getListJoe().add(t_wesh);
		shopH.getListJoe().add(t_eljoe);
		shopH.getListJoe().add(t_pirate);
		shopH.getListJoe().add(t_bunny);
		shopH.getListJoe().add(t_jocahontas);
		
		shopH.getListVaisseaux().add(v_normal);
		shopH.getListVaisseaux().add(v_sousmarin);
		shopH.getListVaisseaux().add(v_pirate);
		shopH.getListVaisseaux().add(v_carot);
		shopH.getListVaisseaux().add(v_cloudy);
		
		points = new Label("Points : " + String.valueOf(profile.getPoints()), shopH.getNormalStyle());
		
		skinName = new Label("", shopH.getNormalStyle());
		skinName.setWrap(true);
		skinName.setAlignment(Align.center, Align.center);
		//skinName.setWidth(240f);
		
		objectif = new Label("", shopH.getNormalStyle());
		objectif.setWrap(true);
		objectif.setAlignment(Align.center, Align.center);
		//objectif1.setWidth(240f);
		
		btnJoe = new Button(skin2, "btnJoe");
		btnJoe.setPosition(-316f, VIRTUALHEIGHT + 165f);
		btnJoe.addListener(new ClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				shopMode = 1;
				purchaseOn = false;
				shopH.getCells().get(1).setWidget(shopH.getListJoe().get(joePos));
				
				correspLock(shopMode, joePos);
				correspDesc(shopMode, joePos);
				disableSelect(joePos, shipPos);
				
				btn5000.setVisible(false);
				btn10000.setVisible(false);
				btn25000.setVisible(false);
				btn5000.setTouchable(Touchable.disabled);
				btn10000.setTouchable(Touchable.disabled);
				btn25000.setTouchable(Touchable.disabled);
				
				btnNext.setVisible(true);
				btnNext.setTouchable(Touchable.enabled);
				btnPrev.setVisible(true);
				btnPrev.setTouchable(Touchable.enabled);
			}
		});
		btnShip = new Button(skin2, "btnShip");
		btnShip.setPosition(-338f + 236f, VIRTUALHEIGHT + 165f);
		btnShip.addListener(new ClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				shopMode = 2;
				purchaseOn = false;
				shopH.getCells().get(1).setWidget(shopH.getListVaisseaux().get(shipPos));
				
				correspLock(shopMode, shipPos);
				correspDesc(shopMode, shipPos);
				disableSelect(joePos, shipPos);
				
				btn5000.setVisible(false);
				btn10000.setVisible(false);
				btn25000.setVisible(false);
				btn5000.setTouchable(Touchable.disabled);
				btn10000.setTouchable(Touchable.disabled);
				btn25000.setTouchable(Touchable.disabled);
				
				btnNext.setVisible(true);
				btnNext.setTouchable(Touchable.enabled);
				btnPrev.setVisible(true);
				btnPrev.setTouchable(Touchable.enabled);
			}
		});
		
		btnPurchase = new Button(skin2, "btnPurchase");
		btnPurchase.setPosition(106f, VIRTUALHEIGHT + 165f);
		btnPurchase.addListener(new ClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				purchaseOn = true;
				shopH.getCells().get(1).setWidget(null);
				
				btnValide.setVisible(false);
				btnUnlock.setVisible(false);
				btnValide.setTouchable(Touchable.disabled);
				btnUnlock.setTouchable(Touchable.disabled);
				btnNext.setVisible(false);
				btnNext.setTouchable(Touchable.disabled);
				btnPrev.setVisible(false);
				btnPrev.setTouchable(Touchable.disabled);
				
				btn5000.setVisible(true);
				btn10000.setVisible(true);
				btn25000.setVisible(true);
				btn5000.setTouchable(Touchable.enabled);
				btn10000.setTouchable(Touchable.enabled);
				btn25000.setTouchable(Touchable.enabled);
				
				objectif.setText("");
				skinName.setText("");
				
			}
		});
		
        shopChoice = new ButtonGroup(btnJoe, btnShip, btnPurchase);
		
		btnNext = new Button(skin2, "btnnext");
		btnNext.setPosition(-36f, VIRTUALHEIGHT-56f);
		btnNext.addListener(new ClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				if (shopMode == 1)
				{
					
					if (joePos != shopH.getListJoe().size() - 1)
					{
						shopH.getCells().get(1).setWidget(shopH.getListJoe().get(joePos+1));
						joePos = joePos + 1;
					}
					else
					{
						shopH.getCells().get(1).setWidget(shopH.getListJoe().get(0));
						joePos = 0;
					}
					correspLock(shopMode, joePos);
					correspDesc(shopMode, joePos);
					disableSelect(joePos, shipPos);
					
				}
				else
				{
					if (shipPos != shopH.getListVaisseaux().size()-1)
					{
						shopH.getCells().get(1).setWidget(shopH.getListVaisseaux().get(shipPos+1));
						shipPos = shipPos + 1;
					}
					else
					{
						shopH.getCells().get(1).setWidget(shopH.getListVaisseaux().get(0));
						shipPos = 0;
					}
					correspLock(shopMode, shipPos);
					correspDesc(shopMode, shipPos);
					disableSelect(joePos, shipPos);
					
				}
				
			}
		});
		btnPrev = new Button(skin2, "btnprev");
		btnPrev.setPosition(-VIRTUALWIDTH*0.27f -44f, VIRTUALHEIGHT-56f);
		btnPrev.addListener(new ClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				if (shopMode == 1)
				{
					if (joePos != 0)
					{
						shopH.getCells().get(1).setWidget(shopH.getListJoe().get(joePos-1));
						joePos = joePos - 1;
					}
					else
					{
						shopH.getCells().get(1).setWidget(shopH.getListJoe().get(shopH.getListJoe().size()-1));
						joePos = shopH.getListJoe().size()-1;
					}
					correspLock(shopMode, joePos);
					correspDesc(shopMode, joePos);
					disableSelect(joePos, shipPos);
				}
				else
				{
					if (shipPos != 0)
					{
						shopH.getCells().get(1).setWidget(shopH.getListVaisseaux().get(shipPos-1));
						shipPos = shipPos - 1;
					}
					else
					{
						shopH.getCells().get(1).setWidget(shopH.getListVaisseaux().get(shopH.getListVaisseaux().size()-1));
						shipPos = shopH.getListVaisseaux().size()-1;
					}
					correspLock(shopMode, shipPos);
					correspDesc(shopMode, shipPos);
					disableSelect(joePos, shipPos);
					
				}
				
			}
		});
		btnShopQuit = new Button(skin2, "btnShopQuit");
		btnShopQuit.setPosition(-36f+272f, VIRTUALHEIGHT+108f);
		btnShopQuit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
    			btnPrev.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btnNext.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btnJoe.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btnShip.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btnPurchase.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btnShopQuit.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			locked.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			unlocked.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			checkTrue1.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			checkFalse1.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			checkTrue2.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			checkFalse2.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			
    			btn5000.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btn10000.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btn25000.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
        		
    			
    			settingsTable.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2),
    				new Action() {
						
						@Override
						public boolean act(float delta) {
							// TODO Auto-generated method stub
			        		settingsTable.remove();
			        		dialogBackground.remove();
			        		btnNext.remove();
			        		btnPrev.remove();
			        		btnShip.remove();
			        		btnPurchase.remove();
			        		btnShopQuit.remove();
			        		btnJoe.remove();
			        		locked.remove();
			        		unlocked.remove();
			        		checkTrue1.remove();
			        		checkTrue2.remove();
			        		checkFalse1.remove();
			        		checkFalse2.remove();
			        		
			        		btn5000.remove();
			        		btn10000.remove();
			        		btn25000.remove();
			        		
			        		tableSwitch = true;
			        		settingsOn = false;
							return true;
					}
				}));
			}
		});
		
		btnUnlock = new Button(skin2, "btnUnlock");
		btnUnlock.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				
				if (shopMode == 1)
				{
					profile.setPoints(profile.getPoints() - correspPrime(shopMode, joePos));
					profile.getAvailableJoe().put(joePos, true);
					
					correspLock(shopMode, joePos);
					correspDesc(shopMode, joePos);
					disableSelect(joePos, shipPos);
				}
				else
				{
					profile.setPoints(profile.getPoints() - correspPrime(shopMode, shipPos));
					profile.getAvailableShip().put(shipPos, true);
					
					correspLock(shopMode, shipPos);
					correspDesc(shopMode, shipPos);
					disableSelect(joePos, shipPos);
				}
				points.setText("Points : " + String.valueOf(profile.getPoints()));
			}
		});
		
		btnValide = new Button(skin2, "btnSelect");
		btnValide.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y) {
        		// TODO Auto-generated method stub
        		super.clicked(event, x, y);	

    			profile.setJoeskin(joePos);
    			profile.setShipskin(shipPos);
    			
    			btnPrev.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btnNext.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btnJoe.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btnPurchase.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btnShip.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btnShopQuit.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			locked.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			unlocked.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			checkTrue1.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			checkFalse1.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			checkTrue2.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			checkFalse2.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
        		
    			btn5000.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btn10000.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			btn25000.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));
    			
    			settingsTable.addAction(Actions.sequence(Actions.moveBy(0f, VIRTUALHEIGHT, 0.75f, Interpolation.pow2),
    				new Action() {
						
						@Override
						public boolean act(float delta) {
							// TODO Auto-generated method stub
			        		settingsTable.remove();
			        		dialogBackground.remove();
			        		btnNext.remove();
			        		btnPrev.remove();
			        		btnShip.remove();
			        		btnJoe.remove();
			        		btnPurchase.remove();
			        		btnShopQuit.remove();
			        		locked.remove();
			        		unlocked.remove();
			        		checkTrue1.remove();
			        		checkTrue2.remove();
			        		checkFalse1.remove();
			        		checkFalse2.remove();
			        		
			        		btn5000.remove();
			        		btn10000.remove();
			        		btn25000.remove();
			        		
			        		tableSwitch = true;
			        		settingsOn = false;
							return true;
					}
				}));
    		}
	    });
		
		btn5000 = new Button(skin2, "btn5000");
		btn5000.setPosition(btnJoe.getX()+70f, btnJoe.getY()-180f);
		btn5000.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				EscapeJoe.iabControl.buyPoints7500();
			}
		});
		btn10000 = new Button(skin2, "btn10000");
		btn10000.setPosition(btnJoe.getX()+70f, btnJoe.getY()-249f);
		btn10000.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				EscapeJoe.iabControl.buyPoints15000();
			}
		});
		btn25000 = new Button(skin2, "btn25000");
		btn25000.setPosition(btnJoe.getX()+70f, btnJoe.getY()-317f);
		btn25000.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				EscapeJoe.iabControl.buyPoints30000();
			}
		});
		
		labelTable.add(skinName).expandX().fill().height(60f);
		labelTable.row();
		labelTable.add(objectif).expand().fill();
		
		shopH.add(points).expandX().colspan(2).bottom().padTop(80f);
		shopH.row();
		shopH.add(shopH.getListJoe().get(joePos)).expand().padLeft(30f);
		shopH.add(labelTable).width(260f).height(160f).padRight(40f);
		shopH.row();
		shopH.add(btnUnlock).padBottom(10f).padRight(36f);
		shopH.add(btnValide).padBottom(10f).padRight(10f);
		
		settingsTable.add(shopH).padTop(26f);
		
		//debug
		//settingsTable.debug();
		//shopH.debug();
		//labelTable.debug();
		
		stage.addActor(bgMenu);
		stage.addActor(btnMusic);
		stage.addActor(btnSound);
		stage.addActor(btnPlay);
		stage.addActor(btnLead);
		stage.addActor(btnSettings);
		stage.addActor(btnSignIn);
		stage.addActor(btnSignOut);
		stage.addActor(btnFb);
		stage.addActor(btnRate);
		stage.addActor(joe);
		joe.toFront();
		bgMenu.toBack();
	}
	
	@Override
	public void resize(int width, int height)
	{
		super.resize(width, height);
	}
	
	@Override
	public void render(float delta)
	{
		super.render(delta);
		
		if (EscapeJoe.actionResolver.getSignedInGPGS())
		{
			btnSignIn.setVisible(false);
			btnSignIn.setTouchable(Touchable.disabled);
			btnSignOut.setVisible(true);
			btnSignOut.setTouchable(Touchable.enabled);
		}
		else
		{
			btnSignOut.setVisible(false);
			btnSignOut.setTouchable(Touchable.disabled);
			btnSignIn.setVisible(true);
			btnSignIn.setTouchable(Touchable.enabled);
		}
		
		
		if (tableSwitch || firstTime) 
		{	
			if (settingsOn)
			{			
				for (int i = 0; i < stage.getActors().size; i++)
				{
					stage.getActors().get(i).setTouchable(Touchable.disabled);
				}
				
				settingsTable.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				btnPrev.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				btnNext.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				btnJoe.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				btnShip.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				btnPurchase.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				btnShopQuit.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				locked.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				unlocked.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				checkTrue1.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				checkTrue2.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				checkFalse1.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				checkFalse2.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				
				btn5000.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				btn10000.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				btn25000.addAction(Actions.sequence(Actions.alpha(1), Actions.moveBy(0f, -VIRTUALHEIGHT, 0.75f, Interpolation.pow2)));	
				
				stage.addActor(dialogBackground);
				stage.addActor(settingsTable);
				stage.addActor(btnPrev);
				stage.addActor(btnNext);
				stage.addActor(btnJoe);
				stage.addActor(btnShopQuit);
				stage.addActor(btnShip);
				stage.addActor(btnPurchase);
				stage.addActor(locked);
				stage.addActor(unlocked);
				stage.addActor(checkFalse1);
				stage.addActor(checkFalse2);
				stage.addActor(checkTrue1);
				stage.addActor(checkTrue2);
				
				stage.addActor(btn5000);
				stage.addActor(btn10000);
				stage.addActor(btn25000);
			}
			
			else
			{
				for (int i = 0; i < stage.getActors().size; i++)
				{
					stage.getActors().get(i).setTouchable(Touchable.enabled);
				}
			}
			firstTime = false;
			tableSwitch = false;
		}
		
		if (settingsOn)
		{
			if (EscapeJoe.isPurchased())
			{
				points.setText("Points : " + String.valueOf(profile.getPoints()));
				EscapeJoe.setPurchased(false);
			}
		}
		
		stage.act(delta);
        stage.draw();
        //settingsTable.drawDebug(stage);
        //shopH.drawDebug(stage);
        //labelTable.drawDebug(stage);
	}
	
	
	@Override
	public void hide()
	{
		super.dispose();
	}
	@Override
	public void dispose() {
		super.dispose();
	}
	
	
	
	
	
	
	
	public void correspDesc(int shopmode, int pos) {
		
		Boolean checkNew, checkNew2;
		checkNew = profile.getAvailableJoe().get(pos);
		checkNew2 = profile.getAvailableShip().get(pos);
		
		String newligne = System.getProperty("line.separator");
		
		if (shopmode == 1)
		{
			switch (pos)
			{
				case 0 :
				{
					skinName.setText("Joe, just Joe");
					
					objectif.setText("");
					break;
				}
				case 1 :
				{
					skinName.setText("Street Joe");
					
					if (checkNew != null)
					{
						if (checkNew)
						{
						objectif.setText("");
						}
						else
						{
							objectif.setText("Over 100");
						}
					}
					else
					{
						profile.getAvailableJoe().put(pos, false);
						correspDesc(shopmode, pos);
					}
						
					break;
				}
				case 2 :
				{
					skinName.setText("El MuchaJoe");
					
					if (checkNew != null)
					{
						if (checkNew)
						{
						objectif.setText("");
						}
						else
						{
							objectif.setText("Over 150 - OR -" + newligne + "Buy it for 7500 pts");
						}
					}
					else
					{
						profile.getAvailableJoe().put(pos, false);
						correspDesc(shopmode, pos);
					}
					break;
				}
				case 3 :
				{
					skinName.setText("Pirate Joe");
					
					if (checkNew != null)
					{
						if (checkNew)
						{
						objectif.setText("");
						}
						else
						{
							objectif.setText("Over 225 - OR -" + newligne + "Buy it for 10000 pts");
						}
					}
					else
					{
						profile.getAvailableJoe().put(pos, false);
						correspDesc(shopmode, pos);
					}
					break;

				}
				case 4 :
				{
					skinName.setText("Bunny Joe");

					if (checkNew != null)
					{
						if (checkNew)
						{
						objectif.setText("");
						}
						else
						{
							objectif.setText("Over 300 - OR -" + newligne + "Buy it for 15000 pts");
						}
					}
					else
					{
						profile.getAvailableJoe().put(pos, false);
						correspDesc(shopmode, pos);
					}
					break;
				}
				case 5 :
				{
					skinName.setText("Joe-cahontas");
					
					if (checkNew != null)
					{
						if (checkNew)
						{
						objectif.setText("");
						}
						else
						{
							objectif.setText("Over 60");
						}
					}
					else
					{
						profile.getAvailableJoe().put(pos, false);
						correspDesc(shopmode, pos);
					}
					break;
				}
				default:
					break;
			}
		}
		else
		{
			switch (pos)
			{
				case 0 :
				{
					
					skinName.setText("Just a normal Ship");
					objectif.setText("");
					break;
				}
				case 1 :
				{
					skinName.setText("Submarine Ship");
					
					if (checkNew2 != null)
					{
						if (checkNew2)
						{
						objectif.setText("");
						}
						else
						{
							objectif.setText("Over 100, 3 times in a Row - OR -" + newligne + "Buy it for 10000 pts");
						}
					}
					else
					{
						profile.getAvailableShip().put(pos, false);
						correspDesc(shopmode, pos);
					}
					break;
				}
				case 2 :
				{
					skinName.setText("Oh Ship ! Pirates !");
					
					if (checkNew2 != null)
					{
						if (checkNew2)
						{
						objectif.setText("");
						}
						else
						{
							objectif.setText("Over 175, 3 times in a Row - OR -" + newligne + "Buy it for 20000 pts");
						}
					}
					else
					{
						profile.getAvailableShip().put(pos, false);
						correspDesc(shopmode, pos);
					}
					break;
				}
				case 3 :
				{
					skinName.setText("Carrot-like Ship");
					
					if (checkNew2 != null)
					{
						if (checkNew2)
						{
						objectif.setText("");
						}
						else
						{
							objectif.setText("Over 225, 3 times in a Row - OR -" + newligne + "Buy it for 25000 pts");
						}
					}
					else
					{
						profile.getAvailableShip().put(pos, false);
						correspDesc(shopmode, pos);
					}
					break;
				}
				case 4 :
				{
					skinName.setText("Cloudy Ship");
					
					if (checkNew2 != null)
					{
						if (checkNew2)
						{
						objectif.setText("");
						}
						else
						{
							objectif.setText("Over 50, 3 times in a Row - OR -" + newligne + "Buy it for 5000 pts");
						}
					}
					else
					{
						profile.getAvailableShip().put(pos, false);
						correspDesc(shopmode, pos);
					}
					break;
				}
				default:
					break;
			}
		}	
	}


	public void correspLock(int shopmode, int pos) {
		
		Boolean checkNew, checkNew2;
		checkNew = profile.getAvailableJoe().get(pos);
		checkNew2 = profile.getAvailableShip().get(pos);
		
		if (shopmode == 1)
		{
			if(checkNew != null)
			{
				if (checkNew)
				{
					locked.setVisible(false);
					unlocked.setVisible(true);
					btnUnlock.setVisible(true);
					btnUnlock.setDisabled(true);
					btnUnlock.setTouchable(Touchable.disabled);
					
					checkTrue1.setVisible(true);
					checkFalse1.setVisible(false);
				}
				else
				{
					locked.setVisible(true);
					unlocked.setVisible(false);
					
					checkTrue1.setVisible(false);
					checkFalse1.setVisible(true);
					
					if (!unlockable(shopmode, pos))
					{		
						btnUnlock.setVisible(true);
						btnUnlock.setDisabled(true);
						btnUnlock.setTouchable(Touchable.disabled);
					}
					else
					{
						btnUnlock.setVisible(true);
						btnUnlock.setDisabled(false);
						btnUnlock.setTouchable(Touchable.enabled);
					}
				}
			}
			else
			{
				profile.getAvailableJoe().put(pos, false);
				//checkStats
				correspLock(shopmode, pos);
			}
		}
		else
		{
			if(checkNew2 != null)
			{
				if (checkNew2)
				{
					locked.setVisible(false);
					unlocked.setVisible(true);
					btnUnlock.setVisible(true);
					btnUnlock.setDisabled(true);
					btnUnlock.setTouchable(Touchable.disabled);
					
				}
				else
				{
					locked.setVisible(true);
					unlocked.setVisible(false);
					
					if (!unlockable(shopmode, pos))
					{		
						btnUnlock.setVisible(true);
						btnUnlock.setDisabled(true);
						btnUnlock.setTouchable(Touchable.disabled);
					}
					else
					{
						btnUnlock.setVisible(true);
						btnUnlock.setDisabled(false);
						btnUnlock.setTouchable(Touchable.enabled);
					}
				}
			}
			else
			{
				profile.getAvailableShip().put(pos, false);
				correspLock(shopmode, pos);
			}
		}
	
	}
	
	public void disableSelect(int joePos, int shipPos) {
		
		Boolean checkNew, checkNew2;
		checkNew = profile.getAvailableJoe().get(joePos);
		checkNew2 = profile.getAvailableShip().get(shipPos);
		
		if(checkNew != null && checkNew2 != null)
		{
			if (checkNew && checkNew2)
			{
				btnValide.setVisible(true);
				btnValide.setDisabled(false);
				btnValide.setTouchable(Touchable.enabled);
			}
			else
			{
				btnValide.setVisible(true);
				btnValide.setDisabled(true);
				btnValide.setTouchable(Touchable.disabled);
			}
			
			if (checkNew) {
				checkTrue1.setVisible(true);
				checkFalse1.setVisible(false);			
			}
			else {
				checkTrue1.setVisible(false);
				checkFalse1.setVisible(true);
			}
			
			if (checkNew2) {
				checkTrue2.setVisible(true);
				checkFalse2.setVisible(false);			
			}
			else {
				checkTrue2.setVisible(false);
				checkFalse2.setVisible(true);
			}
		}
		
	}

	public boolean unlockable(int shopmode, int pos)
	{
		boolean unlockable;
		unlockable = false;
		
		if (profile.getPoints() >= correspPrime(shopmode, pos))
			unlockable = true;
		
		return unlockable;
		
	}
	
	public int correspPrime(int shop, int pos) {
		
		int prime = 0;
		
		if (shop == 1)
		{
			switch (pos)
			{
				case 0 :
				{	
					break;
				}
				case 1 :
				{
					prime = 100000000; //pcq il a pas de prime
					break;
				}
				case 2 :
				{
					prime = 7500;
					break;
				}
				case 3 :
				{
					prime = 10000;
					break;
				}
				case 4 :
				{
					prime = 15000;
					break;
				}
				case 5 :
				{
					prime = 1000000000;
					break;
				}
				default:
					break;
			}
		}
		else
		{
			switch (pos)
			{
				case 0 :
				{
					break;
				}
				case 1 :
				{
					prime = 10000;
					break;
				}
				case 2 :
				{
					prime = 20000;
					break;
				}
				case 3 :
				{
					prime = 25000;
					break;
				}
				case 4 :
				{
					prime = 5000;
					break;
				}

				default:
					break;
			}
		}
		
		
		return prime;
	}
}

