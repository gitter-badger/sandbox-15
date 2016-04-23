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
package net.roryclaasen.sandbox.entities;

import net.roryclaasen.sandbox.models.TexturedModel;
import net.roryclaasen.sandbox.util.DeltaUtil;

import org.lwjgl.util.vector.Vector3f;

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
		this.position.x += dx * DeltaUtil.getDelta();
		this.position.y += dy * DeltaUtil.getDelta();
		this.position.z += dz * DeltaUtil.getDelta();
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
