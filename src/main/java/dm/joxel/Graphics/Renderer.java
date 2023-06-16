package dm.joxel.Graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import dm.joxel.Objects.GameObject;
import dm.joxel.Maths.Matrix4f;

public class Renderer {
	private Shader shader;

	public Renderer(Shader shader) {
		this.shader = shader;
	}

	public void renderMesh(GameObject gameObject) {
		GL30.glBindVertexArray(gameObject.getMesh().getVAO());
		GL30.glEnableVertexAttribArray(0);
		GL30.glEnableVertexAttribArray(1);
		GL30.glEnableVertexAttribArray(2);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, gameObject.getMesh().getIBO());

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL13.glBindTexture(GL11.GL_TEXTURE_2D, gameObject.getMesh().getMaterial().getTextureId());

		shader.bind();
		shader.setUniform("model", Matrix4f.transform(gameObject.position, gameObject.rotation, gameObject.scale));
		GL11.glDrawElements(GL11.GL_TRIANGLES, gameObject.mesh.getIndices().length, GL11.GL_UNSIGNED_INT, 0);
		shader.unbind();

		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL30.glDisableVertexAttribArray(0);
		GL30.glDisableVertexAttribArray(1);
		GL30.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);	
	}
}
