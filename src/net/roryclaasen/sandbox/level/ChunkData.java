package net.roryclaasen.sandbox.level;

import net.gogo98901.log.Log;

public class ChunkData {
	private int id;
	private int x, y;
	private String blendMap;
	private String heightMap;
	private int texturePack;
	private ChunkObjectData data;

	public ChunkData(int id) {
		this.id = id;
	}

	public void printData() {
		String offset = System.getProperty("line.separator") + "      ";
		String data = offset + "id: " + id;
		data += offset + "x: " + x;
		data += offset + "y: " + y;
		data += offset + "blendMap: " + blendMap;
		data += offset + "heightMap: " + heightMap;
		data += offset + "texturePack: " + texturePack;
		Log.info("Chunk Data" + data);
	}

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

	public int getID() {
		return id;
	}

	public ChunkObjectData getData() {
		return data;
	}

	public void setData(ChunkObjectData data) {
		this.data = data;
	}
}
