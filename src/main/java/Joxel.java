import org.lwjgl.glfw.GLFW;

import dm.joxel.Engine.Window;
import dm.joxel.Engine.Input;

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
			if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
				break;
			}
		}
		window.destroy();
		
	}

	public void update() {
		 // System.out.println("Update");
		 window.update();
		 if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("LEFT: X: " + Input.getMouseX() + " Y: " + Input.getMouseY());
		 if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) System.out.println("RIGHT: X: " + Input.getMouseX() + " Y: " + Input.getMouseY());
	}

	public void render() {
		 // System.out.println("Render");
		 window.swapBuffers();
	}

	public static void main(String[] args) {
		System.out.println("Joxel");
		new Joxel().start();
	}
}
