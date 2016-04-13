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
package net.roryclaasen.sandbox.entities;

import net.roryclaasen.sandbox.DisplayManager;
import net.roryclaasen.sandbox.terrain.Terrain;
import net.roryclaasen.sandbox.terrain.TerrainManager;
import net.roryclaasen.sandbox.util.config.Config;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity {

	private boolean moving = false, turning = false;

	private boolean isInMenu = false;
	// TODO add menus
	private float currentSpeedTurn = 0;

	private Camera camera;

	private Vector3f pos = new Vector3f(0, 0, 0);

	public Player(Vector3f position, float rotX, float rotY, float rotZ) {
		super(null, position, rotX, rotY, rotZ, 1F);
		hasHealth = false;
	}

	@SuppressWarnings("unused")
	public void move() {
		Terrain terrain = TerrainManager.getCurrentTerrain(getX(), getZ());
		checkInputs();
		increaseRotation(0, currentSpeedTurn * DisplayManager.getFrameTimeSeconds(), 0);

		increasePosition(pos.x, pos.y, pos.z);

		float terrainHeight = terrain.getHeightOfTerrain(getPosition().x, getPosition().z);

		pos = new Vector3f(0, 0, 0);
	}

	private void checkInputs() {
		moving = false;
		turning = false;
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			moving = true;
			walkForward(Config.movingSpeed.getFloat());
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			moving = true;
			walkBackwards(Config.movingSpeed.getFloat());
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			moving = true;
			strafeLeft(Config.movingSpeed.getFloat());
		} else if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			moving = true;
			strafeRight(Config.movingSpeed.getFloat());
		}
		if (!(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))) {
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
				pos.y += Config.movingSpeed.getFloat();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				pos.y -= Config.movingSpeed.getFloat();
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

	public boolean isInMenu() {
		return isInMenu;
	}

	public void setInMenu(boolean inMenu) {
		isInMenu = inMenu;
	}
}
