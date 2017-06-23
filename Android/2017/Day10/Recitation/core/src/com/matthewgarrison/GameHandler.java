package com.matthewgarrison;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.matthewgarrison.objects.Score;
import com.matthewgarrison.screens.MainGame;

import java.util.ArrayList;
import java.util.Collections;

public class GameHandler extends Game {
	public final static int SCREEN_WIDTH = 20, SCREEN_HEIGHT = 11;
	public Preferences prefs;
	public ArrayList<Score> scores;
	private final static String DEFAULT_SCORES = "---: 10000 ---: 10000 ---: 10000";

	@Override
	public void create () {
		prefs = Gdx.app.getPreferences("SI-UCF");
		this.setScreen(new MainGame(this));
	}

	public void loadScores() {
		scores = new ArrayList<Score>();
		String[] parts = prefs.getString("scores", DEFAULT_SCORES).split("[: ]+");
		for (int i = 0, j = 0; j < 3; i += 2, j++) {
			scores.add(new Score(parts[i], Float.parseFloat(parts[i+1])));
		}
	}

	public void addNewSCore(String name, int n, float f) {
		loadScores();
		Score newScore = new Score(name, f);
		scores.add(newScore);
		Collections.sort(scores);
		scores.remove(3);

		String output = "";
		for (Score s : scores) output += s + " ";
		prefs.putString("scores", output);
		prefs.flush();
	}
}