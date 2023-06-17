package dm.joxel.Objects;

import org.joml.Math;
import org.lwjgl.glfw.GLFW;

import dm.joxel.Engine.Input;
import dm.joxel.Maths.Vector3f;

public class Camera {
	private Vector3f position, rotation;
	private float moveSpeed = 0.25f, mouseSens = 0.25f;
	private double oldMouseX, oldMouseY, newMouseX, newMouseY;

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public void update() {

		newMouseX = Input.getMouseX();
		newMouseY = Input.getMouseY();

		float x = Math.sin(Math.toRadians(rotation.y)) * moveSpeed;
		float z = Math.cos(Math.toRadians(rotation.y)) * moveSpeed;

		if(Input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector3f.add(position, new Vector3f(-z, 0, x));
		if(Input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector3f.add(position, new Vector3f(z, 0, -x));
		if(Input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector3f.add(position, new Vector3f(x, 0, z));
		if(Input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector3f.add(position, new Vector3f(-x, 0, -z));
		if(Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));
		if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0));

		float dx = (float) (newMouseX - oldMouseX);
		float dy = (float) (newMouseY - oldMouseY);

		rotation = Vector3f.add(rotation, new Vector3f(-dy * mouseSens, -dx * mouseSens, 0));

		oldMouseX = newMouseX;
		oldMouseY = newMouseY;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}
}
