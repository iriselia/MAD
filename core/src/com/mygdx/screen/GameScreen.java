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
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Assets;
import com.mygdx.game.GameController;



public class GameScreen implements Screen {

    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 100;
    
    public GameScreen() {
        stage = new Stage();
        camera = new OrthographicCamera();
        dragAndDrop = new DragAndDrop();
    }    
    
    private Viewport viewport;
    
	private OrthographicCamera camera;

    private SpriteBatch batch;

    private Sprite mapSprite;
    
    private CameraController controller;
    GestureDetector gestureDetector;
    
    private Stage stage;
    
    private Table table;
    
    private DragAndDrop dragAndDrop;
   
    


	@Override
	public void render(float delta) {
		controller.update();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //mapSprite.draw(batch);
        batch.end();
        stage.act(delta);
        stage.draw();
	}

	@Override
	public void show() {
		Assets.loadMainMenuOrSettings();
		Assets.loadPieces();
		
		GameController gameController = new GameController(stage, dragAndDrop);
		
		Group background = new Group();
		background.setBounds(0, 0, Gdx.graphics.getWidth() *2, Gdx.graphics.getHeight() * 2);
		
		Group foreground = new Group();
		foreground.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// Notice the order
		stage.addActor(background);
		stage.addActor(foreground);
		
        //mapSprite = new Sprite(new Texture(Gdx.files.internal("world/map.png")));
        //mapSprite.setPosition(0, 0);
        //mapSprite.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
		Image map = new Image(new Texture(Gdx.files.internal("world/map.png")));
		map.setPosition(0, 0);
		//map.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
        background.addActor(map);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(3000, 3000 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.update();
        
        controller = new CameraController();
        gestureDetector = new GestureDetector(100, 0.5f, 2, 0.15f, controller);
        
        batch = new SpriteBatch();
        addButtons();
        drawPieces();
        
        foreground.addActor(table);
        
        viewport = new FitViewport(camera.viewportWidth, camera.viewportHeight, camera);
        
        stage.setViewport(viewport);
  
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gestureDetector);
        inputMultiplexer.addProcessor(stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        
        gameController.addTouchAndDrag("butterfly");
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
		table = new Table(Assets.menuSkin);
		table.setFillParent(true);
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		TextButton btnReturn = new TextButton("Return", Assets.menuSkin);
		btnReturn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                dispose();
            }
        });
		btnReturn.pad(10);
		
		TextButton btnQuit = new TextButton("Quit", Assets.menuSkin);
		btnQuit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	Gdx.app.exit();
            }
        });
		btnQuit.pad(10);		
		
	    table.add(btnReturn).expand().top().left(); // Sized to cell horizontally.
	    table.add(btnQuit).expand().top().right();
	    //stage.addActor(table);
	}
	
	public void drawPieces() {
//		Hanto.gameInstance = new HantoGameDevelopment();
//		Hanto.gameInstance.initialize(HantoPlayerColor.BLUE);
//		HantoPlayerColor currentPlayer = Hanto.gameInstance.getGameState().getPlayerOnMove();
//		Label lblPlayerColor = new Label (currentPlayer.toString(), Assets.skin);
//		if (!Hanto.gameInstance.getGameState().getBoard().isEmpty()) {
//			//TODO: create function drawPiecesOnBoard
//		}
//		ImageButton butterfly = new ImageButton(pieceSkin, "butterfly");
//		ImageButton crab = new ImageButton(pieceSkin, "crab");
//		ImageButton sparrow = new ImageButton(pieceSkin, "sparrow");
//		table.add(lblPlayerColor).top().center();
//		table.row();
//		table.add(butterfly).expand().bottom().left();
//		table.getCell(butterfly).spaceRight(30);
//		table.add(crab);
//		table.add(sparrow);
		
	}
	
	class CameraController extends GestureAdapter {
		float velX, velY;
		boolean flinging = false;
		float initialScale = 1;
		
		float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

		private void keepCameraWithinViewport() {
		    camera.zoom = MathUtils.clamp(camera.zoom, 1.0f, 100/camera.viewportWidth);
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
			if(!dragAndDrop.isDragging()) {
				velX = camera.zoom * velocityX * 0.5f;
				velY = camera.zoom * velocityY * 0.5f;
			}
			return false;
		}

		@Override
		public boolean pan (float x, float y, float deltaX, float deltaY) {
			if(!dragAndDrop.isDragging()) {
				camera.position.add(-deltaX * camera.zoom * 0.1f, deltaY * camera.zoom * 0.1f, 0);
			}
			return false;
		}

		@Override
		public boolean panStop (float x, float y, int pointer, int button) {
			return false;
		}

		@Override
		public boolean zoom (float originalDistance, float currentDistance) {
			if(!dragAndDrop.isDragging()) {
				float ratio = originalDistance / currentDistance;
				camera.zoom = initialScale * ratio;
				System.out.println(camera.zoom);
			}
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

}
