package dm.joxel.Graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.*;

public class Mesh {
	private Vertex[] vertices;
	private int[] indices;
	private Material material;
	private int vao, pbo, ibo, cbo, tbo;

	public Mesh(Vertex[] vertices, int[] indices, Material material) {
		this.vertices = vertices;
		this.indices = indices;
		this.material = material;
	}

	public void create() {
		material.create();

		vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);

		FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);

		float[] positionData = new float[vertices.length * 3];
		for (int i = 0; i < vertices.length; i++) {
			positionData[i * 3] = vertices[i].getPosition().x;
			positionData[i * 3 + 1] = vertices[i].getPosition().y;
			positionData[i * 3 + 2] = vertices[i].getPosition().z;
		}
		positionBuffer.put(positionData).flip();

		pbo = storeData(positionBuffer, 0, 3);
		
		FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
		float[] colorData = new float[vertices.length * 3];
		for (int i = 0; i < vertices.length; i++) {
			colorData[i * 3] = vertices[i].getColor().x;
			colorData[i * 3 + 1] = vertices[i].getColor().y;
			colorData[i * 3 + 2] = vertices[i].getColor().z;
		}
		colorBuffer.put(colorData).flip();
		
		cbo = storeData(colorBuffer, 1, 3);

		FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
		float[] textureData = new float[vertices.length * 2];
		for (int i = 0; i < vertices.length; i++) {
			textureData[i * 2] = vertices[i].getTextureCoords().x;
			textureData[i * 2 + 1] = vertices[i].getTextureCoords().y;
		}
		textureBuffer.put(textureData).flip();
		
		tbo = storeData(textureBuffer, 2, 2);
		
		IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
		indicesBuffer.put(indices).flip();

		ibo = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	private int storeData(FloatBuffer buffer, int index, int size) {
		int bufferId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		return bufferId;
	}

	public void destroy() {
		GL15.glDeleteBuffers(pbo);
		GL15.glDeleteBuffers(cbo);
		GL15.glDeleteBuffers(ibo);
		GL15.glDeleteBuffers(tbo);
		
		material.destroy();

		GL30.glDeleteVertexArrays(vao);
	}
	public Vertex[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public Material getMaterial() {
		return material;
	}

	public int getVAO() {
		return vao;
	}

	public int getPBO() {
		return pbo;
	}

	public int getIBO() {
		return ibo;
	}

	public int getCBO() {
		return cbo;
	}

	public int getTBO() {
		return tbo;
	}
}
