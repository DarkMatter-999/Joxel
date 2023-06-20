package dm.joxel.Graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import java.util.*;

import dm.joxel.Objects.Camera;
import dm.joxel.Objects.GameObject;
import dm.joxel.Engine.Window;
import dm.joxel.Maths.Matrix4f;

public class Renderer {
	private Shader shader;
	private Window window;
	private float fov = 70.0f;

	public Renderer(Shader shader, Window window) {
		this.shader = shader;
		this.window = window;
	}

	public void renderMesh(GameObject gameObject, Camera camera) {
		GL30.glBindVertexArray(gameObject.getMesh().getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glEnableVertexAttribArray(2);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, gameObject.getMesh().getIBO());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, gameObject.getMesh().getMaterial().getTextureId());

		shader.bind();
		shader.setUniform("model", Matrix4f.transform(gameObject.position, gameObject.rotation, gameObject.scale));
		shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
		shader.setUniform("projection", Matrix4f.perspective(fov, (float) window.width / (float) window.height, 0.1f, 1000.0f));
		GL11.glDrawElements(GL11.GL_TRIANGLES, gameObject.mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shader.unbind();

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);	
	}

	public void renderObjects(List<GameObject> gameObjects, Camera camera) {
		GL30.glBindVertexArray(gameObjects.get(0).getMesh().getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glEnableVertexAttribArray(2);

		for(int i=0; i<gameObjects.size(); i++) {
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, gameObjects.get(i).getMesh().getIBO());

			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL13.glBindTexture(GL11.GL_TEXTURE_2D, gameObjects.get(i).getMesh().getMaterial().getTextureId());

			shader.bind();
			shader.setUniform("model", Matrix4f.transform(gameObjects.get(i).position, gameObjects.get(i).rotation, gameObjects.get(i).scale));
			shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
			shader.setUniform("projection", Matrix4f.perspective(fov, (float) window.width / (float) window.height, 0.1f, 1000.0f));
			GL11.glDrawElements(GL11.GL_TRIANGLES, gameObjects.get(i).mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
			shader.unbind();

		}
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);	
	}

}
