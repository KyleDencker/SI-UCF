import java.util.ArrayDeque;
import java.util.Scanner;

public class BFS {

	static int[] dx = {1, -1, 0, 0};
	static int[] dy = {0, 0, 1, -1};

	public static void main(String[] args) {
		// Breadth first search
		Scanner scan = new Scanner(System.in);

		int height = scan.nextInt();
		int width = scan.nextInt();
		int startX = -1, startY = -1, endX = -1, endY = -1;
		char[][] graph = new char[height][width];
		char[][] path = new char[height][width];
		int[][] dist = new int[height][width];
		// Scan in the maze.
		for (int y = 0; y < height; y++) {
			String input = scan.next();
			for (int x = 0; x < width; x++) {
				graph[y][x] = path[y][x] = input.charAt(x);
				// Set start and end points.
				if (graph[y][x] == 'S') {
					startX = x;
					startY = y;
				} else if (graph[y][x] == 'E') {
					endX = x;
					endY = y;
				}
				dist[y][x] = -1;
			}
		}


		ArrayDeque<Integer> X = new ArrayDeque<Integer>();
		ArrayDeque<Integer> Y = new ArrayDeque<Integer>();
		X.addLast(startX);
		Y.addLast(startY);
		dist[startY][startX] = 0;
		// BFS through our maze.
		while (!X.isEmpty()) {
			int currX = X.pollFirst();
			int currY = Y.pollFirst();

			if (currX == endX && currY == endY) break;

			int newDist = dist[currY][currX] + 1;
			for (int i = 0; i < dx.length; i++) {
				int newX = currX + dx[i];
				int newY = currY + dy[i];

				if (isValid(newX, newY, height, width)) {
					if (graph[newY][newX] == '.' || graph[newY][newX] == 'E') {
						if (dist[newY][newX] == -1) {
							X.addLast(newX);
							Y.addLast(newY);
							dist[newY][newX] = newDist;
						}
					}
				}
			}
		}

		// Backtrack to find our path.
		X.clear();
		Y.clear();
		X.addLast(endX);
		Y.addLast(endY);
		while (!X.isEmpty()) {
			int currX = X.pollFirst();
			int currY = Y.pollFirst();

			if (currX == startX && currY == startY) break;
			path[currY][currX] = 'X';
			for (int i = 0; i < dx.length; i++) {
				int newX = currX + dx[i];
				int newY = currY + dy[i];

				if (isValid(newX, newY, height, width)) {
					if (dist[newY][newX] == dist[currY][currX] - 1) {
						X.addLast(newX);
						Y.addLast(newY);
					}
				}
			}
		}

		print(path);
		print(dist);
	}

	static boolean isValid(int x, int y, int h, int w) {
		if (x < 0) return false;
		if (x >= w) return false;
		if (y < 0) return false;
		if (y >= h) return false;
		return true;
	}

	static void print(char[][] graph) {
		for (int y = 0; y < graph.length; y++) {
			for (int x = 0; x < graph[0].length; x++) {
				System.out.print(graph[y][x]);
			}
			System.out.println();
		}
	}

	static void print(int[][] graph) {
		for (int y = 0; y < graph.length; y++) {
			for (int x = 0; x < graph[0].length; x++) {
				String s = String.valueOf(graph[y][x]);
				System.out.print((s.length() > 1 ? "" : " ") + s + " ");
			}
			System.out.println();
		}
	}
}

/*

6 6
S***E*
.*...*
.*.***
.*....
.****.
......

7 12
S...********
***.*.......
....*.*****.
.**.*.*****E
..*.*.***...
**........**
************






 */
