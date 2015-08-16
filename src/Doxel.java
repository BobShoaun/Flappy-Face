import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

public class Doxel {
	private int x, y, width;
	public static URL url;
	public static Image image; 
	
	public Doxel() {
		x = 0;
		y = 0;
	}
	
	public void paint(Graphics g, Game gm){
		width = gm.getWidth();
		g.drawImage(image, x, y, gm);
		g.drawImage(image, x + width, y, gm);
	}

}
