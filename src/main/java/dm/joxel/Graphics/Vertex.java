package dm.joxel.Graphics;

import dm.joxel.Maths.Vector2f;
import dm.joxel.Maths.Vector3f;

public class Vertex {
	private Vector3f position, color;
	private Vector2f textureCoords;	

	public Vertex(Vector3f position, Vector3f color, Vector2f textureCoords) {
		this.position = position;
		this.color = color;
		this.textureCoords = textureCoords;
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getColor() {
		return color;
	}

	public Vector2f getTextureCoords() {
		return textureCoords;
	}
}
