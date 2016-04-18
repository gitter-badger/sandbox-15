/* Copyright 2016 Rory Claasen

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.roryclaasen.sandbox.RenderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.roryclaasen.sandbox.RenderEngine.entity.EntityRenderer;
import net.roryclaasen.sandbox.RenderEngine.models.TexturedModel;
import net.roryclaasen.sandbox.RenderEngine.shaddow.ShadowMapMasterRenderer;
import net.roryclaasen.sandbox.RenderEngine.shaders.StaticShader;
import net.roryclaasen.sandbox.RenderEngine.skybox.Skybox;
import net.roryclaasen.sandbox.RenderEngine.skybox.SkyboxRenderer;
import net.roryclaasen.sandbox.RenderEngine.terrain.TerrainRenderer;
import net.roryclaasen.sandbox.RenderEngine.terrain.TerrainShader;
import net.roryclaasen.sandbox.RenderEngine.terrain.water.WaterFrameBuffers;
import net.roryclaasen.sandbox.RenderEngine.terrain.water.WaterRenderer;
import net.roryclaasen.sandbox.RenderEngine.terrain.water.WaterShader;
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
import org.lwjgl.opengl.GL13;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class MasterRenderer {

	public static final float FOV = Config.fov.getFloat();
	public static final float PLAIN_NEAR = 0.01F;
	public static final float PLAIN_FAR = 1000F;

	private Loader loader;

	private Vector3f fogColour = Skybox.getFogColour();

	private Matrix4f projectionMatrix;

	private StaticShader shader = new StaticShader();
	private EntityRenderer entityRenderer;

	private TerrainShader terrainShader = new TerrainShader();
	private TerrainRenderer terrainRenderer;

	private SkyboxRenderer skyboxRenderer;

	private WaterShader waterShader = new WaterShader();
	private WaterRenderer waterRenderer;

	private ShadowMapMasterRenderer shadowMapRenderer;

	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();

	public MasterRenderer(Loader loader, Camera camera) {
		this.loader = loader;
		enableCulling();
		createProjectionMatrix();
		entityRenderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
		shadowMapRenderer = new ShadowMapMasterRenderer(camera);
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

	public void renderWater(TerrainManager terrainManager, Camera camera, Light sun) {
		waterRenderer.render(terrainManager.getWaters(), camera, sun);
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
		terrainRenderer.render(terrains, shadowMapRenderer.getToShadowMapSpaceMatrix());
		terrainShader.stop();
		skyboxRenderer.render(camera, fogColour.x, fogColour.y, fogColour.z);

		entities.clear();
		terrains.clear();
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(fogColour.x, fogColour.y, fogColour.z, 1);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowmapTexture());
	}

	public void createProjectionMatrix() {
		projectionMatrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = PLAIN_FAR - PLAIN_NEAR;

		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((PLAIN_FAR + PLAIN_NEAR) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * PLAIN_NEAR * PLAIN_FAR) / frustum_length);
		projectionMatrix.m33 = 0;
	}

	public Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	public void renderShadowMap(List<Entity> entityList, Light sun) {
		for (Entity entity : entityList) {
			processEntity(entity);
		}
		shadowMapRenderer.render(entities, sun);
		entities.clear();
	}

	public int getShadowmapTexture() {
		return shadowMapRenderer.getShadowMap();
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
		shadowMapRenderer.cleanUp();
	}

}
