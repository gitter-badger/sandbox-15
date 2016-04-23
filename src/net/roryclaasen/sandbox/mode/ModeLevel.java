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
package net.roryclaasen.sandbox.mode;

import net.roryclaasen.sandbox.GameMaster;
import net.roryclaasen.sandbox.Sandbox;
import net.roryclaasen.sandbox.RenderEngine.particle.ParticleMaster;
import net.roryclaasen.sandbox.RenderEngine.post.PostProcessing;
import net.roryclaasen.sandbox.RenderEngine.terrain.water.WaterFrameBuffers;
import net.roryclaasen.sandbox.entities.Entity;
import net.roryclaasen.sandbox.entities.Player;
import net.roryclaasen.sandbox.entities.light.Light;
import net.roryclaasen.sandbox.guis.DebugInfo;
import net.roryclaasen.sandbox.level.loader.EntityData;
import net.roryclaasen.sandbox.models.Models;
import net.roryclaasen.sandbox.terrain.Terrain;
import net.roryclaasen.sandbox.terrain.TerrainManager;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class ModeLevel extends ModeBase {

	private Player player;

	private WaterFrameBuffers buffers;

	public ModeLevel(Sandbox sandbox, GameMaster gameMaster) {
		super(sandbox, gameMaster);

		buffers = new WaterFrameBuffers();
		_sand.renderer.setUpWaterRenderer(buffers);
	}

	@Override
	public void init() {
		_sand.levelLoader.newLevel();

		EntityData playerData = _sand.levelLoader.getPlayerData();
		player = new Player(new Vector3f(Terrain.getSize() / 2, 300, Terrain.getSize() / 2), playerData.getRotationX(), playerData.getRotationY(), playerData.getRotationZ());
		player.setCamera(_sand.camera);
		_sand.camera.setPlayer(player);

		_sand.entityManager.addPlayer(player);

		addSun();
		for (int xt = 0; xt < 2; xt++) {
			for (int zt = 0; zt < 2; zt++) {
				for (int q = 0; q < 75; q++) {
					float x = xt;
					float z = zt;
					float y = -1;
					while (y <= -1) {
						x = (xt * Terrain.getSize()) + (random.nextInt((int) Terrain.getSize()));
						z = (zt * Terrain.getSize()) + (random.nextInt((int) Terrain.getSize()));
						y = TerrainManager.getCurrentTerrain(x, z).getHeightOfTerrain(x, z) - 0.2f;
					}
					Vector3f position = new Vector3f(x, y, z);
					_sand.entityManager.add(new Entity(Models.tree.get(), position, 0, 0, 0, 1.5f));
				}
			}
		}

		_sand.skybox.start();
		DebugInfo.add("seed", 1, "seed: " + _sand.levelLoader.getWorldData().getSeed());
	}

	private void addSun() {
		Light lightSun = new Light(new Vector3f(Terrain.getSize() / 2, 400, Terrain.getSize() / 2), new Vector3f(1f, 1f, 1f));
		lightSun.getPosition().translate(Terrain.getSize() / 6, 0, Terrain.getSize() / 6);
		lightSun.getPosition().scale(1000);
		_sand.entityManager.addSun(lightSun);
	}

	@Override
	public void render() {
		_sand.renderer.renderShadowMap(_sand.entityManager.getEntities(), _sand.entityManager.getSun());

		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		buffers.bindReflectionFrameBuffer();
		float distance = 2 * (_sand.camera.getPosition().y - _sand.terrainManager.getWaters().get(0).getHeight());
		_sand.camera.getPosition().y -= distance;
		_sand.camera.invertPitch();
		_sand.renderer.renderScene(_sand.entityManager, _sand.terrainManager, _sand.camera, new Vector4f(0, 1, 0, -_sand.terrainManager.getWaters().get(0).getHeight() + 1f));
		_sand.camera.getPosition().y += distance;
		_sand.camera.invertPitch();
		buffers.bindRefractionFrameBuffer();
		_sand.renderer.renderScene(_sand.entityManager, _sand.terrainManager, _sand.camera, new Vector4f(0, -1, 0, _sand.terrainManager.getWaters().get(0).getHeight() + 1f));

		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		buffers.unbindCurrentFrameBuffer();

		_sand.fbo.bindFrameBuffer();
		_sand.renderer.renderScene(_sand.entityManager, _sand.terrainManager, _sand.camera, new Vector4f(0, -1, 0, 100000));
		_sand.renderer.renderWater(_sand.terrainManager, _sand.camera, _sand.entityManager.getSun());

		ParticleMaster.renderParticles(_sand.camera);
		_sand.fbo.unbindFrameBuffer();
		PostProcessing.doPostProcessing(_sand.fbo.getColourTexture());
	}

	@Override
	public void tick(float delta) {
		if (!_master.menu.hasFocus()) {
			_sand.skybox.tick(_sand.entityManager);
			player.tick(delta);
			_sand.camera.move();

		}
		_sand.worldUtil.tick(_sand.camera);
		_sand.mousePicker.tick();
		ParticleMaster.tick();
	}

	@Override
	public void cleanUp() {
		buffers.cleanUp();
	}

}
