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
package net.roryclaasen.sandbox.entities;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import net.roryclaasen.sandbox.guis.DebugInfo;
import net.roryclaasen.sandbox.terrain.Terrain;
import net.roryclaasen.sandbox.terrain.TerrainManager;
import net.roryclaasen.sandbox.util.config.Config;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity {

	private boolean moving = false, turning = false;

	private float currentSpeedTurn = 0;

	private Camera camera;

	private Vector3f pos = new Vector3f(0, 0, 0);

	public Player(Vector3f position, float rotX, float rotY, float rotZ) {
		super(null, position, rotX, rotY, rotZ, 1F);
		hasHealth = false;
		DebugInfo.add("pos", getDebugXYZ());
	}

	@SuppressWarnings("unused")
	public void tick(float delta) {
		DebugInfo.update("pos", getDebugXYZ());
		Terrain terrain = TerrainManager.getCurrentTerrain(getX(), getZ());
		checkInputs();
		increaseRotation(0, currentSpeedTurn * delta, 0);

		increasePosition(pos.x, pos.y, pos.z);

		float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);

		pos = new Vector3f(0, 0, 0);
	}

	private String getDebugXYZ() {
		double x = this.getPosition().getX();
		double y = this.getPosition().getY();
		double z = this.getPosition().getZ();
		DecimalFormat df = new DecimalFormat("#.###");
		df.setRoundingMode(RoundingMode.CEILING);
		return "xyz:  " + df.format(x) + "  /  " + df.format(y) + "  /  " + df.format(z);
	}

	private void checkInputs() {
		moving = false;
		turning = false;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			moving = true;
			walkForward(Config.movingSpeed.get());
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			moving = true;
			walkBackwards(Config.movingSpeed.get());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			moving = true;
			strafeLeft(Config.movingSpeed.get());
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			moving = true;
			strafeRight(Config.movingSpeed.get());
		}
		if (!(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))) {
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				pos.y += Config.movingSpeed.get();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				pos.y -= Config.movingSpeed.get();
			}
		}
	}

	public void walkForward(float distance) {
		pos.x -= -distance * (float) Math.sin(Math.toRadians(camera.getYaw()));
		pos.z += -distance * (float) Math.cos(Math.toRadians(camera.getYaw()));
	}

	public void walkBackwards(float distance) {
		pos.x += -distance * (float) Math.sin(Math.toRadians(camera.getYaw()));
		pos.z -= -distance * (float) Math.cos(Math.toRadians(camera.getYaw()));
	}

	public void strafeLeft(float distance) {
		pos.x -= -distance * (float) Math.sin(Math.toRadians(camera.getYaw() - 90));
		pos.z += -distance * (float) Math.cos(Math.toRadians(camera.getYaw() - 90));
	}

	public void strafeRight(float distance) {
		pos.x -= -distance * (float) Math.sin(Math.toRadians(camera.getYaw() + 90));
		pos.z += -distance * (float) Math.cos(Math.toRadians(camera.getYaw() + 90));
	}

	public boolean isMoving() {
		return moving;
	}

	public boolean isTurning() {
		return turning;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Camera getCamera() {
		return camera;
	}
}
