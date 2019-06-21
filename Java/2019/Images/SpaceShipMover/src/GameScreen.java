import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class GameScreen extends JComponent implements KeyListener {
	
	private Image image;
	int angle = 0;
	
	public GameScreen() {
		image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("ship.png"));
	}
	
	public void paintComponent(Graphics g) {
		
		g.drawImage(image, 20, 20, this);
		
		Graphics2D g2d=(Graphics2D)g.create();       // Create a Java2D version of g.
		g2d.translate(225, 45);              // Translate the center of our coordinates.
		g2d.rotate(Math.toRadians(angle));                      // Rotate the image by 1 radian.
		g2d.drawImage(image, -25, -25, this);		
		g2d.translate(-225, -45);
		g2d.rotate(-Math.toRadians(angle));
		g.drawImage(image, 400, 20, this);

	}
	




@Override
public void keyTyped(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

@Override
public void keyPressed(KeyEvent e) {
	if (e.getKeyChar() == 'a') angle+=5;
	else angle-= 5;
	repaint();
	System.out.println("Angle:"+angle);
}

@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}
}
