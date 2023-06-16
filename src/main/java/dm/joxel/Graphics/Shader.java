package dm.joxel.Graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import dm.joxel.Utils.FileUtils;

public class Shader {
	private String vertexFile, fragmentFile;
	private int vertexId, fragmentId, programId;

	public Shader(String vertexPath, String fragmentPath) {
		vertexFile = FileUtils.loadAsString(vertexPath);
		fragmentFile = FileUtils.loadAsString(fragmentPath);
	}

	public void create() {
		programId = GL20.glCreateProgram();
		vertexId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);

		GL20.glShaderSource(vertexId, vertexFile);
		GL20.glCompileShader(vertexId);
		
		if (GL20.glGetShaderi(vertexId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Vertex Shader: " + GL20.glGetShaderInfoLog(vertexId));
			return;
		}

		fragmentId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		GL20.glShaderSource(fragmentId, fragmentFile);
		GL20.glCompileShader(fragmentId);
		
		if (GL20.glGetShaderi(fragmentId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Fragment Shader: " + GL20.glGetShaderInfoLog(fragmentId));
			return;
		}

		GL20.glAttachShader(programId, vertexId);
		GL20.glAttachShader(programId, fragmentId);
		
		GL20.glLinkProgram(programId);
		if (GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
			System.err.println("Program Linking: " + GL20.glGetProgramInfoLog(programId));
			return;
		}
		
		GL20.glValidateProgram(programId);
		if (GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
			System.err.println("Program Validation: " + GL20.glGetProgramInfoLog(programId));
			return;
		}
		
		GL20.glDeleteShader(vertexId);
		GL20.glDeleteShader(fragmentId);
	}

	public void bind() {
		GL20.glUseProgram(programId);
	}
	public void unbind() {
		GL20.glUseProgram(0);
	}
	public void destroy() {
		GL20.glDeleteProgram(programId);
	}
}
