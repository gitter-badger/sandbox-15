package net.roryclaasen.sandbox.entities.light;

import net.roryclaasen.sandbox.entities.Entity;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	private Vector3f position;
	private Vector3f color;
	private Vector3f attenuation = new Vector3f(1, 0, 0);

	public Light(Vector3f position, Vector3f color,Vector3f attenuation) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
	}

	public Light(Vector3f position, Vector3f color) {
		this(position, color, new Vector3f(1, 0, 0));
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public Vector3f getAttenuation() {
		return attenuation;
	}
	
	public double getDistanceFrom(Entity entity){
		Vector3f p = entity.getPosition();
		return Math.sqrt(Math.pow(Math.sqrt(Math.pow((double) (p.x + position.x), 2) + Math.pow((double) (p.z + position.z), 2)) + Math.pow((double) (p.y + position.y), 2), 2));
	}
}
