package com.matthewgarrison;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayDeque;

public class BFS {

	private static int[] dx = {1, -1, 0, 0};
	private static int[] dy = {0, 0, 1, -1};

	public static void go(MainScreen screen) {
		//Gdx.graphics.setContinuousRendering(false);
		if (screen.startX == -1 || screen.startY == -1 || screen.endX == -1 || screen.endY == -1) return;
		ArrayDeque<Integer> X = new ArrayDeque<Integer>();
		ArrayDeque<Integer> Y = new ArrayDeque<Integer>();
		X.addLast(screen.startX);
		Y.addLast(screen.startY);
		float timer = 0;
		while (!X.isEmpty()) {
			//Gdx.graphics.requestRendering();
			timer += Gdx.graphics.getDeltaTime();
			if (timer < 0.51f) continue;
			timer = 0;
			screen.draw();
			System.out.println("draw");

			int currX = X.pollFirst();
			int currY = Y.pollFirst();

			if (currX == screen.endX && currY == screen.endY) break;

			if (screen.grid[currY][currX] != 'S') screen.grid[currY][currX] = 'V';
			for (int i = 0; i < dx.length; i++) {
				int newX = currX + dx[i];
				int newY = currY + dy[i];

				if (isValid(newX, newY)) {
					if (screen.grid[newY][newX] == 'F' || screen.grid[newY][newX] == 'E') {
						X.addLast(newX);
						Y.addLast(newY);
					}
				}
			}
		}

		//Gdx.graphics.setContinuousRendering(true);
	}

	private static boolean isValid(int x, int y) {
		return (x >= 0 && x < 20 && y >= 0 && y < 12);
	}

}
