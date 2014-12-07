package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GameController;
import com.mygdx.screen.GameScreen;

public class CameraController extends GestureAdapter {
	private float velX, velY;
	private boolean flinging = false;
	private float initialScale = 1;
	private OrthographicCamera camera;
	private float effectiveViewportWidth;
	private float effectiveViewportHeight;
	
	public CameraController(OrthographicCamera camera){
		this.camera = camera;
		effectiveViewportWidth = camera.viewportWidth * camera.zoom;
		effectiveViewportHeight = camera.viewportHeight * camera.zoom;
	}
	
	private void keepCameraWithinViewport() {
	    //camera.zoom = MathUtils.clamp(camera.zoom, 1.0f, 100/camera.viewportWidth);
		camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 1.0f);
	    camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, GameScreen.WORLD_WIDTH - effectiveViewportWidth / 2f);
	    camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, GameScreen.WORLD_HEIGHT - effectiveViewportHeight / 2f);
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
		if(!GameController.dragAndDrop.isDragging()) {
			velX = camera.zoom * velocityX * 0.5f;
			velY = camera.zoom * velocityY * 0.5f;
		}
		return false;
	}

	@Override
	public boolean pan (float x, float y, float deltaX, float deltaY) {
		if(!GameController.dragAndDrop.isDragging()) {
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
		if(!GameController.dragAndDrop.isDragging()) {
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
