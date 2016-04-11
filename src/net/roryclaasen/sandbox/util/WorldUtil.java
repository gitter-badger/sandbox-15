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
package net.roryclaasen.sandbox.util;

import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.DisplayManager;
import net.roryclaasen.sandbox.Sandbox;
import net.roryclaasen.sandbox.RenderEngine.skybox.Skybox;
import net.roryclaasen.sandbox.entities.Camera;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class WorldUtil {
	private boolean wireFrame = false;
	private Sandbox game;

	public WorldUtil(Sandbox game) {
		this.game = game;
	}

	public void update(Camera camera) {
		if (wireFrame) GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		else GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			game.entityManager.getPlayer().setInMenu(true);
			Mouse.setCursorPosition(DisplayManager.getWidth() / 2, DisplayManager.getHeight() / 2);
		}
		if (Mouse.isButtonDown(0) && game.entityManager.getPlayer().isInMenu()) game.entityManager.getPlayer().setInMenu(false);

		if (Sandbox.getArguments().isDebugMode()) {
			Log.info("Lol");
			if (Keyboard.isKeyDown(Keyboard.KEY_F1)) setWireFrame(false);
			if (Keyboard.isKeyDown(Keyboard.KEY_F2)) setWireFrame(true);
			if (Keyboard.isKeyDown(Keyboard.KEY_F9)) Skybox.setDay();
			if (Keyboard.isKeyDown(Keyboard.KEY_F10)) Skybox.setNight(true);
		}
	}

	public boolean isWireFrame() {
		return wireFrame;
	}

	public void setWireFrame(boolean wireFrame) {
		this.wireFrame = wireFrame;
	}
}
