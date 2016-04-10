package net.roryclaasen.sandbox.entities;

import net.roryclaasen.sandbox.RenderEngine.models.TexturedModel;
import net.roryclaasen.sandbox.entities.light.Light;

import org.lwjgl.util.vector.Vector3f;

public class EntityLight extends Entity {
	private Light light;
	private float offset = 12.7f;

	public EntityLight(TexturedModel model, int textureIndex, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, textureIndex, position, rotX, rotY, rotZ, scale);
	}

	public EntityLight(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}
	
	public void setPosition(Vector3f position) {
		this.setPosition(position);
		light.setPosition(new Vector3f(position.x, position.y + offset, position.z));
	}

	public EntityLight setLight(Light light) {
		this.light = light;
		return this;
	}

	public EntityLight setLight(Vector3f color) {
		return setLight(color, new Vector3f(1, 0, 0));
	}

	public EntityLight setLight(Vector3f color, Vector3f attenuation) {
		this.light = new Light(new Vector3f(getPosition().x, getPosition().y + offset, getPosition().z), color, attenuation);
		return this;
	}

	public Light getLight() {
		return light;
	}

}
