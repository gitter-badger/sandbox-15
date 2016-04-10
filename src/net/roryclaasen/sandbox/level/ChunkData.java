package net.roryclaasen.sandbox.level;


public class ChunkData {

	private String blendMap;
	private String heightMap;
	private int texturePack;
	private ChunkObjectData data;

	public ChunkData() {}

	public String getBlendMap() {
		return blendMap;
	}

	public void setBlendMap(String blendMap) {
		this.blendMap = blendMap;
	}

	public String getHeightMap() {
		return heightMap;
	}

	public void setHeightMap(String heightMap) {
		this.heightMap = heightMap;
	}

	public int getTexturePack() {
		return texturePack;
	}

	public void setTexturePack(int texturePack) {
		this.texturePack = texturePack;
	}

	public ChunkObjectData getData() {
		return data;
	}

	public void setData(ChunkObjectData data) {
		this.data = data;
	}
}
