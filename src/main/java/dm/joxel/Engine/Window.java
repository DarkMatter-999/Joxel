package dm.joxel.Engine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
	private int width, height;
	private String title;
	private long window;
	private int frames;
	private static long time;
	public Input input;
	public float[] backgroundColor = { 0.0f, 0.0f, 0.0f };
	private GLFWWindowSizeCallback windowSizeCallback;
	private boolean isFullscreen = false;
	private boolean isResized;

	private int[] windowPosX = new int[1], windowPosY = new int[1];

	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	public void create() {
		if(!glfwInit()) {
			System.err.println("Error creating window");
			return;			
		}

		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

		window = glfwCreateWindow(width, height, title, isFullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);

		if (window == 0) {
			System.err.println("Window wasn't created");
		}

		input = new Input();

		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		windowPosX[0] = (vidMode.width() - width) / 2;
		windowPosY[0] = (vidMode.height() - height) / 2;
		GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);
		glfwMakeContextCurrent(window);

		GL.createCapabilities();
		glEnable(GL_DEPTH_TEST);

		createCallbacks();

		glfwShowWindow(window);

		glfwSwapInterval(1);

		time = System.currentTimeMillis();
	}

	public void update() {
		if (isResized) {
			GL11.glViewport(0, 0, width, height);
			isResized = false;
		}
		glClearColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glfwPollEvents();
		frames++;

		long newtime = System.currentTimeMillis();
		if(newtime > time + 1000) {
			glfwSetWindowTitle(window, title + " | FPS : " + frames);
			time = newtime;
			frames = 0;
		}
	}

	private void createCallbacks() {
		glfwSetKeyCallback(window, input.getKeyboardCallback());
		glfwSetCursorPosCallback(window, input.getMouseMoveCallback());
		glfwSetMouseButtonCallback(window, input.getMouseButtonsCallback());
		glfwSetScrollCallback(window, input.getMouseScrollCallback());
		
		windowSizeCallback = new GLFWWindowSizeCallback() {
			public void invoke(long window, int w, int h) {
				width = w;
				height = h;
				glViewport(0, 0, width, height);
				isResized = true;
				System.out.println("Width: " + width + " Height: " + height);	
			}
		};

		glfwSetWindowSizeCallback(window, windowSizeCallback);

	}

	public void swapBuffers() {
		glfwSwapBuffers(window);
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}

	public void destroy() {
		windowSizeCallback.free();
		input.destroy();
		glfwWindowShouldClose(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}

	public void setBGColor(float r, float g, float b) {
		backgroundColor[0] = r;
		backgroundColor[1] = g;
		backgroundColor[2] = b;
	}

	public void setFullscreen(boolean fullscreen) {
		this.isFullscreen = fullscreen;
		isResized = true;
		if (isFullscreen) {
			GLFW.glfwGetWindowPos(window, windowPosX, windowPosY);
			GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
		} else {
			GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
		}
	}
	public void toggleFullscreen() {
		this.isFullscreen = !this.isFullscreen;
		setFullscreen(isFullscreen);
	}
}
