import javax.swing.JFrame;

public class SpaceShip {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Space SHips");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(500, 500);
		GameScreen game = new GameScreen();
		frame.add(game);
		frame.addKeyListener(game);
		frame.setVisible(true);
	}

}
