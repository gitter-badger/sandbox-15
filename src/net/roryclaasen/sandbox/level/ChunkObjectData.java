package net.roryclaasen.sandbox.level;

import java.util.ArrayList;
import java.util.List;

public class ChunkObjectData {
	private List<ObjectData> objects;

	public ChunkObjectData() {
		objects = new ArrayList<ObjectData>();
	}

	public void add(ObjectData data) {
		objects.add(data);
	}

	public List<ObjectData> getObjects() {
		return objects;
	}
}
