package net.roryclaasen.sandbox.level;

import java.util.ArrayList;
import java.util.List;

import net.gogo98901.log.Log;

public class WorldData {

	private List<ChunkData> chunks;
	private EntityData playerData;

	public WorldData() {
		chunks = new ArrayList<ChunkData>();
	}

	public void addChunk(ChunkData data) {
		for (ChunkData cData : chunks) {
			if (cData.getID() == data.getID()) {
				Log.warn("Chunk error.ignored: reason same ID (id = " + data.getID() + ")");
				return;
			}
		}
		chunks.add(data);
	}

	public void addPlayer(EntityData playerData) {
		this.playerData = playerData;
	}
	
	public EntityData getPlayerData(){
		return playerData;
	}

	public List<ChunkData> getChunks() {
		return chunks;
	}
}
