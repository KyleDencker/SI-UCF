package com.matthewgarrison.objects;

public class Score implements Comparable<Score> {
	String name;
	int value;
	float timeTaken;

	public Score(String s, float f) {
		name = s;
		timeTaken = f;
	}

	@Override
	public int compareTo(Score s) {
		if (timeTaken == s.timeTaken) {
			return name.compareTo(s.name);
		}
		return Float.compare(timeTaken, s.timeTaken);
		// return value - s.value;
	}

	public String toString() {
		return name + ": " + String.format("%.3f", timeTaken);
	}
}
