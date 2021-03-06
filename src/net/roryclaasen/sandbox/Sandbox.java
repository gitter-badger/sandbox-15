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
import net.roryclaasen.sandbox.RenderEngine.gui.Splash;
import net.roryclaasen.sandbox.RenderEngine.particle.ParticleMaster;
import net.roryclaasen.sandbox.RenderEngine.post.PostProcessing;
import net.roryclaasen.sandbox.RenderEngine.skybox.Skybox;
import net.roryclaasen.sandbox.entities.Camera;
import net.roryclaasen.sandbox.entities.EntityManager;
import net.roryclaasen.sandbox.guis.GuiManager;
import net.roryclaasen.sandbox.level.LevelLoader;
import net.roryclaasen.sandbox.models.ModelLoader;
import net.roryclaasen.sandbox.terrain.TerrainManager;
import net.roryclaasen.sandbox.util.Arguments;
import net.roryclaasen.sandbox.util.DeltaUtil;
import net.roryclaasen.sandbox.util.Loader;
import net.roryclaasen.sandbox.util.MousePicker;
import net.roryclaasen.sandbox.util.TextureUtil;
import net.roryclaasen.sandbox.util.WorldUtil;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Sandbox {

	private static Sandbox sandbox;
	private static Arguments arguments;
	private static DeltaUtil delta;

	public DisplayManager display;
	public Loader loader;
	
	private Splash splash;

	public Camera camera;

	public Fbo fbo;

	public MasterRenderer renderer;
	public GuiRenderer rendererGui;

	public GuiManager guiManager;
	public EntityManager entityManager;
	public TerrainManager terrainManager;

	public LevelLoader levelLoader;
	public Skybox skybox;

	public MousePicker mousePicker;
	public WorldUtil worldUtil;
	
	public GameMaster gameMaster;
	
	private boolean running = false;

	public Sandbox(Arguments arguments) {
		Sandbox.sandbox = this;
		Sandbox.arguments = arguments;

		Languages.setFromConfig();

		delta = new DeltaUtil();
		display = new DisplayManager();
		loader = new Loader();
	}

	private void init() {
		Log.info("Initializing...");

		camera = new Camera();

		fbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_RENDER_BUFFER);

		renderer = new MasterRenderer(loader, camera);
		rendererGui = new GuiRenderer(loader);

		TextMaster.init(loader);
		TextureUtil.init(loader);
		ModelLoader.init(loader);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		guiManager = new GuiManager();
		entityManager = new EntityManager();
		terrainManager = new TerrainManager();

		levelLoader = new LevelLoader(sandbox);
		skybox = new Skybox();

		worldUtil = new WorldUtil(sandbox);
		mousePicker = new MousePicker(camera, renderer.getProjectionMatrix());

		gameMaster = new GameMaster(sandbox);
		gameMaster.init();
		Log.info("Initializing... DONE");
	}

	public void start() {
		if (arguments.isRunningAsCI()) {
			Log.info("Running as CI");
			Log.info("Skipping start");
		} else {
			running = true;
			display.createDisplay();
			
			splash = new Splash(loader, "512");
			splash.show();
			display.updateDisplay();
			init();
			delta.start();

			PostProcessing.init(loader);
			run();
			close();
		}
	}

	private void run() {
		Log.info("Started Rendering");
		while (running) {
			if (Display.isCloseRequested()) {
				Log.info("Close requested from window");
				running = false;
			} else {
				gameMaster.tick(DeltaUtil.getDelta());
				delta.update();
				GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
				gameMaster.render();
				display.updateDisplay();
			}
		}
	}

	public void close() {
		if (running) {
			running = false;
			try {
				loader.cleanUp();
				fbo.cleanUp();
				renderer.cleanUp();
				rendererGui.cleanUp();
				splash.cleanUp();
				TextMaster.cleanUp();
				ParticleMaster.cleanUp();
				PostProcessing.cleanUp();
				gameMaster.cleanUp();
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

	public static DeltaUtil delta() {
		return delta;
	}
}
