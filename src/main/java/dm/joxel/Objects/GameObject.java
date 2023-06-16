package dm.joxel.Objects;

import dm.joxel.Graphics.Mesh;
import dm.joxel.Maths.Vector3f;

public class GameObject {
	public Vector3f position, rotation, scale;
	public Mesh mesh;

	public GameObject(Mesh mesh, Vector3f position, Vector3f rotation, Vector3f scale) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.mesh = mesh;
	}

	public void update() {
		position.add(0.01f, 0.0f, 0.0f);
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public Vector3f getScale() {
		return scale;
	}

	public Mesh getMesh() {
		return mesh;
	}
}
