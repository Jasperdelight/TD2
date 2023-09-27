package main;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class Game extends JFrame implements Runnable{

	private GameScreen gameScreen;
	private BufferedImage img;
	private long lastUpdate;
	private double timePerUpdate;
	private Thread gameThread;
	private final double FPS_SET = 120;
	private final double UPS_SET = 60;

	public Game(){
		importImg();
		setSize(640, 640);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		gameScreen = new GameScreen(img);
		add(gameScreen);
		setVisible(true);
	}
	private void importImg(){
		InputStream is = getClass().getResourceAsStream("/spriteatlas.png");
		try {
			img = ImageIO.read(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void start(){
		gameThread = new Thread(this){};
		gameThread.start();
	}
	private void updateGame(){
		// System.out.println("Game Updated!");
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		long lastFrame = System.nanoTime();
		long lastTimeCheck = System.currentTimeMillis();
		long lastUpdate = System.nanoTime();
		int frames = 0;
		int updates = 0;

		while(true){
			// RENDER
			if(System.nanoTime() - lastFrame >= timePerFrame){
				repaint();
				lastFrame = System.nanoTime();
				frames++;
			}
			// UPDATES
			if(System.nanoTime()- lastUpdate >= timePerUpdate){
				updateGame();
				lastUpdate = System.nanoTime();
				updates++;
			}
			if(System.currentTimeMillis() - lastTimeCheck >= 1000){
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
				lastTimeCheck = System.currentTimeMillis();
			}
		}
	}
}
