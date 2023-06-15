import dm.joxel.Engine.Window;

public class Joxel implements Runnable {
	public Thread game;
	public static Window window;
	public final int WIDTH = 1280, HEIGHT = 720;

	public void start() {
		game = new Thread(this, "Game");

		game.run();
	}

	public void init() {
		 System.out.println("Init gameloop");
		 window = new Window(WIDTH, HEIGHT, "Joxel");
		 window.create();
	}

	public void run() {
		init();
		
		while(!window.shouldClose()) {
			update();
			render();
		}
		
	}

	public void update() {
		 // System.out.println("Update");
		 window.update();
	}

	public void render() {
		 // System.out.println("Render");
		 window.swapBuffers();
	}

	public static void main(String[] args) {
		new Joxel().start();
		System.out.println("Joxel");
	}
}
