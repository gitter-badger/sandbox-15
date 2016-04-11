package net.roryclaasen.sandbox.RenderEngine.shaders;

import net.roryclaasen.sandbox.DisplayManager;
import net.roryclaasen.sandbox.entities.Camera;
import net.roryclaasen.sandbox.util.Maths;
import net.roryclaasen.sandbox.util.config.Config;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class SkyboxShader extends ShaderProgram {

	private static final String VERT_FILE = "skybox.vert", FRAG_FILE = "skybox.frag";

	private static final float ROTATION_SPEED = Config.skyRotate.getFloat();

	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_fogColour;
	private int location_blendFactor;
	private int location_cubemap1;
	private int location_cubemap2;

	private float rotation = 0;

	public SkyboxShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_fogColour = super.getUniformLocation("fogColour");
		location_blendFactor = super.getUniformLocation("blendFactor");
		location_cubemap1 = super.getUniformLocation("cubeMap1");
		location_cubemap2 = super.getUniformLocation("cubeMap2");
	}
	
	public void connectTextureUnits(){
		super.loadInt(location_cubemap1, 0);
		super.loadInt(location_cubemap2, 1);
	}
	
	public void loadBlendFactor(float factor){
		super.loadFloat(location_blendFactor, factor);
	}

	public void loadFogColour(float r, float g, float b) {
		super.loadVector(location_fogColour, new Vector3f(r, g, b));
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}

	public void loadViewMatrix(Camera camera) {
		Matrix4f matrix = Maths.createViewMatrix(camera);
		matrix.m30 = 0;
		matrix.m31 = 0;
		matrix.m32 = 0;
		rotation += ROTATION_SPEED * DisplayManager.getFrameTimeSeconds();
		Matrix4f.rotate((float) Math.toRadians(rotation), new Vector3f(0, 1, 0), matrix, matrix);
		super.loadMatrix(location_viewMatrix, matrix);
	}
}