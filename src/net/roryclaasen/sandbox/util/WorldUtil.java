package net.roryclaasen.sandbox.util;

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
