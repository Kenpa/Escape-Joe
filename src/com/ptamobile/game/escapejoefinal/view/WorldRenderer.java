package com.ptamobile.game.escapejoefinal.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.ptamobile.game.escapejoefinal.EscapeJoe;
import com.ptamobile.game.escapejoefinal.model.Background;
import com.ptamobile.game.escapejoefinal.model.Joe;
import com.ptamobile.game.escapejoefinal.model.Obstacle;
import com.ptamobile.game.escapejoefinal.model.Profile;
import com.ptamobile.game.escapejoefinal.tween.ExplosionAnimator;
import com.ptamobile.game.escapejoefinal.view.World.WorldState;

public class WorldRenderer {

	//ShapeRenderer[] cosmoShape, mineralShape; //astBigShape, astSmShape, satShape;
	private World world;
	private SpriteBatch batch;
	private TextureRegion asteroidBig, asteroidSmall, satellite, cosmonaute, mineral, fusee, plate, joeTexture, shipTexture, hublotTexture;
	private Joe joe;
	private Profile profile;
	
	private Background bg;
	private ExplosionAnimator explosionH;
	
	public WorldRenderer(World world) {
		TextureAtlas atlas = EscapeJoe.assets.get("images/gamescreenAtlasxhd.pack", TextureAtlas.class);
		Skin skin = EscapeJoe.assets.get("ui/game.json", Skin.class);
		
		profile = EscapeJoe.profileManager.retrieveProfile();
		
		this.world = world;
		this.batch = new SpriteBatch();
		asteroidBig = new TextureRegion(atlas.findRegion("asteroidmedium"));
		asteroidSmall = new TextureRegion(atlas.findRegion("asteroidsmall"));
		satellite = new TextureRegion(atlas.findRegion("satellite"));
		cosmonaute = new TextureRegion(atlas.findRegion("cosmonaute"));
		mineral = new TextureRegion(atlas.findRegion("mineral"));
		plate = new TextureRegion(atlas.findRegion("plate"));
		fusee = new TextureRegion(atlas.findRegion("fusee"));
		
		
		joeTexture = new TextureRegion(atlas.findRegion(correspondanceJoe(profile.getJoeskin())));
		shipTexture = new TextureRegion(atlas.findRegion(correspondanceShip(profile.getShipskin())));
		
		
		hublotTexture = new TextureRegion(atlas.findRegion("hublot"));
		bg = world.getBg();
		explosionH = new ExplosionAnimator();
		
/*		joeShape = new ShapeRenderer[5];
		for (int i = 0; i < 5; i++)
		{
			joeShape[i] = new ShapeRenderer();
			joeShape[i].setColor(Color.RED);
		}*/
		
	// shape obstacle
/*		cosmoShape = new ShapeRenderer[12];
		for (int i = 0; i < 12; i++)
		{
			cosmoShape[i] = new ShapeRenderer();
			cosmoShape[i].setColor(Color.PINK);
		}
		mineralShape = new ShapeRenderer[8];
		for (int i = 0; i < 8; i++)
		{
			mineralShape[i] = new ShapeRenderer();
			mineralShape[i].setColor(Color.PINK);
		}
		satShape = new ShapeRenderer[11];
		for (int i = 0; i < 11; i++)
		{
			satShape[i] = new ShapeRenderer();
			satShape[i].setColor(Color.PINK);
		}*/
		
	}
	
	public void render(float delta) {
		joe = world.getJoe();
		
		EscapeJoe.camera.update();
		batch.setProjectionMatrix(EscapeJoe.camera.combined);
		
		batch.begin();
		
		if(!world.getState().equals(WorldState.PAUSE))
			bg.backgroundScrolling(delta);
		bg.getBgScroll().draw(batch);
		
		for (Obstacle obs : world.getActiveObs())
		{
			switch (obs.getType()) 
			{
				case AstBig:
					batch.draw(asteroidBig, obs.getPosition().x, obs.getPosition().y, obs.getWidth()/2, obs.getHeight()/2, obs.getWidth(), obs.getHeight(), 1, 1, obs.getRotation());
					break;
	
				case AstSmall:
					batch.draw(asteroidSmall, obs.getPosition().x, obs.getPosition().y, obs.getWidth()/2, obs.getHeight()/2, obs.getWidth(), obs.getHeight(), 1, 1, obs.getRotation());
					break;
					
				case Satellite:
					batch.draw(satellite, obs.getPosition().x, obs.getPosition().y, obs.getWidth()/2, obs.getHeight()/2, obs.getWidth(), obs.getHeight(), 1, 1, obs.getRotation());
					break;

				case Cosmonaute:
					batch.draw(cosmonaute, obs.getPosition().x, obs.getPosition().y, obs.getWidth()/2, obs.getHeight()/2, obs.getWidth(), obs.getHeight(), 1, 1, obs.getRotation());
					break;
					
				case Fusee:
					batch.draw(fusee, obs.getPosition().x, obs.getPosition().y, obs.getWidth()/2, obs.getHeight()/2, obs.getWidth(), obs.getHeight(), 1, 1, obs.getRotation());
					break;
					
				case Mineral:
					batch.draw(mineral, obs.getPosition().x, obs.getPosition().y, obs.getWidth()/2, obs.getHeight()/2, obs.getWidth(), obs.getHeight(), 1, 1, obs.getRotation());
					break;
					
				case Plate:
					batch.draw(plate, obs.getPosition().x, obs.getPosition().y, obs.getWidth()/2, obs.getHeight()/2, obs.getWidth(), obs.getHeight(), 1, 1, obs.getRotation());
					break;
			}
		}
		if(!world.getState().equals(WorldState.GAMEOVER))
		{
			batch.draw(hublotTexture, joe.getPosition().x + 27f, joe.getPosition().y + 19f , hublotTexture.getRegionWidth()/2, hublotTexture.getRegionHeight()/2, hublotTexture.getRegionWidth(), hublotTexture.getRegionHeight(), 1, 1, joe.getRotation());
			batch.draw(joeTexture, joe.getPosition().x + 34f, joe.getPosition().y + 18f, joeTexture.getRegionWidth()/2, joeTexture.getRegionHeight()/2, joeTexture.getRegionWidth(), joeTexture.getRegionHeight(), 1, 1, joe.getRotation());
			batch.draw(shipTexture, joe.getPosition().x, joe.getPosition().y, joe.getWidth()/2, joe.getHeight()/2, shipTexture.getRegionWidth(), shipTexture.getRegionHeight(), 1, 1, joe.getRotation());
		}
		else {
			float tmp = explosionH.getStateTime();
        	tmp += delta;
        	explosionH.setStateTime(tmp);
        	explosionH.setCurrentFrame(explosionH.getExplosionAnimation().getKeyFrame(explosionH.getStateTime(), false));
        	if (explosionH.getExplosionAnimation().getKeyFrameIndex(explosionH.getStateTime()) != 9)
        		batch.draw(explosionH.getCurrentFrame(), joe.getPosition().x - 50f , joe.getPosition().y-2f);  
		}

		batch.end();
		
/*		// dessiner les shape de joe
		for (int i = 0; i < 5 ; i++)
		{
			joeShape[i].setProjectionMatrix(EscapeJoe.camera.combined);
			joeShape[i].begin(ShapeType.Line);
			joeShape[i].polygon(joe.getPolygons()[i].getTransformedVertices());
			joeShape[i].end();	
		}*/
		
	//dessiner les shape des obstacle
/*		for (Obstacle obs : world.getActiveObs())
		{
			if (obs.getType().equals(ObsType.Mineral))
			{
				for(int i = 0; i < 7 ; i++)
				{
					mineralShape[i].setProjectionMatrix(EscapeJoe.camera.combined);
					mineralShape[i].begin(ShapeType.Line);
					mineralShape[i].polygon(obs.getPolygons()[i].getTransformedVertices());
					mineralShape[i].end();	
				}
			}
			else if (obs.getType().equals(ObsType.Cosmonaute))
			{
				for(int i = 0; i < 4 ; i++)
				{
					cosmoShape[i].setProjectionMatrix(EscapeJoe.camera.combined);
					cosmoShape[i].begin(ShapeType.Line);
					cosmoShape[i].polygon(obs.getPolygons()[i].getTransformedVertices());
					cosmoShape[i].end();	
				}
			}
			else
			{*/
/*				for(int i = 0; i < 11 ; i++)
				{
					satShape[i].setProjectionMatrix(EscapeJoe.camera.combined);
					satShape[i].begin(ShapeType.Line);
					satShape[i].polygon(obs.getPolygons()[i].getTransformedVertices());
					satShape[i].end();	
				}
			}
		}*/
				
	}
	
	public String correspondanceJoe(int pos) {
		
		String imageName = "";
		switch (pos)
		{
		case 0:
		{
			imageName = "t_normal";
			break;
		}
		case 1:
		{
			imageName = "t_wesh";
			break;
		}
		case 2:
		{
			imageName = "t_eljoe";
			break;
		}
		case 3:
		{
			imageName = "t_pirate";
			break;
		}
		case 4:
		{
			imageName = "t_bunny";
			break;
		}
		case 5:
		{
			imageName = "t_jocahontas";
			break;
		}
		default:
			break;
		}
		
		return imageName;
	}
	
	public String correspondanceShip(int pos) {
	
		String imageName = "";
		switch (pos)
		{
		case 0:
		{
			imageName = "v_normal";
			break;
		}
		case 1:
		{
			imageName = "v_sousmarin";
			break;
		}
		case 2:
		{
			imageName = "v_pirate";
			break;
		}
		case 3:
		{
			imageName = "v_carot";
			break;
		}
		case 4:
		{
			imageName = "v_cloudy";
			break;
		}

		default:
			break;
		}
		
		return imageName;
	}
	
	public void dispose() {
		bg.getBgTexture().dispose();
		batch.dispose();
	}

}
