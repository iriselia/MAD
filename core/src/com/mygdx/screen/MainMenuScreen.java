package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

public class MainMenuScreen implements Screen {
	final Hanto game;
	Stage stage;
	TextureAtlas atlas;
	Skin skin;
	Table table;

    OrthographicCamera camera;
    // setup the dimensions of the menu buttons
    //private static final float BUTTON_WIDTH = 300f;
    //private static final float BUTTON_HEIGHT = 60f;
    //private static final float BUTTON_SPACING = 10f;
    public MainMenuScreen(final Hanto gam) {
        this.game = gam;
        stage = new Stage();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        stage.act(delta);
        stage.draw();
    }

	@Override
	public void resize(int width, int height) {
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
		parameter.size = 80;
		BitmapFont buttonFont = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
		textButtonStyle.font = buttonFont;
		LabelStyle labelStyle = new LabelStyle(buttonFont, Color.BLACK);
		// label "title"
        Label titleLabel = new Label( "Hanto", labelStyle);
		
		TextButton btnExit = new TextButton("Exit", textButtonStyle);
		TextButton btnStart = new TextButton("New Game", textButtonStyle);
		btnExit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                //System.exit(0);
            }
        });
		btnStart.addListener(new ClickListener() {
			@Override
            public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new GameScreen(game));
				dispose();
            }
		});
		TextButton btnBackToGame = null;
		//if there's a game running, add a return to game button
		if (game.gameInstance != null){
			btnBackToGame = new TextButton("Resume Game", textButtonStyle);
			btnBackToGame.addListener(new ClickListener() {
				@Override
	            public void clicked(InputEvent event, float x, float y) {
					game.setScreen(new GameScreen(game));
					dispose();
	            }
			});
		}
		//table.addActor(btnStart);
		table.row().height(400);
		table.add(titleLabel);
		table.row();
		if (btnBackToGame != null) {
			table.add(btnBackToGame);
			table.row();
		}
		table.add(btnStart);
		table.row();
		table.add(btnExit);
		table.debug();
		stage.addActor(table);		
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