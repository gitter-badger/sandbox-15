package net.roryclaasen.sandbox.RenderEngine.texture;

public class ModelTexture {
	private int textureID;

	private float shineDamper = 1;
	private float refelectivity = 0;
	
	private boolean hasTransparency = false;
	private boolean fakeLighting = false;
	
	private int numberOfRows = 1;

	public ModelTexture(int id) {
		this.textureID = id;
	}

	public int getID() {
		return textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getRefelectivity() {
		return refelectivity;
	}

	public void setRefelectivity(float refelectivity) {
		this.refelectivity = refelectivity;
	}

	public boolean hasTransparency() {
		return hasTransparency;
	}

	public ModelTexture setTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
		return this;
	}

	public boolean useFakeLighting() {
		return fakeLighting;
	}

	public ModelTexture setFakeLighting(boolean fakeLighting) {
		this.fakeLighting = fakeLighting;
		return this;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public ModelTexture setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
		return this;
	}

	public ModelTexture setShine(float shineDamper, float refelectivity) {
		setShineDamper(shineDamper);
		setRefelectivity(refelectivity);
		return this;
	}
}
