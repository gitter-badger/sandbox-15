/** 
   Copyright 2016 Rory Claasen

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

import net.roryclaasen.sandbox.RenderEngine.water.WaterFrameBuffers;
import net.roryclaasen.sandbox.RenderEngine.water.WaterTile;
import net.roryclaasen.sandbox.entities.Camera;
import net.roryclaasen.sandbox.entities.Player;
import net.roryclaasen.sandbox.entities.light.Light;
import net.roryclaasen.sandbox.level.EntityData;
import net.roryclaasen.sandbox.util.MousePicker;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class GameStateLevel extends GameState {

	private Player player;
	private Camera camera;

	private WaterFrameBuffers buffers;

	public GameStateLevel(GameStateManager stateManager) {
		super(stateManager);

		buffers = new WaterFrameBuffers();
		renderer.setUpWaterRenderer(buffers);
	}

	@Override
	public void init() {
		gsm.getSandbox().levelLoader.loadLevel("test");
		EntityData pData = gsm.getSandbox().levelLoader.getPlayerData();
		// Player
		player = new Player(new Vector3f(384, 0, 384), pData.getRotationX(), pData.getRotationY(), pData.getRotationZ());
		camera = new Camera(player);
		player.setCamera(camera);
		camera.setPitch(20f);

		entityManager.addPlayer(player);

		// Light
		Light lightSun = new Light(new Vector3f(player.getX(), player.getY() + 100, player.getZ()), new Vector3f(1f, 1f, 1f));
		entityManager.addSun(lightSun);

		for (int x = 1; x < 8; x++) {
			for (int y = 1; y < 8; y++) {
				terrainManager.addWater(new WaterTile(x * 100, y * 100, -1));
			}
		}

		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());

		skybox.start();
	}

	@Override
	public void render() {
		GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
		buffers.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - terrainManager.getWaters().get(0).getHeight());
		camera.getPosition().y -= distance;
		camera.invertPitch();
		renderer.renderScene(entityManager, terrainManager, camera, new Vector4f(0, 1, 0, -terrainManager.getWaters().get(0).getHeight()));
		camera.getPosition().y += distance;
		camera.invertPitch();
		buffers.bindRefractionFrameBuffer();
		renderer.renderScene(entityManager, terrainManager, camera, new Vector4f(0, -1, 0, terrainManager.getWaters().get(0).getHeight()));
		
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		buffers.unbindCurrentFrameBuffer();
		renderer.renderScene(entityManager, terrainManager, camera, new Vector4f(0, -1, 0, 100000));
		renderer.renderWater(terrainManager, camera);

		if (player.isInMenu()) {
			// TODO draw a menu of some sort
		}
	}

	@Override
	public void update() {
		if (!player.isInMenu()) {
			skybox.update(entityManager);
		}
		camera.move();
		worldUtil.update(camera);
		mousePicker.update();
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
