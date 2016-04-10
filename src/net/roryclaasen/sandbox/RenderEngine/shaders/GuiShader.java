package net.roryclaasen.sandbox.RenderEngine.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class GuiShader extends ShaderProgram {

	private static final String VERT_FILE = "gui.vert", FRAG_FILE = "gui.frag";
	private int location_transformationMatrix;

	public GuiShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
