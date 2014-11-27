package com.mygdx.tween;

import com.badlogic.gdx.scenes.scene2d.Actor;

import aurelienribon.tweenengine.TweenAccessor;

public class ActorAccessor implements TweenAccessor<Actor> {
	
	public static final int Y = 0, RGB = 1, ALPHA = 2;

	@Override
	public int getValues(Actor target, int tweenType, float[] returnValues) {
		final int result;
		switch(tweenType) {
		case Y:
			returnValues[0] = target.getY();
			result = 1;
			break;
		case RGB:
			returnValues[0] = target.getColor().r;
			returnValues[0] = target.getColor().g;
			returnValues[0] = target.getColor().b;
			result = 3;
			break;
		case ALPHA:
			returnValues[0] = target.getColor().a;
			result = 1;
			break;
		default:
			assert false;
			result = -1;
		}
		return result;
	}

	@Override
	public void setValues(Actor target, int tweenType, float[] newValues) {
		switch(tweenType) {
		case Y:
			target.setY(newValues[0]);
			break;
		case RGB:
			target.setColor(newValues[0], newValues[1], newValues[2], target.getColor().a);
			break;
		case ALPHA:
			target.setColor(target.getColor().r, target.getColor().g, target.getColor().b, newValues[0]);
			break;
		default:
			assert false;
		}
		
	}

}
