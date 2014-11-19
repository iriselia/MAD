package com.mygdx.screen;

import hanto.studentpren.HantoGameFactory;
import hanto.util.HantoGameID;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Hanto;

public class GameScreen implements Screen {
	final Hanto game;
	Stage stage;
	TextureAtlas atlas;
	Skin skin;
	Table table;
    OrthographicCamera camera;
    Texture buttonTexture;
    
    public GameScreen(final Hanto gam) {
        this.game = gam;
        stage = new Stage();
        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        game.gameInstance = HantoGameFactory.getInstance().makeHantoGame(HantoGameID.DELTA_HANTO);

        stage.act(delta);
        stage.draw();
        game.batch.begin();
		game.batch.draw(buttonTexture, 360, 640);
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {

		stage = new Stage();
		
		Gdx.input.setInputProcessor(stage);
		//atlas = new TextureAtlas(Gdx.files.internal("data/uiskin.json"));
		skin = new Skin(Gdx.files.internal("data/uiskin.json"));
		table = new Table(skin);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		//textButtonStyle.disabled = skin.getDrawable("ok_disable");
		//textButtonStyle.over = skin.getDrawable("ok_hover");
		textButtonStyle.up = skin.getDrawable("default-rect");
		textButtonStyle.down = skin.getDrawable("default-rect-down");
		textButtonStyle.pressedOffsetX = 1;
		textButtonStyle.pressedOffsetY = -1;
		//generate font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/ManilaSansBld.otf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size =30;
		BitmapFont buttonFont = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
		textButtonStyle.font = buttonFont;
		LabelStyle labelStyle = new LabelStyle(buttonFont, Color.BLACK);
		// label "title"
        Label titleLabel = new Label( "Game Screen", labelStyle);
		
		TextButton btnReturn = new TextButton("Return to Main Menu", textButtonStyle);
		btnReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
		TextButton btnQuit = new TextButton("Quit Game", textButtonStyle);
		btnQuit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.gameInstance = null;
                game.setScreen(new MainMenuScreen(game));
                dispose();
            }
        });
		
		buttonTexture = new Texture(Gdx.files.internal("hexTiles/tileAutumn_tile.png"));
		
		
	    table.add(titleLabel).width(500).expand().fillX(); // Sized to cell horizontally.
	    //table.add(nameText).top();
	    table.row();
	    table.add(btnReturn).left();
	    table.add(btnQuit).right();
	    table.debug();
		stage.addActor(table);
	    //table.add(addressText).width(100);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
