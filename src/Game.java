
import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

public class Game extends Applet implements Runnable, KeyListener, MouseMotionListener, MouseListener{

	private static final long serialVersionUID = 1L;
	
	private Image doubleI;
	private Graphics doubleG;
	private int score = 0;
	
	private boolean startMenu = true;
	private boolean mouseInPlay = false;
	private boolean birdMove = false;
	private boolean obstacleAppear = false;
	private boolean gameOver = false;
	private boolean gameOverMenu = false;
	private boolean mouseInTryAgain = false;
	
	private double backgroundX = 0;
	private double backgroundDx = -3;
	private URL backgroundURL;
	private Image backgroundImage;
	
	private Bird b = new Bird();
	private Obstacles obs = new Obstacles();
	private Obstacles2 obs2 = new Obstacles2();
	private Ground grd = new Ground();
	
	public void gameOverAllow() {
		gameOver = true;
		//bird hits ground then trigger menu
		if(b.getY() - b.getRadius() > grd.getGroundY()){
			gameOverMenu = true;
		}		
	}

	public void scoreCounter() {
		score++;
	}
	
	public void init() {
		setSize(400,600);
		addKeyListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
		try{
			backgroundURL = getDocumentBase();
			Ground.groundURL = getDocumentBase();
		}catch (Exception e){
			
		}
		backgroundImage = getImage(backgroundURL, "images/Background.png");
		Ground.groundImage = getImage(Ground.groundURL, "images/Ground.png");
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run() {
		while(true){
			if(!startMenu){
				if(birdMove){
					b.update(this);
				}
				if(obstacleAppear){
					if(!gameOver){
						obs.update(this, b);
						obs2.update(this, b);
						backgroundX += backgroundDx;
						if(backgroundX + this.getWidth() < 0 ){
							backgroundX = 0;
						}
					}	
				}
				grd.update(this, b);
			}	
			//setting frame rate
			try {
				Thread.sleep(17);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
	
	public void update(Graphics g) {
		//double buffering
		if(doubleI == null){
			doubleI = createImage(this.getWidth(), this.getHeight());
			doubleG = doubleI.getGraphics();
		}
		doubleG.setColor(getBackground());
		doubleG.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		doubleG.setColor(getForeground());
		paint(doubleG);
		
		g.drawImage(doubleI, 0, 0, this);
	}
	
	public void paint(Graphics g) {
		g.drawImage(backgroundImage, (int)backgroundX, 0, this);
		g.drawImage(backgroundImage, (int)backgroundX + this.getWidth(), 0, this);
		if(startMenu){
			Font font1 = new Font("Impact", Font.BOLD, 70);
			g.setFont(font1);
			g.setColor(Color.BLACK);
			g.drawString("FLAPPY FACE", 13, 103);
			g.setColor(Color.GREEN);
			g.drawString("FLAPPY FACE", 10, 100);
			//play button
			g.setColor(Color.BLACK);
			g.fillRect(120, 280, 160, 80);
			Font font4 = new Font("Impact", Font.BOLD, 70);
			g.setFont(font4);
			g.setColor(Color.WHITE);
			g.drawString("PLAY", 130, 350);
			if(mouseInPlay){
				g.setColor(Color.RED);
				g.drawString("PLAY", 130, 350);
			}
		}else{
			String currentScore = Integer.toString(score);
			if(obstacleAppear){
				obs.paint(g);
				obs2.paint(g);
				//paint score
				Font font1 = new Font("Impact", Font.BOLD, 60);
				g.setFont(font1);
				g.setColor(Color.BLACK);
				g.drawString(currentScore, 193, 153);
				g.setColor(Color.WHITE);
				g.drawString(currentScore, 190, 150);
			}else{
				//get ready message
				Font font4 = new Font("Impact", Font.BOLD, 60);
				g.setFont(font4);
				g.setColor(Color.RED);
				g.drawString("Get Ready", 60, 150);
				g.setColor(Color.BLACK);
				Font font3 = new Font("Impact", Font.PLAIN, 20);
				g.setFont(font3);
				g.drawString("'spacebar' to fly", 190, 230);
			}		
			//game over menu
			if(gameOverMenu){
				g.setColor(Color.BLACK);
				g.fillRect(40, 90, 320, 320);
				g.setColor(new Color(139, 69, 19));
				g.fillRect(50, 100, 300, 300);
				Font font2 = new Font("Impact", Font.BOLD, 50);
				g.setFont(font2);
				g.setColor(Color.WHITE);
				g.drawString("Game Over", 75, 175);
				Font font3 = new Font("Impact", Font.PLAIN, 30);
				g.setFont(font3);
				g.drawString("Your Score: " + currentScore, 130, 270);
				//Try again button
				g.setColor(Color.BLACK);
				g.fillRect(120, 310, 175, 55);
				Font font4 = new Font("Impact", Font.BOLD, 35);
				g.setFont(font4);
				g.setColor(Color.WHITE);
				g.drawString("Try Again", 130, 350);
				if(mouseInTryAgain){
					g.setColor(Color.RED);
					g.drawString("Try Again", 130, 350);
				}
			}
		}
		//bird and ground are always painted
		b.paint(g);
		grd.paint(g, this);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			if(!gameOver){
				b.moveUp();
				birdMove = true;
				obstacleAppear = true;
			}			
		}
	}

	public void mouseMoved(MouseEvent e) {
		if(e.getX() > 120 && e.getX() < 295){
			if(e.getY() > 310 && e.getY() < 365){
				mouseInTryAgain = true;
			}else{
				mouseInTryAgain = false;
			}
		}else{
			mouseInTryAgain = false;
		}
		if(e.getX() > 120 && e.getX() < 280){
			if(e.getY() > 280 && e.getY() < 360){
				mouseInPlay = true;
			}else{
				mouseInPlay = false;
			}
		}else{
			mouseInPlay = false;
		}
	}

	public void mouseClicked(MouseEvent e) {
		//game reset
		if(gameOverMenu){
			if(mouseInTryAgain){
				score = 0;
				birdMove = false;
				obstacleAppear = false;
				gameOver = false;
				gameOverMenu = false;			
				mouseInTryAgain = false;
				b = new Bird();
				obs = new Obstacles();
				obs2 = new Obstacles2();
				grd = new Ground();
			}	
		}
		if(mouseInPlay){
			startMenu = false;
		}
	}
	//unused methods
	public void stop() {

	}
	
	public void destroy() {

	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {

	}
}