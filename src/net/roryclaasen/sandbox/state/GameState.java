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

import net.roryclaasen.sandbox.DisplayManager;
import net.roryclaasen.sandbox.RenderEngine.Fbo;
import net.roryclaasen.sandbox.RenderEngine.MasterRenderer;
import net.roryclaasen.sandbox.RenderEngine.gui.GuiRenderer;
import net.roryclaasen.sandbox.RenderEngine.skybox.Skybox;
import net.roryclaasen.sandbox.entities.EntityManager;
import net.roryclaasen.sandbox.guis.GuiManager;
import net.roryclaasen.sandbox.terrain.TerrainManager;
import net.roryclaasen.sandbox.util.Loader;
import net.roryclaasen.sandbox.util.MousePicker;
import net.roryclaasen.sandbox.util.WorldUtil;

public abstract class GameState {

	protected GameStateManager gsm;

	protected Loader loader;
	protected DisplayManager display;
	protected GuiRenderer rendererGui;
	protected GuiManager guiManager;
	protected MasterRenderer renderer;
	protected EntityManager entityManager;
	protected TerrainManager terrainManager;
	protected Skybox skybox;

	protected MousePicker mousePicker;
	protected WorldUtil worldUtil;
	protected Fbo fbo;

	public GameState(GameStateManager stateManager) {
		this.gsm = stateManager;

		this.loader = this.gsm.getSandbox().loader;
		this.display = this.gsm.getSandbox().display;
		this.rendererGui = this.gsm.getSandbox().rendererGui;
		this.guiManager = this.gsm.getSandbox().guiManager;
		this.renderer = this.gsm.getSandbox().renderer;
		this.entityManager = this.gsm.getSandbox().entityManager;
		this.terrainManager = this.gsm.getSandbox().terrainManager;
		this.skybox = this.gsm.getSandbox().skybox;

		this.mousePicker = this.gsm.getSandbox().mousePicker;
		this.worldUtil = this.gsm.getSandbox().worldUtil;
		
		this.fbo = this.gsm.getSandbox().fbo;
	}

	public abstract void init();

	public abstract void render();

	public abstract void update();

	public abstract void reset();

	public abstract void cleanUp();
}
