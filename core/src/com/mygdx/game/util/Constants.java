package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
/**
 * Hold constants used for Hanto Game
 * 
 * @author Peng Ren
 *
 */
public class Constants {
    public final static float w = Gdx.graphics.getWidth();
    public final static float h = Gdx.graphics.getHeight();
    public final static float aspectRatio = h / w;
    public final static float WORLD_WIDTH = w * 3;
    public final static float WORLD_HEIGHT = h * 3;
    public final static int TILE_LENGTH = 150;
    public final static int BUTTON_SIZE = (int) (w / 6);
    public final static int numberOfHelpImages = 8;
}
