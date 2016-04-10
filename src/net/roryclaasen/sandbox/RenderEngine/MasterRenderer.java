package net.roryclaasen.sandbox.RenderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.roryclaasen.sandbox.RenderEngine.models.TexturedModel;
import net.roryclaasen.sandbox.RenderEngine.shaders.StaticShader;
import net.roryclaasen.sandbox.RenderEngine.shaders.TerrainShader;
import net.roryclaasen.sandbox.RenderEngine.shaders.WaterShader;
import net.roryclaasen.sandbox.RenderEngine.skybox.Skybox;
import net.roryclaasen.sandbox.RenderEngine.water.WaterFrameBuffers;
import net.roryclaasen.sandbox.entities.Camera;
import net.roryclaasen.sandbox.entities.Entity;
import net.roryclaasen.sandbox.entities.EntityManager;
import net.roryclaasen.sandbox.entities.light.Light;
import net.roryclaasen.sandbox.terrain.Terrain;
import net.roryclaasen.sandbox.terrain.TerrainManager;
import net.roryclaasen.sandbox.util.Loader;
import net.roryclaasen.sandbox.util.config.Config;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class MasterRenderer {
	private static final float FOV = Config.fov.getFloat();
	private static final float PLAIN_NEAR = 0.01F;
	private static final float PLAIN_FAR = 1000F;

	private Loader loader;

	private Vector3f fogColour = Skybox.getFogColour();

	private Matrix4f projectionMatrix;

	private StaticShader shader = new StaticShader();
	private EntityRenderer entityRenderer;

	private TerrainShader terrainShader = new TerrainShader();
	private TerrainRender terrainRenderer;

	private SkyboxRenderer skyboxRenderer;

	private WaterShader waterShader = new WaterShader();
	private WaterRenderer waterRenderer;

	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();

	public MasterRenderer(Loader loader) {
		this.loader = loader;
		enableCulling();
		createProjectionMatrix();
		entityRenderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRender(terrainShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public void renderScene(EntityManager entityManager, TerrainManager terrainManager, Camera camera, Vector4f clipPlane) {
		fogColour = Skybox.getFogColour();
		for (Terrain terrain : terrainManager.getTerrains()) {
			processTerrain(terrain);
		}
		entityManager.render(this);

		render(entityManager.getNearestLights(), camera, clipPlane);
	}

	public void setUpWaterRenderer(WaterFrameBuffers buffers) {
		waterRenderer = new WaterRenderer(loader, waterShader, projectionMatrix, buffers);
	}

	public void renderWater(TerrainManager terrainManager, Camera camera) {
		waterRenderer.render(terrainManager.getWaters(), camera);
	}

	public void render(List<Light> lights, Camera camera, Vector4f clipPlane) {
		prepare();
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyClourVariable(fogColour.x, fogColour.y, fogColour.z);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		entityRenderer.render(entities);
		shader.stop();
		terrainShader.start();
		terrainShader.loadClipPlane(clipPlane);
		terrainShader.loadSkyColorVariable(fogColour.x, fogColour.y, fogColour.z);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		skyboxRenderer.render(camera, fogColour.x, fogColour.y, fogColour.z);

		entities.clear();
		terrains.clear();
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(fogColour.x, fogColour.y, fogColour.z, 1);
	}

	public void createProjectionMatrix() {
		float aspectRation = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1F / Math.tan(Math.toRadians(FOV) / 2F)) * aspectRation);
		float x_scale = y_scale / aspectRation;
		float frustum_lenght = PLAIN_FAR - PLAIN_NEAR;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((PLAIN_FAR + PLAIN_NEAR) / frustum_lenght);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * PLAIN_NEAR * PLAIN_FAR) / frustum_lenght);
		projectionMatrix.m33 = 0;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if (batch != null) batch.add(entity);
		else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}

	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
		waterShader.cleanUp();
	}

}
