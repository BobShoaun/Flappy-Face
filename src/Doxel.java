import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

public class Doxel {
	private int x, y;
	public static URL url;
	public static Image image; 
	
	public Doxel() {
		x = 0;
		y = 0;
	}
	
	public void paint(Graphics g, Game gm){
		g.drawImage(image, x, y, gm);
	}

}
