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
package net.roryclaasen.sandbox.state;

import java.util.Random;

import net.roryclaasen.sandbox.Sandbox;
import net.roryclaasen.sandbox.RenderEngine.font.GUIText;
import net.roryclaasen.sandbox.RenderEngine.font.TextMaster;
import net.roryclaasen.sandbox.RenderEngine.particle.ParticleMaster;
import net.roryclaasen.sandbox.RenderEngine.particle.ParticleSystem;
import net.roryclaasen.sandbox.RenderEngine.particle.ParticleTexture;
import net.roryclaasen.sandbox.RenderEngine.post.PostProcessing;
import net.roryclaasen.sandbox.RenderEngine.terrain.water.WaterFrameBuffers;
import net.roryclaasen.sandbox.RenderEngine.terrain.water.WaterTile;
import net.roryclaasen.sandbox.entities.Camera;
import net.roryclaasen.sandbox.entities.Entity;
import net.roryclaasen.sandbox.entities.Player;
import net.roryclaasen.sandbox.entities.light.Light;
import net.roryclaasen.sandbox.level.loader.EntityData;
import net.roryclaasen.sandbox.models.Models;
import net.roryclaasen.sandbox.terrain.Terrain;
import net.roryclaasen.sandbox.terrain.TerrainManager;
import net.roryclaasen.sandbox.util.MousePicker;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class GameStateLevel extends GameState {

	private Player player;
	private Camera camera;

	private WaterFrameBuffers buffers;

	private Random random = new Random();

	private GUIText seed;

	public GameStateLevel(GameStateManager stateManager) {
		super(stateManager);

		buffers = new WaterFrameBuffers();
		renderer.setUpWaterRenderer(buffers);
	}

	ParticleSystem system;

	@Override
	public void init() {
		gsm.getSandbox().levelLoader.newLevel();
		EntityData playerData = gsm.getSandbox().levelLoader.getPlayerData();
		// Player
		player = new Player(new Vector3f(Terrain.getSize() / 2, 300, Terrain.getSize() / 2), playerData.getRotationX(), playerData.getRotationY(), playerData.getRotationZ());
		camera = gsm.getSandbox().camera;
		camera.setPlayer(player);
		player.setCamera(gsm.getSandbox().camera);

		entityManager.addPlayer(player);

		// Light
		addDefults();

		for (int i = 0; i < 100; i++) {
			float x = 0;
			float z = 0;
			float y = -1;
			while (y <= -1) {
				x = random.nextInt((int) Terrain.getSize());
				z = random.nextInt((int) Terrain.getSize());
				y = TerrainManager.getCurrentTerrain(x, z).getHeightOfTerrain(x, z) - 0.2f;
			}
			Vector3f position = new Vector3f(x, y, z);
			entityManager.add(new Entity(Models.tree.get(), position, 0, 0, 0, 1.5f));
		}

		for (int x = 1; x < Terrain.getSize() / WaterTile.TILE_SIZE; x++) {
			for (int y = 1; y < Terrain.getSize() / WaterTile.TILE_SIZE; y++) {
				terrainManager.addWater(new WaterTile(x * 100, y * 100, -1));
			}
		}

		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());

		skybox.start();
		ParticleTexture pTexture = new ParticleTexture(loader.loadTexture("particles/star"), 1);
		system = new ParticleSystem(pTexture, 40, 10, 0.1f, 1, 1.6f);

		seed = new GUIText("seed: " + gsm.getSandbox().levelLoader.getWorldData().getSeed(), 1, TextMaster.sans, new Vector2f(0f, 0.025f), 1F, false);
		seed.setColor(0F, 1F, 0F);
		seed.border(Sandbox.DEBUG_EFFECT);
	}

	private void addDefults() {
		Light lightSun = new Light(new Vector3f(Terrain.getSize() / 2, 400, Terrain.getSize() / 2), new Vector3f(1f, 1f, 1f));
		lightSun.getPosition().translate(Terrain.getSize() / 6, 0, Terrain.getSize() / 6);
		lightSun.getPosition().scale(1000);
		entityManager.addSun(lightSun);
	}

	@Override
	public void render() {
		renderer.renderShadowMap(entityManager.getEntities(), entityManager.getSun());

		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		buffers.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - terrainManager.getWaters().get(0).getHeight());
		camera.getPosition().y -= distance;
		camera.invertPitch();
		renderer.renderScene(entityManager, terrainManager, camera, new Vector4f(0, 1, 0, -terrainManager.getWaters().get(0).getHeight() + 1f));
		camera.getPosition().y += distance;
		camera.invertPitch();
		buffers.bindRefractionFrameBuffer();
		renderer.renderScene(entityManager, terrainManager, camera, new Vector4f(0, -1, 0, terrainManager.getWaters().get(0).getHeight() + 1f));

		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		buffers.unbindCurrentFrameBuffer();

		fbo.bindFrameBuffer();
		renderer.renderScene(entityManager, terrainManager, camera, new Vector4f(0, -1, 0, 100000));
		renderer.renderWater(terrainManager, camera, entityManager.getSun());

		ParticleMaster.renderParticles(camera);
		fbo.unbindFrameBuffer();
		PostProcessing.doPostProcessing(fbo.getColourTexture());
		
		if (player.isInMenu()) {
			// TODO draw a menu of some sort
		}
	}

	@Override
	public void update() {
		if (!player.isInMenu()) {
			skybox.update(entityManager);
			player.update();
			camera.move();
		}
		// system.generateParticles(player.getPosition());
		worldUtil.update(camera);
		mousePicker.update();
		ParticleMaster.update();
	}

	@Override
	public void reset() {
		player = null;
		camera = null;

		buffers = null;
	}

	@Override
	public void cleanUp() {
		buffers.cleanUp();
	}
}
