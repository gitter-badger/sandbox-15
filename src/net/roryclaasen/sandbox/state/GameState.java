package net.roryclaasen.sandbox.state;

import net.roryclaasen.sandbox.RenderEngine.DisplayManager;
import net.roryclaasen.sandbox.RenderEngine.GuiRenderer;
import net.roryclaasen.sandbox.RenderEngine.MasterRenderer;
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
	}

	public abstract void init();

	public abstract void render();

	public abstract void update();

	public abstract void reset();

	public abstract void cleanUp();
}
