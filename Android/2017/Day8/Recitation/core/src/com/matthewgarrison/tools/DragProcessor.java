package com.matthewgarrison.tools;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.matthewgarrison.objects.Ball;

public class DragProcessor extends InputAdapter {
	private Vector3 touchPos, dragPos;
	private Ball ball;
	private OrthographicCamera camera;
	private float marginOfError, distance, angle;
	private boolean isValid, isStarted;

	public DragProcessor(Ball b, OrthographicCamera c) {
		touchPos = new Vector3();
		dragPos = new Vector3();
		ball = b;
		camera = c;
		marginOfError = b.getDiameter();
		distance = angle = 0;
		isValid = isStarted = false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)	{
		touchPos.set(screenX, screenY, 0);
		camera.unproject(touchPos);
		if (touchPos.dst(ball.getVector3Pos()) <= marginOfError) {
			isValid = true;
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (isValid) {
			isStarted = true;

			dragPos.set(screenX, screenY, 0);
			camera.unproject(dragPos);
			distance = ball.getVector3Pos().dst(dragPos);
			angle = MathUtils.atan2(ball.getBody().getPosition().x - dragPos.x,
					dragPos.y - ball.getBody().getPosition().y) + (float)Math.PI/2.0f;
			if (angle < 0) angle += 2.0f * Math.PI;
		}

		return true;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (dragPos.dst(ball.getVector3Pos()) > marginOfError) {
			if (!ball.getIsLaunched()) {
				ball.setIsLaunched(true);
				ball.applyLinearImpulse(getX(), getY());
				ball.getBody().setGravityScale(1);
				ball.launchCount++;
			}
		}
		isValid = isStarted = false;
		return true;
	}

	public Vector3 getDragPosForScreen() {
		Vector3 v = new Vector3(dragPos);
		v.set(v.x + 2*getX(), v.y + 2*getY(), 0);
		return v;
	}

	public boolean getIsStarted() {
		return isStarted;
	}

	private float getX() {
		return -distance * (float)Math.cos(angle);
	}

	private float getY() {
		return -distance * (float)Math.sin(angle);
	}
}