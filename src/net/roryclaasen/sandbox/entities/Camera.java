package net.roryclaasen.sandbox.entities;

import net.roryclaasen.sandbox.util.config.Config;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

	private Vector3f position;
	private float pitch, yaw, roll;
	private float currentZoom = 50, currentAngle = 0;
	private float minZoom = 10, maxZoom = 700;

	private float mouseSensitivity = Config.sensitivity.getFloat();

	private Player player;

	public Camera(Player player) {
		this.player = player;
		position = new Vector3f(player.getPosition().x, player.getPosition().y + 50, player.getPosition().z);
		pitch = 20f;
	}

	public void move() {
		if (!player.isInMenu()) {
			calculateZoom();
			calcuatePitch();
			calcuateAngleArroundPlayer();

			float dx = Mouse.getDX();
			float dy = Mouse.getDY();

			yaw(dx * mouseSensitivity);
			pitch(dy * mouseSensitivity);

			if (!player.isInMenu()) {
				player.setRotY(currentAngle);
			}
		}
		calculateCameraPosition(calcualteHorizotalDistance(), calcualteVerticalDistance());
		Mouse.setGrabbed(!player.isInMenu());
	}

	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		currentZoom -= zoomLevel;
		if (currentZoom < minZoom) currentZoom = minZoom;
		if (currentZoom > maxZoom) currentZoom = maxZoom;

	}

	private void calcuatePitch() {
		float pitchChange = Mouse.getDY() * 0.1f;
		pitch -= pitchChange;
		if (pitch > 90) pitch = 90;
		if (pitch < -90) pitch = -90;
	}

	private void calcuateAngleArroundPlayer() {
		float angleChange = Mouse.getDX() * 0.3f;
		currentAngle -= angleChange;
	}

	private void calculateCameraPosition(float distanceH, float distanceV) {
		float theta = currentAngle;
		float xOffset = (float) (distanceH * Math.sin(Math.toRadians(theta)));
		float zOffset = (float) (distanceH * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - xOffset;
		position.z = player.getPosition().z - zOffset;
		position.y = player.getPosition().y + distanceV;
		yaw = 180 - currentAngle;
	}

	private float calcualteHorizotalDistance() {
		return (float) (currentZoom * Math.cos(Math.toRadians(pitch)));
	}

	private float calcualteVerticalDistance() {
		return (float) (currentZoom * Math.sin(Math.toRadians(pitch)));
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public void pitch(float pitch) {
		this.pitch += pitch;
	}

	public void yaw(float yaw) {
		this.yaw += yaw;
	}

	public void roll(float roll) {
		this.roll += roll;
	}

	public void invertPitch() {
		this.pitch = -this.pitch;
	}
}
