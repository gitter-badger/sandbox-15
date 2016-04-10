package net.roryclaasen.sandbox.entities;

import org.lwjgl.util.vector.Vector3f;

import net.roryclaasen.sandbox.RenderEngine.DisplayManager;
import net.roryclaasen.sandbox.RenderEngine.models.TexturedModel;

public class Entity {
	protected float health, maxHealth;
	protected boolean hasHealth;
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;

	private int textureIndex = 0;

	public Entity(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this.model = model;
		this.textureIndex = textureIndex;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
	}

	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		this(model, 0, position, rotX, rotY, rotZ, scale);
	}

	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float) column / (float) model.getTexture().getNumberOfRows();
	}

	public float getTextureYOffset() {
		int row = textureIndex / model.getTexture().getNumberOfRows();
		return (float) row / (float) model.getTexture().getNumberOfRows();
	}

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx * DisplayManager.getFrameTimeSeconds();
		this.position.y += dy * DisplayManager.getFrameTimeSeconds();
		this.position.z += dz * DisplayManager.getFrameTimeSeconds();
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getX() {
		return position.getX();
	}

	public float getY() {
		return position.getY();
	}

	public float getZ() {
		return position.getZ();
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getHealth() {
		if (hasHealth) return health;
		return -1;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public float getMaxHealth() {
		if (hasHealth) return maxHealth;
		return -1;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public boolean isHasHealth() {
		return hasHealth;
	}

	public void setHasHealth(boolean hasHealth) {
		this.hasHealth = hasHealth;
	}

}
