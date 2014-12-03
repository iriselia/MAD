package com.mygdx.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;


public class GameScreen implements Screen {

    static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;
    
	//final Hanto game;
	//private TextureAtlas atlas;
    //private Texture buttonTexture;
    
    public GameScreen() {
        //this.game = gam;
        stage = new Stage();
        camera = new OrthographicCamera();
		Assets.loadMainMenuOrSettings();
    }    
    
	private OrthographicCamera camera;
	private OrthographicCamera btnCamera;

    private SpriteBatch batch;

    private Sprite mapSprite;
    
    private CameraController controller;
    GestureDetector gestureDetector;
    
    private Stage stage;
    //private Skin skin;
    private Table table;
    
	class CameraController implements GestureListener {
		float velX, velY;
		boolean flinging = false;
		float initialScale = 1;
		
		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

		private void keepCameraWithinViewport() {
		    camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);
		    camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
		    camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
		}
		
		public boolean touchDown (float x, float y, int pointer, int button) {
			flinging = false;
			initialScale = camera.zoom;
			return false;
		}

		@Override
		public boolean tap (float x, float y, int count, int button) {
			return false;
		}

		@Override
		public boolean longPress (float x, float y) {
			return false;
		}

		@Override
		public boolean fling (float velocityX, float velocityY, int button) {
			flinging = true;
			velX = camera.zoom * velocityX * 0.5f;
			velY = camera.zoom * velocityY * 0.5f;
			return false;
		}

		@Override
		public boolean pan (float x, float y, float deltaX, float deltaY) {
			camera.position.add(-deltaX * camera.zoom * 0.1f, deltaY * camera.zoom * 0.1f, 0);
			return false;
		}

		@Override
		public boolean panStop (float x, float y, int pointer, int button) {
			return false;
		}

		@Override
		public boolean zoom (float originalDistance, float currentDistance) {
			float ratio = originalDistance / currentDistance;
			camera.zoom = initialScale * ratio;
			System.out.println(camera.zoom);
			return false;
		}

		@Override
		public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer) {
			return false;
		}

		public void update () {
			if (flinging) {
				velX *= 0.98f;
				velY *= 0.98f;
				camera.position.add(-velX * Gdx.graphics.getDeltaTime() * 0.1f, velY * Gdx.graphics.getDeltaTime() * 0.1f, 0);
				if (Math.abs(velX) < 0.01f) {
					velX = 0;
				}
				if (Math.abs(velY) < 0.01f) {
					velY = 0;
				}
			}
			keepCameraWithinViewport();
		}
	}

	@Override
	public void render(float delta) {

		controller.update();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        mapSprite.draw(batch);
        batch.end();
        stage.act(delta);
        stage.draw();
	}

	@Override
	public void show() {
		
        mapSprite = new Sprite(new Texture(Gdx.files.internal("world/map.png")));
        mapSprite.setPosition(0, 0);
        mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(30, 30 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        
        controller = new CameraController();
        gestureDetector = new GestureDetector(100, 0.5f, 2, 0.15f, controller);
        //Gdx.input.setInputProcessor(gestureDetector);
        
        batch = new SpriteBatch();
        addButtons();
        //InputProcessor inputProcessorOne = new CustomInputProcessorOne();
        //InputProcessor inputProcessorTwo = new CustomInputProcessorTwo();
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
	}
	
	@Override
	public void hide() {
		dispose();
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

	}

	@Override
	public void resize(int width, int height) {
        camera.viewportWidth = 30f;
        camera.viewportHeight = 30f * height/width;
        camera.update();
		
	}
	
	public void addButtons() {
		//Gdx.input.setInputProcessor(stage);
		table = new Table(Assets.skin);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //Label titleLabel = new Label( "Game Screen", Assets.skin);
		
		TextButton btnReturn = new TextButton("Return to Main Menu", Assets.skin);
		btnReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                //game.setScreen(new MainMenu());
                dispose();
            }
        });
		btnReturn.pad(50);
		TextButton btnQuit = new TextButton("Quit Game", Assets.skin);
		btnQuit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	//game.gameInstance = null;
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                dispose();
            }
        });
		btnQuit.pad(50);
		
		//buttonTexture = new Texture(Gdx.files.internal("hexTiles/testTile.png"));
		
		
	    //table.add(titleLabel).width(500).expand().fillX(); // Sized to cell horizontally.
	    //table.add(nameText).top();
	    //table.row();
	    table.add(btnReturn).left();
	    table.add(btnQuit).right();
	    table.debug();
		stage.addActor(table);
	}

}
