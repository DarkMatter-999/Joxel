package dm.joxel.Graphics;

import de.matthiasmann.twl.utils.PNGDecoder;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;

public class Material {
	String path;
	int textureId;
	private float width, height;
	public Material(String path) {
		this.path = path;
	}

	public void create() {
		try {
			PNGDecoder decoder = new PNGDecoder(new FileInputStream(path));

			ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
			decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
			buf.flip();

			width = decoder.getWidth();
			height = decoder.getHeight();

			int id = GL11.glGenTextures();

	        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

		    GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

			GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
	        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

		    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

			GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

	        textureId = id;
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not open texture : " + path);
		}
	}

	public void destroy() {
		GL13.glDeleteTextures(textureId);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public int getTextureId() {
		return textureId;
	}
}
