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

import net.gogo98901.log.Log;
import net.roryclaasen.Bootstrap;
import net.roryclaasen.sandbox.RenderEngine.Fbo;
import net.roryclaasen.sandbox.RenderEngine.MasterRenderer;
import net.roryclaasen.sandbox.RenderEngine.font.BorderEffect;
import net.roryclaasen.sandbox.RenderEngine.font.GUIText;
import net.roryclaasen.sandbox.RenderEngine.font.TextMaster;
import net.roryclaasen.sandbox.RenderEngine.gui.GuiRenderer;
import net.roryclaasen.sandbox.RenderEngine.particle.ParticleMaster;
import net.roryclaasen.sandbox.RenderEngine.post.PostProcessing;
import net.roryclaasen.sandbox.RenderEngine.skybox.Skybox;
import net.roryclaasen.sandbox.entities.Camera;
import net.roryclaasen.sandbox.entities.EntityManager;
import net.roryclaasen.sandbox.guis.GuiManager;
import net.roryclaasen.sandbox.level.LevelLoader;
import net.roryclaasen.sandbox.models.Models;
import net.roryclaasen.sandbox.state.GameStateManager;
import net.roryclaasen.sandbox.terrain.TerrainManager;
import net.roryclaasen.sandbox.util.Arguments;
import net.roryclaasen.sandbox.util.Loader;
import net.roryclaasen.sandbox.util.MousePicker;
import net.roryclaasen.sandbox.util.TextureUtil;
import net.roryclaasen.sandbox.util.WorldUtil;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Sandbox {

	public static final BorderEffect DEBUG_EFFECT = new BorderEffect(new Vector3f(0.2f, 0.2f, 0.2f)).setBorderWidth(5f);

	private static Sandbox sandbox;
	private static Arguments arguments;

	public int currentFrames, currentUpdates;

	public Loader loader;
	public LevelLoader levelLoader;
	public DisplayManager display;
	private GameStateManager gameStateManager;

	public MasterRenderer renderer;
	public GuiRenderer rendererGui;
	public GuiManager guiManager;
	public EntityManager entityManager;
	public TerrainManager terrainManager;
	public Skybox skybox;

	public MousePicker mousePicker;
	public WorldUtil worldUtil;

	public Camera camera;

	public Fbo fbo;

	public Sandbox(Arguments arguments) {
		Sandbox.sandbox = this;
		Sandbox.arguments = arguments;

		display = new DisplayManager();
	}

	private void init() {
		Log.info("Initializing...");
		loader = new Loader();
		TextMaster.init(loader);
		TextureUtil.init(loader);
		camera = new Camera();

		renderer = new MasterRenderer(loader, camera);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		rendererGui = new GuiRenderer(loader);
		guiManager = new GuiManager();
		entityManager = new EntityManager();
		terrainManager = new TerrainManager(loader);
		skybox = new Skybox();
		worldUtil = new WorldUtil(this);
		levelLoader = new LevelLoader(this);
		fbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER);
		
		gameStateManager = new GameStateManager(this);

		Models.load(loader);
		Log.info("Initializing... DONE");

		gameStateManager.setState(GameStateManager.State.GAME);
	}

	public void start() {
		Log.info("Starting " + Bootstrap.TITLE);
		display.createDisplay();
		init();

		GUIText fps = new GUIText(currentFrames + " :fps", 1, TextMaster.sans, new Vector2f(0f, 0f), 1F, false);
		fps.setColor(0F, 1F, 0F);
		fps.border(DEBUG_EFFECT);
		PostProcessing.init(loader);
		{
			long lastTime = System.nanoTime();
			long timer = System.currentTimeMillis();
			final double ns = 1000000000.0 / 60.0;
			double delta = 0.0;
			int updates = 0;
			int frames = 0;

			Log.info("Started Rendering");

			// Mouse.setCursorPosition(DisplayManager.getWidth() / 2, DisplayManager.getHeight() / 2);
			while (!Display.isCloseRequested()) {
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

				long now = System.nanoTime();
				delta += (now - lastTime) / ns;
				lastTime = now;
				while (delta >= 1) {
					{// update
						gameStateManager.update();
					}
					updates++;
					delta--;
				}
				{// render
					worldUtil.render();
					gameStateManager.render();
					worldUtil.renderWireFrame(false);
					TextMaster.render();
					display.updateDisplay();
				}
				frames++;

				if (System.currentTimeMillis() - timer > 1000) {
					timer += 1000;
					currentFrames = frames;
					currentUpdates = updates;
					updates = 0;
					frames = 0;
					fps.update(currentFrames + " :fps", -1f, null, null, -1f, false, null);
				}
			}
		}
		close();
	}

	public void close() {
		try {
			fbo.cleanUp();
			PostProcessing.cleanUp();
			ParticleMaster.cleanUp();
			TextMaster.cleanUp();
			gameStateManager.cleanUp();
			renderer.cleanUp();
			rendererGui.cleanUp();
			loader.cleanUp();
			Log.info("CleanUp... OK");
		} catch (Exception e) {
			Log.info("CleanUp... Failed");
			Log.stackTrace(e);
		}
		display.destroyDisplay();

		Log.save();
	}

	public static Sandbox getSandbox() {
		return sandbox;
	}

	public static Arguments getArguments() {
		return arguments;
	}
}
