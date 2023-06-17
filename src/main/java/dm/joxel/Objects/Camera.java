package dm.joxel.Objects;

import org.joml.Math;
import org.lwjgl.glfw.GLFW;

import dm.joxel.Engine.Input;
import dm.joxel.Maths.Vector3f;

public class Camera {
	private Vector3f position, rotation;
	private float moveSpeed = 0.25f, mouseSens = 0.25f;
	private double oldMouseX = 0, oldMouseY = 0, newMouseX, newMouseY;
	private float distance = 2.0f, angle = 0, verticalAngle = 0, horizontalAngle = 0;

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public void update() {
		newMouseX = Input.getMouseX();
		newMouseY = Input.getMouseY();
		
		float x = (float) Math.sin(Math.toRadians(rotation.y)) * moveSpeed;
		float z = (float) Math.cos(Math.toRadians(rotation.y)) * moveSpeed;
		
		if (Input.isKeyDown(GLFW.GLFW_KEY_A)) position = Vector3f.add(position, new Vector3f(-z, 0, x));
		if (Input.isKeyDown(GLFW.GLFW_KEY_D)) position = Vector3f.add(position, new Vector3f(z, 0, -x));
		if (Input.isKeyDown(GLFW.GLFW_KEY_W)) position = Vector3f.add(position, new Vector3f(-x, 0, -z));
		if (Input.isKeyDown(GLFW.GLFW_KEY_S)) position = Vector3f.add(position, new Vector3f(x, 0, z));
		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE)) position = Vector3f.add(position, new Vector3f(0, moveSpeed, 0));
		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL)) position = Vector3f.add(position, new Vector3f(0, -moveSpeed, 0));
		
		float dx = (float) (newMouseX - oldMouseX);
		float dy = (float) (newMouseY - oldMouseY);
		
		rotation = Vector3f.add(rotation, new Vector3f(-dy * mouseSens, -dx * mouseSens, 0));

		// Limit x rotation to a certain range
		if (rotation.x > 90) {
		    rotation.x = 90;
		} else if (rotation.x < -90) {
		    rotation.x = -90;
		}
	
		oldMouseX = newMouseX;
		oldMouseY = newMouseY;
	}
	
	public void update(GameObject gameObject) {

		newMouseX = Input.getMouseX();
		newMouseY = Input.getMouseY();
		
		float dx = (float) (newMouseX - oldMouseX);
		float dy = (float) (newMouseY - oldMouseY);

		if (Input.isKeyDown(GLFW.GLFW_KEY_F)) {
			verticalAngle -= dy * mouseSens;
			horizontalAngle += dx * mouseSens;
		}

		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_SHIFT)) {
			if (distance > 0) {
				distance += dy * mouseSens / 4;
			} else {
				distance = 0.1f;
			}
		}

		float horizontalDistance = distance * (float) Math.cos(Math.toRadians(verticalAngle));
		float verticalDistance = distance * (float) Math.sin(Math.toRadians(verticalAngle));

		float xOffset = (float) (horizontalDistance * Math.sin(Math.toRadians(-horizontalAngle)));
		float zOffset = (float) (horizontalDistance * Math.cos(Math.toRadians(-horizontalAngle)));

		position.set(gameObject.getPosition().x + xOffset, gameObject.getPosition().y - verticalDistance, gameObject.getPosition().z + zOffset);

		rotation.set(verticalAngle, -horizontalAngle, 0);

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
