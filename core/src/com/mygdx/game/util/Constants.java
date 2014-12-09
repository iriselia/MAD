package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;

public class Constants {
    public final static float w = Gdx.graphics.getWidth();
    public final static float h = Gdx.graphics.getHeight();
    public final static float aspectRatio = h / w;
    public final static float WORLD_WIDTH = w * 3;
    public final static float WORLD_HEIGHT = h * 3;
    public final static int TILE_LENGTH = 200;
}
