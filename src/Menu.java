import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

public class Menu {
	private int menuX, menuY, menuWidth;
	public static URL menuURL;
	public static Image menuImage; 
	
	public Menu() {
		menuX = 0;
		menuY = 0;
	}
	
	public void paint(Graphics g, Game gm){
		menuWidth = gm.getWidth();
		g.drawImage(menuImage, menuX, menuY, gm);
		g.drawImage(menuImage, menuX + menuWidth, menuY, gm);
	}

}
