package com.mygdx.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mygdx.game.Assets;
import com.mygdx.game.util.GameController;
import com.mygdx.game.util.CameraController;



public class GameScreen implements Screen {

    //public static final int WORLD_WIDTH = 2000;
    //public static final int WORLD_HEIGHT = 2000;
    public final static float w = Gdx.graphics.getWidth();
    public final static float h = Gdx.graphics.getHeight();
    public final static float aspectRatio = h / w;
    public final static float WORLD_WIDTH = w * 3;
    public final static float WORLD_HEIGHT = h * 3;
    
    public GameScreen() {
        camera = new OrthographicCamera();
        gameStage = new Stage();
    }    
    
	private OrthographicCamera camera;

    private CameraController camController;
    GestureDetector gestureDetector;
    
    private Stage gameStage;
    
    private Table table;
    
    private ImageButton sourceImage;
    
    float last;

	@Override
	public void render(float delta) {
		camController.update();
        camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float left = gameStage.getCamera().position.x - w / 2;
		float bottom = gameStage.getCamera().position.y - h / 2;
				
		sourceImage.setBounds(left, bottom, 200, 200);
		table.setBounds(left, bottom, w, h);
		
        gameStage.act(delta);
        gameStage.draw();
	}

	@Override
	public void show() {
		Assets.loadMainMenuOrSettings();
		Assets.loadPieces();
						
		Image map = new Image(new Texture(Gdx.files.internal("world/background.png")));
		map.setPosition(0, 0);
		map.setSize(WORLD_WIDTH, WORLD_HEIGHT);
		
        gameStage.addActor(map);

        //camera = new OrthographicCamera(40, 40 * (h / w));
        camera = new OrthographicCamera(w, h);
        camera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
        camera.update();
        
        camController = new CameraController(camera);
        gestureDetector = new GestureDetector(100, 0.5f, 2, 0.15f, camController);
        
        gameStage.getViewport().setCamera(camera);
        
        addButtons();
        drawPieces();
        
        //uiStage.addActor(table);
        gameStage.addActor(table);
            
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gestureDetector);
        //inputMultiplexer.addProcessor(uiStage);
        inputMultiplexer.addProcessor(gameStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
        
        sourceImage = new ImageButton(Assets.pieceSkin, "butterfly");
        
        GameController.addTouchAndDrag(gameStage, "butterfly", sourceImage);
        //GameController.addTouchAndDrag(uiStage, "butterfly");
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
        camera.viewportWidth = w;
        camera.viewportHeight = h;
        camera.update();
		
	}
	
	public void addButtons() {
		table = new Table(Assets.menuSkin);
		table.setFillParent(true);
		table.setBounds(0, 0, w, h);
		
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
	
}
