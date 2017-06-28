package com.matthewgarrison;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayDeque;


public class DFS {

	private int[] dx = {1, -1, 0, 0};
	private int[] dy = {0, 0, 1, -1};
	private MainScreen screen;
	private ArrayDeque<Integer> X, Y;
	public boolean isStarted, isDone;

	public DFS(MainScreen screen) {
		this.screen = screen;
		isStarted = isDone = false;
		X = new ArrayDeque<Integer>();
		Y = new ArrayDeque<Integer>();
	}

	public void init() {
		if (screen.startX == -1 || screen.startY == -1) return;
		X.clear();
		Y.clear();
		X.addFirst(screen.startX);
		Y.addFirst(screen.startY);
		screen.dist[screen.startY][screen.startX] = 0;
		isStarted = true;
		isDone = false;
	}

	public void go() {
		while (!X.isEmpty()) {
			int currX = X.pollFirst();
			int currY = Y.pollFirst();

			int newDist = screen.dist[currY][currX] + 1;
			if (screen.grid[currY][currX] != 'S' && screen.grid[currY][currX] != 'E')
				screen.grid[currY][currX] = 'V';
			for (int i = 0; i < dx.length; i++) {
				int newX = currX + dx[i];
				int newY = currY + dy[i];

				if (isValid(newX, newY)) {
					if (screen.dist[newY][newX] == -1 || screen.dist[newY][newX] > newDist) {
						if (screen.grid[newY][newX] != 'W') {
							X.addFirst(newX);
							Y.addFirst(newY);
							screen.dist[newY][newX] = newDist;
						}
					}
				}
			}

			return;
		}

		isDone = true;
		isStarted = false;
		backtrack();
	}

	public void backtrack() {
		if (screen.endY == -1 || screen.endX == -1) return;
		X.clear();
		Y.clear();
		X.addFirst(screen.endX);
		Y.addFirst(screen.endY);
		outerLoop : while (!X.isEmpty()) {
			int currX = X.pollFirst();
			int currY = Y.pollFirst();

			if (currX == screen.startX && currY == screen.startY) break;

			if (screen.grid[currY][currX] != 'E') screen.grid[currY][currX] = 'P';
			for (int i = 0; i < dx.length; i++) {
				int newX = currX + dx[i];
				int newY = currY + dy[i];

				if (isValid(newX, newY)) {
					if (screen.dist[newY][newX] == screen.dist[currY][currX] - 1) {
						X.addFirst(newX);
						Y.addFirst(newY);
						//continue outerLoop;
					}
				}
			}

		}
	}

	private boolean isValid(int x, int y) {
		return (x >= 0 && x < 20 && y >= 0 && y < 12);
	}

}
