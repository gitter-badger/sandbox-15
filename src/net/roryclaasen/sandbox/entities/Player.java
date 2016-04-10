package net.roryclaasen.sandbox.entities;

import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity {

	private boolean turning = false;

	private boolean isInMenu = false;
	// TODO add menus

	private Camera camera;

	public Player(Vector3f position, float rotX, float rotY, float rotZ) {
		super(null, position, rotX, rotY, rotZ, 1F);
		hasHealth = false;
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
