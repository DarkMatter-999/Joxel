import org.lwjgl.glfw.GLFW;

import dm.joxel.Engine.Window;
import dm.joxel.Graphics.Mesh;
import dm.joxel.Graphics.Renderer;
import dm.joxel.Graphics.Shader;
import dm.joxel.Graphics.Vertex;
import dm.joxel.Maths.Vector3f;
import dm.joxel.Engine.Input;

public class Joxel implements Runnable {
	public Thread game;
	public static Window window;
	public final int WIDTH = 1280, HEIGHT = 720;
	public Shader shader;

	public Renderer renderer;
	public Mesh mesh = new Mesh(new Vertex[] {
			new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 1.0f))
		}, new int[] {
			0, 1, 2,
			0, 3, 2
	});

	public void start() {
		game = new Thread(this, "Game");

		game.run();
	}

	public void init() {
		 System.out.println("Init gameloop");
		 window = new Window(WIDTH, HEIGHT, "Joxel");
		 window.setBGColor(0.4f, 0.7f, 1.0f);
		 window.create();

		 shader = new Shader("resources/mainVertex.glsl","resources/mainFragment.glsl");

		 renderer = new Renderer(shader);
		 mesh.create();
		 shader.create();
	}

	public void run() {
		init();
		
		while(!window.shouldClose()) {
			update();
			render();
			if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
				break;
			} else if(Input.isKeyDown(GLFW.GLFW_KEY_F11)) {
				window.toggleFullscreen();
			}
		}
		
		close();
	}

	private void close() {
		window.destroy();
		mesh.destroy();
		shader.destroy();
	}

	public void update() {
		 // System.out.println("Update");
		 window.update();
		 if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("LEFT: X: " + Input.getMouseX() + " Y: " + Input.getMouseY());
		 if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_RIGHT)) System.out.println("RIGHT: X: " + Input.getMouseX() + " Y: " + Input.getMouseY());
	}

	public void render() {
		renderer.renderMesh(mesh);
		 // System.out.println("Render");
		 window.swapBuffers();
	}

	public static void main(String[] args) {
		System.out.println("Joxel");
		new Joxel().start();
	}
}
