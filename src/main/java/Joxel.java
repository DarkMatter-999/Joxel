import org.lwjgl.glfw.GLFW;

import dm.joxel.Engine.Window;
import dm.joxel.Graphics.Mesh;
import dm.joxel.Graphics.Renderer;
import dm.joxel.Graphics.Shader;
import dm.joxel.Graphics.Vertex;
import dm.joxel.Maths.Vector3f;
import dm.joxel.Objects.Camera;
import dm.joxel.Objects.GameObject;
import dm.joxel.Maths.Vector2f;
import dm.joxel.Engine.Input;
import dm.joxel.Graphics.Material;

public class Joxel implements Runnable {
	public Thread game;
	public static Window window;
	public final int WIDTH = 1280, HEIGHT = 720;
	public Shader shader;

	public Renderer renderer;
	public Mesh mesh = new Mesh(new Vertex[] {
			//Back face
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),
			
			//Front face
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
			
			//Right face
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
			
			//Left face
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
			
			//Top face
			new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
			
			//Bottom face
			new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
	}, new int[] {
			//Back face
			0, 1, 3,	
			3, 1, 2,	
			
			//Front face
			4, 5, 7,
			7, 5, 6,
			
			//Right face
			8, 9, 11,
			11, 9, 10,
			
			//Left face
			12, 13, 15,
			15, 13, 14,
			
			//Top face
			16, 17, 19,
			19, 17, 18,
			
			//Bottom face
			20, 21, 23,
			23, 21, 22
		}, new Material("resources/Textures/dirt.png"));

	public GameObject gameObject = new GameObject(mesh, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

	public Camera camera = new Camera(new Vector3f(0,0,1), new Vector3f(0,0,0));

	public void start() {
		game = new Thread(this, "Game");

		game.run();
	}

	public void init() {
		 System.out.println("Init gameloop");
		 window = new Window(WIDTH, HEIGHT, "Joxel");
		 window.setBGColor(0.4f, 0.7f, 1.0f);
		 window.create();

		 shader = new Shader("resources/Shaders/mainVertex.glsl","resources/Shaders/mainFragment.glsl");

		 renderer = new Renderer(shader, window);
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
			if(Input.isKeyDown(GLFW.GLFW_KEY_E)) window.mouseState(true);
			if(Input.isKeyDown(GLFW.GLFW_KEY_Q)) window.mouseState(false);
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

		 gameObject.update();
		 camera.update(gameObject);
	}

	public void render() {
		 renderer.renderMesh(gameObject, camera);
		 // System.out.println("Render");
		 window.swapBuffers();
	}

	public static void main(String[] args) {
		System.out.println("Joxel");
		new Joxel().start();
	}
}
