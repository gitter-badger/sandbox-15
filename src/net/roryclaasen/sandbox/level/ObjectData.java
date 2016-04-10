package net.roryclaasen.sandbox.level;

import net.roryclaasen.sandbox.RenderEngine.models.Models;
import net.roryclaasen.sandbox.RenderEngine.models.TexturedModel;

public class ObjectData {
	private int x, y;
	private String model;
	private int texIndex = -1;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public TexturedModel getTexturedModel() {
		return Models.get(model);
	}

	public int getTexIndex() {
		return texIndex;
	}

	public void setTexIndex(int texIndex) {
		this.texIndex = texIndex;
	}
}
