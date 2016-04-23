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
package net.roryclaasen.sandbox;

import org.lwjgl.input.Mouse;

import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.RenderEngine.font.TextMaster;
import net.roryclaasen.sandbox.mode.ModeLevel;
import net.roryclaasen.sandbox.mode.ModeMenu;

public class GameMaster {

	private Sandbox _sand;

	public ModeMenu menu;
	public ModeLevel level;

	public GameMaster(Sandbox sandbox) {
		Log.info("[GAMEMASTER] Starting");
		this._sand = sandbox;
		level = new ModeLevel(_sand, this);
		menu = new ModeMenu(_sand, this);
	}

	public void init() {
		Log.info("[GAMEMASTER] Initializing...");
		level.init();
		menu.init();
		menu.setFocus(false);
		Mouse.setGrabbed(true);
		Log.info("[GAMEMASTER] Initializing... DONE");
	}

	public void render() {
		level.renderBase();
		_sand.worldUtil.updateWireFrame(false);
		_sand.rendererGui.render(_sand.guiManager.getGuis());
		menu.renderBase();
		TextMaster.render();
	}

	public void tick(float delta) {
		_sand.worldUtil.tick(_sand.camera);
		level.tickBase(delta);
		menu.tickBase(delta);
	}

	public void cleanUp() {
		Log.info("[GAMEMASTER] Clean up");
		level.cleanUp();
		menu.cleanUp();
	}
}
