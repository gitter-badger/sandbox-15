package net.roryclaasen.sandbox.level;

import java.util.ArrayList;
import java.util.List;

public class WorldData {

	private List<ChunkData> chunks;
	private EntityData playerData;

	public WorldData() {
		chunks = new ArrayList<ChunkData>();
	}

	public void addChunk(ChunkData data) {
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
