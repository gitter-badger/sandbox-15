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
package net.roryclaasen.sandbox.level;

import java.io.File;
import java.util.Iterator;

import net.gogo98901.log.Log;
import net.gogo98901.util.Data;
import net.roryclaasen.Bootstrap;
import net.roryclaasen.sandbox.Sandbox;
import net.roryclaasen.sandbox.RenderEngine.texture.TerrainTexture;
import net.roryclaasen.sandbox.entities.Entity;
import net.roryclaasen.sandbox.level.loader.ChunkData;
import net.roryclaasen.sandbox.level.loader.EntityData;
import net.roryclaasen.sandbox.level.loader.ObjectData;
import net.roryclaasen.sandbox.level.loader.WorldData;
import net.roryclaasen.sandbox.terrain.Terrain;
import net.roryclaasen.sandbox.util.JSONUtil;

import org.json.simple.JSONObject;
import org.lwjgl.util.vector.Vector3f;

public class LevelLoader {

	private Sandbox game;
	private WorldData worldData;

	public LevelLoader(Sandbox game) {
		Log.info("LevelLoader... Setting up");
		this.game = game;
		setUp();
		Log.info("LevelLoader... OK");
	}

	private void setUp() {
		Data.createPath(getLocation());
		File[] levels = (new File(getLocation())).listFiles();
		Log.info("LevelLoader found " + (levels == null ? 0 : levels.length) + " levels saved");
	}

	public void loadLevel(String name) {
		Log.info("LevelLoader... Loading... '" + name + "'");
		name = name.toLowerCase();
		File root = new File(getLocation() + File.separator + name);
		worldData = getWorldData(root);
		for (ChunkData chunk : worldData.getChunks()) {
			Terrain t = new Terrain(0, 0, game.loader, game.terrainManager.getPack(chunk.getTexturePack()), new TerrainTexture(game.loader.loadTexture("level/" + chunk.getBlendMap())));
			game.terrainManager.add(t);
			for (ObjectData object : chunk.getObjects()) {
				float x = t.getX() + object.getLocation().getX();
				float z = t.getZ() + object.getLocation().getZ();
				float y = object.getLocation().getY();
				if (object.getTexIndex() == -1) game.entityManager.add(new Entity(object.getTexturedModel(), new Vector3f(x, y, z), 0, 0, 0, 1));
				else game.entityManager.add(new Entity(object.getTexturedModel(), object.getTexIndex(), new Vector3f(x, y, z), 0, 0, 0, 1));
			}
		}
		Log.info("LevelLoader... Loading... OK");
	}

	private WorldData getWorldData(File root) {
		WorldData data = new WorldData();
		try {
			JSONObject world = JSONUtil.read(new File(root + File.separator + "world"));

			data.addPlayer(new EntityData(JSONUtil.getObject(world, "player")));
			@SuppressWarnings("unchecked")
			Iterator<JSONObject> iterator = JSONUtil.getArray(world, "chunks").iterator();
			while (iterator.hasNext()) {
				data.addChunk(new ChunkData(iterator.next(), root));
			}
		} catch (Exception e) {
			Log.stackTrace(e);
		}
		return data;
	}

	public EntityData getPlayerData() {
		return worldData.getPlayerData();
	}

	public static String getLocation() {
		return Bootstrap.GAME_PATH + "levels";
	}
}
