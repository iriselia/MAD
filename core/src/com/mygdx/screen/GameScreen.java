package com.mygdx.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Source;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Target;
import com.mygdx.game.Assets;


public class GameScreen implements Screen {

    static final int WORLD_WIDTH = 100;
    static final int WORLD_HEIGHT = 100;
    
    public GameScreen() {
        stage = new Stage();
        camera = new OrthographicCamera();
    }    
    
	private OrthographicCamera camera;

    private SpriteBatch batch;

    private Sprite mapSprite;
    
    private CameraController controller;
    GestureDetector gestureDetector;
    
    private Stage stage;
    
    private Table table;
    
	class CameraController implements GestureListener {
		float velX, velY;
		boolean flinging = false;
		float initialScale = 1;
		
		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

		private void keepCameraWithinViewport() {
		    //camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);
		    //camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, 100 - effectiveViewportWidth / 2f);
		    //camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, 100 - effectiveViewportHeight / 2f);
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
		Assets.loadMainMenuOrSettings();

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
        
        //trial
        Assets.skin.add("badlogic", new Texture("simple.png"));
		Image sourceImage = new Image(Assets.skin, "badlogic");
		sourceImage.setBounds(50, 125, 100, 100);
		stage.addActor(sourceImage);
		
		Image validTargetImage = new Image(Assets.skin, "badlogic");
		validTargetImage.setBounds(200, 50, 100, 100);
		stage.addActor(validTargetImage);
		
		Image invalidTargetImage = new Image(Assets.skin, "badlogic");
		invalidTargetImage.setBounds(200, 200, 100, 100);
		stage.addActor(invalidTargetImage);
		
		DragAndDrop dragAndDrop = new DragAndDrop();
		dragAndDrop.addSource(new Source(sourceImage) {
			public Payload dragStart (InputEvent event, float x, float y, int pointer) {
				Payload payload = new Payload();
				payload.setObject("Some payload!");

				payload.setDragActor(new Label("Some payload!", Assets.skin));

				Label validLabel = new Label("Some payload!", Assets.skin);
				validLabel.setColor(0, 1, 0, 1);
				payload.setValidDragActor(validLabel);

				Label invalidLabel = new Label("Some payload!", Assets.skin);
				invalidLabel.setColor(1, 0, 0, 1);
				payload.setInvalidDragActor(invalidLabel);

				return payload;
			}
		});
		dragAndDrop.addTarget(new Target(validTargetImage) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				getActor().setColor(Color.GREEN);
				return true;
			}

			public void reset (Source source, Payload payload) {
				getActor().setColor(Color.WHITE);
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
				System.out.println("Accepted: " + payload.getObject() + " " + x + ", " + y);
			}
		});
		dragAndDrop.addTarget(new Target(invalidTargetImage) {
			public boolean drag (Source source, Payload payload, float x, float y, int pointer) {
				getActor().setColor(Color.RED);
				return false;
			}

			public void reset (Source source, Payload payload) {
				getActor().setColor(Color.WHITE);
			}

			public void drop (Source source, Payload payload, float x, float y, int pointer) {
			}
		});
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
		
		TextButton btnReturn = new TextButton("Return", Assets.skin);
		btnReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                //game.setScreen(new MainMenu());
                dispose();
            }
        });
		btnReturn.pad(10);
		
		TextButton btnQuit = new TextButton("Quit", Assets.skin);
		btnQuit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                dispose();
            }
        });
		btnQuit.pad(10);
		
		//buttonTexture = new Texture(Gdx.files.internal("hexTiles/testTile.png"));
		
		
	    table.add(btnReturn).expand().top().left(); // Sized to cell horizontally.
	    table.add(btnQuit).expand().top().right();
	    stage.addActor(table);
	    //table.row();
	    //table.add(addressLabel);
	    //table.add(addressText).width(100);
	}

}
