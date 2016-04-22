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
import net.roryclaasen.sandbox.RenderEngine.Fbo;
import net.roryclaasen.sandbox.RenderEngine.MasterRenderer;
import net.roryclaasen.sandbox.RenderEngine.font.TextMaster;
import net.roryclaasen.sandbox.RenderEngine.gui.GuiRenderer;
import net.roryclaasen.sandbox.RenderEngine.particle.ParticleMaster;
import net.roryclaasen.sandbox.RenderEngine.post.PostProcessing;
import net.roryclaasen.sandbox.RenderEngine.skybox.Skybox;
import net.roryclaasen.sandbox.entities.Camera;
import net.roryclaasen.sandbox.entities.EntityManager;
import net.roryclaasen.sandbox.guis.DebugInfo;
import net.roryclaasen.sandbox.guis.GuiManager;
import net.roryclaasen.sandbox.level.LevelLoader;
import net.roryclaasen.sandbox.models.ModelLoader;
import net.roryclaasen.sandbox.state.GameStateManager;
import net.roryclaasen.sandbox.terrain.TerrainManager;
import net.roryclaasen.sandbox.util.Arguments;
import net.roryclaasen.sandbox.util.Loader;
import net.roryclaasen.sandbox.util.MousePicker;
import net.roryclaasen.sandbox.util.TextureUtil;
import net.roryclaasen.sandbox.util.WorldUtil;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Sandbox {

	private static Sandbox sandbox;
	private static Arguments arguments;

	public int currentFrames, currentUpdates;
	private boolean running = false;

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

		Languages.setFromConfig();

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

		ModelLoader.init(loader);
		Log.info("Initializing... DONE");

		gameStateManager.setState(GameStateManager.State.GAME);
	}

	public void start() {
		running = true;
		display.createDisplay();
		init();

		DebugInfo.add("fps", 0, currentFrames + " :fps");
		PostProcessing.init(loader);
		run();
		close();
	}

	private void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int updates = 0;
		int frames = 0;
		int passes = 0;

		Log.info("Started Rendering");

		// Mouse.setCursorPosition(DisplayManager.getWidth() / 2, DisplayManager.getHeight() / 2);
		while (running) {
			if (!Display.isCloseRequested()) {
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
					DebugInfo.update("fps", currentFrames + " :fps");
					if (arguments.isRunningAsCI()) {
						passes++;
						if (passes > 5) {
							Log.info("Rendered for " + (60 * passes) + " updates");
							close();

						}
					}
				}
			}
		}
	}

	public void close() {
		if (running) {
			running = false;
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
				Log.warn("CleanUp... Failed");
				Log.stackTrace(e);
			}
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
