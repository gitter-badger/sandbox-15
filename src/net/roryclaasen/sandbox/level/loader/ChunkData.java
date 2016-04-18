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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.roryclaasen.sandbox.util.JSONUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.lwjgl.util.vector.Vector2f;

public class ChunkData {

	private int id;
	private Vector2f location;
	private String blendMap;
	private int texturePack;

	private List<ObjectData> objects = new ArrayList<ObjectData>();
	private List<EntityData> entities = new ArrayList<EntityData>();

	public ChunkData(JSONObject data, File root) throws FileNotFoundException, IOException, ParseException {
		this.id = JSONUtil.getInteger(data, "id", 0);
		this.location = new Vector2f(JSONUtil.getInteger(data, "x", 0), JSONUtil.getInteger(data, "y", 0));

		this.blendMap = JSONUtil.getString(data, "belnd-map", "map.png");
		this.texturePack = JSONUtil.getInteger(data, "texturepack", 0);
		loadData(root);
	}

	private void loadData(File root) throws FileNotFoundException, IOException, ParseException {
		JSONObject world = JSONUtil.read(new File(root + File.separator + "chunk" + id));

		@SuppressWarnings("unchecked")
		Iterator<JSONObject> iterator = JSONUtil.getArray(world, "data").iterator();
		while (iterator.hasNext()) {
			JSONObject entityObject = iterator.next();
			if (entityObject.containsKey("object")) {
				objects.add(new ObjectData(entityObject));
			}
			if (entityObject.containsKey("entity")) {
				entities.add(new EntityData(entityObject));
			}
		}
	}

	public int getId() {
		return id;
	}

	public Vector2f getLocation() {
		return location;
	}

	public String getBlendMap() {
		return blendMap;
	}

	public int getTexturePack() {
		return texturePack;
	}

	public List<ObjectData> getObjects() {
		return objects;
	}

	public List<EntityData> getEntities() {
		return entities;
	}
}
