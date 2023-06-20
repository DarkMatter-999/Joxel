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
import dm.joxel.Objects.Cube;
import java.util.*;

public class Joxel implements Runnable {
	public Thread game;
	public static Window window;
	public final int WIDTH = 1280, HEIGHT = 720;
	public Shader shader;
	public final int CHUNK_SIZE = 8;
	public static List<Vector3f> usedPos = new ArrayList<Vector3f>();
	private boolean isRunning = false;

	public Renderer renderer;
	public Mesh mesh = Cube.makeCube(); 
	public GameObject gameObject = new GameObject(mesh, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));

	public List<GameObject> gameObjects = new ArrayList<GameObject>();

	public Camera camera = new Camera(new Vector3f(0,0,0), new Vector3f(0,0,0));

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
		 isRunning = true;
	}

	public void run() {
		init();

		new Thread(new Runnable() { 
			public void run() {
				while(isRunning) {
					for(int x = (int) camera.getPosition().x - CHUNK_SIZE; x < (int) camera.getPosition().x + CHUNK_SIZE; x++) {
						 for(int z = (int) camera.getPosition().z - CHUNK_SIZE; z < (int) camera.getPosition().z + CHUNK_SIZE; z++) {
						 if(!usedPos.contains(new Vector3f(x, 0, z))) {
							 gameObjects.add(new GameObject(mesh, new Vector3f(x, 0, z), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)));
						 usedPos.add(new Vector3f(x, 0, z));
							  }
						 }
					}

					for(int i=0; i < gameObjects.size(); i++) {
						int distX = (int) (camera.getPosition().x - gameObjects.get(i).getPosition().x);
						int distZ = (int) (camera.getPosition().z - gameObjects.get(i).getPosition().z);
						if(distX < 0) {
							distX = - distX;
						}
						if(distZ < 0) {
							distZ = - distZ;
						}

						if ((distX > CHUNK_SIZE) || (distZ > CHUNK_SIZE)) {
							usedPos.remove(gameObjects.get(i).getPosition());
							gameObjects.remove(i);
						}
					}

				}
			} 
		}).start();
		
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
		isRunning = false;
		close();
		//chunk.stop();
	}

	private void close() {
		window.destroy();
		mesh.destroy();
		shader.destroy();
	}

	public void update() {
		 // System.out.println("Update");
		 window.update();

		 for(GameObject gameObject: gameObjects) {
			gameObject.update();
		 }		 
		 camera.update();
	}

	public void render() {
		 renderer.renderObjects(gameObjects, camera);
		 // System.out.println("Render");
		 window.swapBuffers();
	}

	public static void main(String[] args) {
		System.out.println("Joxel");
		new Joxel().start();
	}
}
