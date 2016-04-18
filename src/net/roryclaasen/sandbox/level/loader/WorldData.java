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
package net.roryclaasen.sandbox.level.loader;

import java.util.ArrayList;
import java.util.List;

public class WorldData {

	private List<ChunkData> chunks;
	private EntityData playerData;

	private int seed = 0;

	public WorldData() {
		chunks = new ArrayList<ChunkData>();
	}

	public void addChunk(ChunkData data) {
		chunks.add(data);
	}

	public void addPlayer(EntityData playerData) {
		this.playerData = playerData;
	}

	public EntityData getPlayerData() {
		return playerData;
	}

	public List<ChunkData> getChunks() {
		return chunks;
	}

	public void setSeed(int seed) {
		this.seed = seed;
	}

	public int getSeed() {
		return seed;
	}
}
