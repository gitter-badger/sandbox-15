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
package net.roryclaasen.sandbox.util;

import net.roryclaasen.sandbox.DisplayManager;
import net.roryclaasen.sandbox.Sandbox;
import net.roryclaasen.sandbox.entities.Camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class WorldUtil {

	public static final float GRAVITY = -50f;
	private boolean wireFrame = false;
	private Sandbox _sand;

	public WorldUtil(Sandbox sandbox) {
		this._sand = sandbox;
	}

	public void updateWireFrame(boolean frame) {
		if (frame) {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		} else {
			GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		}
	}

	public void tick(Camera camera) {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			_sand.gameMaster.menu.setFocus(true);
			Mouse.setCursorPosition(DisplayManager.getWidth() / 2, DisplayManager.getHeight() / 2);
			Mouse.setGrabbed(false);
		}
		if (Mouse.isButtonDown(0) && _sand.gameMaster.menu.hasFocus()) {
			_sand.gameMaster.menu.setFocus(false);
			Mouse.setGrabbed(true);
		}

		if (Sandbox.getArguments().isDebugMode()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_F1)) setWireFrame(false);
			if (Keyboard.isKeyDown(Keyboard.KEY_F2)) setWireFrame(true);
		}

		updateWireFrame(wireFrame);
	}

	public boolean isWireFrame() {
		return wireFrame;
	}

	public void setWireFrame(boolean wireFrame) {
		this.wireFrame = wireFrame;
	}
}
