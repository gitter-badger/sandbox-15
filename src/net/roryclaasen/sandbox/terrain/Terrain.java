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
package net.roryclaasen.sandbox.terrain;

import java.awt.image.BufferedImage;

import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.RenderEngine.texture.TerrainTexture;
import net.roryclaasen.sandbox.RenderEngine.texture.TerrainTexturePack;
import net.roryclaasen.sandbox.models.RawModel;
import net.roryclaasen.sandbox.util.Loader;
import net.roryclaasen.sandbox.util.Maths;
import net.roryclaasen.sandbox.util.TextureUtil;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class Terrain {

	private static final float SIZE = 300;

	private float x, z;
	private RawModel model;
	private TerrainTexturePack texturePack;
	private TerrainTexture blendMap;
	private int seed = -1;

	private float[][] heights;

	public Terrain(int gridX, int gridZ, Loader loader, TerrainTexturePack texturePack, int seed) {
		this.texturePack = texturePack;
		this.x = gridX * SIZE;
		this.z = gridZ * SIZE;
		this.model = generateTerrain(loader, seed, gridX, gridZ);
		this.blendMap = generateBlendMap();
	}

	public static float getSize() {
		return SIZE;
	}

	public float getX() {
		return x;
	}

	public float getZ() {
		return z;
	}

	public RawModel getModel() {
		return model;
	}

	public TerrainTexturePack getTexturePack() {
		return texturePack;
	}

	public TerrainTexture getBlendMap() {
		return blendMap;
	}

	public TerrainTexture generateBlendMap() {
		BufferedImage img = new BufferedImage(256 * 2, 256 * 2, BufferedImage.TYPE_INT_RGB);
		float dif = SIZE / img.getWidth();
		int levels = (int) Math.floor(dif * 255f);
		for (float x = 0; x < img.getWidth(); x++) {
			for (float y = 0; y < img.getWidth(); y++) {
				float height = getHeightOfTerrain(this.x + (x * dif), this.z + (y * dif));
				int r = 0;
				int g = 0;
				int b = 0;
				if (height > 27) {
					r = (int) (levels * height);
				}
				if (height < 3) {
					g = (int) (levels * Math.abs(height));
				}
				if (height > -2 && height < 20) {
					b = (int) (levels * height);
				}
				if (r < 0) r = 0;
				if (r > 255) r = 255;
				if (g < 0) g = 0;
				if (g > 255) g = 255;
				if (b < 0) b = 0;
				if (b > 255) b = 255;
				int col = (r << 16) | (g << 8) | b;
				int rgbX = (int) Math.floor(x);
				int rgbY = (int) Math.floor(y);
				img.setRGB(rgbX, rgbY, col);
			}
		}
		return new TerrainTexture(TextureUtil.loadTexture(img));
	}

	public float[][] getHeights() {
		return heights;
	}

	public int getSeed() {
		return seed;
	}

	public float getHeightOfTerrain(float worldX, float worldZ) {
		float terrainX = worldX - x;
		float terrainZ = worldZ - z;
		float gridSquareSize = SIZE / ((float) heights.length - 1);
		int gridX = (int) Math.floor(terrainX / gridSquareSize);
		int gridZ = (int) Math.floor(terrainZ / gridSquareSize);
		if (gridX >= heights.length - 1 || gridZ >= heights.length - 1 || gridX < 0 || gridZ < 0) return 0;
		float xCoord = (terrainX % gridSquareSize) / gridSquareSize;
		float zCoord = (terrainZ % gridSquareSize) / gridSquareSize;
		float answer;
		if (xCoord <= (1 - zCoord)) answer = Maths.barryCentric(new Vector3f(0, heights[gridX][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		else answer = Maths.barryCentric(new Vector3f(1, heights[gridX + 1][gridZ], 0), new Vector3f(1, heights[gridX + 1][gridZ + 1], 1), new Vector3f(0, heights[gridX][gridZ + 1], 1), new Vector2f(xCoord, zCoord));
		return answer;
	}

	private RawModel generateTerrain(Loader loader, int seed, int x, int z) {
		int vertexCount = 64 + 16 - 8;
		HeightGenerator generator = new HeightGenerator(seed, x, z, vertexCount);
		seed = generator.getSeed();
		heights = new float[vertexCount][vertexCount];

		int count = vertexCount * vertexCount;
		float[] vertices = new float[count * 3];
		float[] normals = new float[count * 3];
		float[] textureCoords = new float[count * 2];
		int[] indices = new int[6 * (vertexCount - 1) * (vertexCount * 1)];
		int vertexPointer = 0;
		for (int i = 0; i < vertexCount; i++) {
			for (int j = 0; j < vertexCount; j++) {
				vertices[vertexPointer * 3] = (float) j / ((float) vertexCount - 1) * SIZE;
				float height = getHeight(j, i, generator);
				heights[j][i] = height;
				vertices[vertexPointer * 3 + 1] = height;
				vertices[vertexPointer * 3 + 2] = (float) i / ((float) vertexCount - 1) * SIZE;
				Vector3f normal = calcuateNormal(j, i, generator);
				normals[vertexPointer * 3] = normal.x;
				normals[vertexPointer * 3 + 1] = normal.y;
				normals[vertexPointer * 3 + 2] = normal.z;
				textureCoords[vertexPointer * 2] = (float) j / ((float) vertexCount - 1);
				textureCoords[vertexPointer * 2 + 1] = (float) i / ((float) vertexCount - 1);
				vertexPointer++;
			}
		}
		int pointer = 0;
		for (int gz = 0; gz < vertexCount - 1; gz++) {
			for (int gx = 0; gx < vertexCount - 1; gx++) {
				int topLeft = (gz * vertexCount) + gx;
				int topRight = topLeft + 1;
				int bottomLeft = ((gz + 1) * vertexCount) + gx;
				int bottomRight = bottomLeft + 1;
				indices[pointer++] = topLeft;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = topRight;
				indices[pointer++] = topRight;
				indices[pointer++] = bottomLeft;
				indices[pointer++] = bottomRight;
			}
		}
		return loader.loadToVAO(vertices, textureCoords, normals, indices);
	}

	private Vector3f calcuateNormal(int x, int z, HeightGenerator generator) {
		float hightL = getHeight(x - 1, z, generator);
		float hightR = getHeight(x + 1, z, generator);
		float hightU = getHeight(x, z + 1, generator);
		float hightD = getHeight(x, z - 1, generator);
		Vector3f normal = new Vector3f(hightL - hightR, 2F, hightD - hightU);
		normal.normalise();
		return normal;

	}

	private float getHeight(int x, int z, HeightGenerator generator) {
		return generator.generateHeight(x, z);
	}
}
