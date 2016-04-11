/** 
   Copyright 2016 Rory Claasen

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
