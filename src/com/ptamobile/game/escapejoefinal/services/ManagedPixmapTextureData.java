package com.ptamobile.game.escapejoefinal.services;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;

public class ManagedPixmapTextureData extends PixmapTextureData {
	
	public ManagedPixmapTextureData(Pixmap pixmap, Format format, boolean useMipMaps) {
		// TODO Auto-generated constructor stub
		super(pixmap, format, useMipMaps, false);
	}
	
	@Override
	public boolean isManaged() {
		return true;
	}

}
