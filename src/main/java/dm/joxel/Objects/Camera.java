package dm.joxel.Objects;

import org.lwjgl.glfw.GLFW;

import dm.joxel.Engine.Input;
import dm.joxel.Maths.Vector3f;

public class Camera {
	private Vector3f position, rotation;
	private float moveSpeed = 0.25f;

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public void update() {
		if(Input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector3f.add(position, new Vector3f(-moveSpeed, 0, 0));
		if(Input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector3f.add(position, new Vector3f(moveSpeed, 0, 0));
		if(Input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector3f.add(position, new Vector3f(0, 0, -moveSpeed));
		if(Input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector3f.add(position, new Vector3f(0, 0, moveSpeed));
		if(Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));
		if(Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0));
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}
}
