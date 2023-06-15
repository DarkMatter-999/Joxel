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

		window = glfwCreateWindow(width, height, title, NULL, NULL);

		if (window == 0) {
			System.err.println("Window wasn't created");
		}

		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);
		glfwMakeContextCurrent(window);

		glfwShowWindow(window);

		glfwSwapInterval(1);

		time = System.currentTimeMillis();
	}

	public void update() {
		glfwPollEvents();
		frames++;

		long newtime = System.currentTimeMillis();
		if(newtime > time + 1000) {
			glfwSetWindowTitle(window, title + " | FPS : " + frames);
			time = newtime;
			frames = 0;
		}
	}

	public void swapBuffers() {
		glfwSwapBuffers(window);
	}

	public boolean shouldClose() {
		return glfwWindowShouldClose(window);
	}
}
