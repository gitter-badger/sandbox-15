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
import java.io.FileReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.gogo98901.log.Log;
import net.gogo98901.util.Data;
import net.roryclaasen.Bootstrap;
import net.roryclaasen.sandbox.Sandbox;
import net.roryclaasen.sandbox.RenderEngine.texture.TerrainTexture;
import net.roryclaasen.sandbox.entities.Entity;
import net.roryclaasen.sandbox.terrain.Terrain;
import net.roryclaasen.sandbox.terrain.TerrainManager;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LevelLoader {

	private JSONParser parser;

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
		
		parser = new JSONParser();
	}

	public void loadLevel(String name) {
		Log.info("LevelLoader... Loading... '" + name + "'");
		name = name.toLowerCase();
		File root = new File(getLocation() + File.separator + name);
		worldData = getWorldData(root);
		for (ChunkData chunk : worldData.getChunks()) {
			Terrain t = new Terrain(0, 0, game.loader, game.terrainManager.getPack(chunk.getTexturePack()), new TerrainTexture(game.loader.loadTexture("level/" + chunk.getBlendMap())));
			game.terrainManager.add(t);
			if (chunk.getData() != null) {
				for (ObjectData object : chunk.getData().getObjects()) {
					float x = t.getX() + object.getX();
					float z = t.getZ() + object.getY();
					float y = TerrainManager.getCurrentTerrain(x, z).getHeightOfTerrain(x, z);
					if (object.getTexIndex() == -1) game.entityManager.add(new Entity(object.getTexturedModel(), new Vector3f(x, y, z), 0, 0, 0, 1));
					else game.entityManager.add(new Entity(object.getTexturedModel(), object.getTexIndex(), new Vector3f(x, y, z), 0, 0, 0, 1));
				}
			}
		}
		Log.info("LevelLoader... Loading... OK");
	}

	private WorldData getWorldData(File root) {
		WorldData data = new WorldData();
		try {
			File worldFile = new File(root + File.separator + "world");

			JSONObject world = (JSONObject) (Object) parser.parse(new FileReader(worldFile));

			JSONObject playerObject = (JSONObject) world.get("player");
			EntityData playerData = new EntityData();
			JSONObject chunksObject = (JSONObject) world.get("chunks");
			try {
				Node nNode = chunkList.item(0);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					ChunkData newChunk = new ChunkData();
					newChunk.setBlendMap(eElement.getElementsByTagName("blendmap").item(0).getTextContent());
					newChunk.setTexturePack(Integer.parseInt(eElement.getElementsByTagName("texturepack").item(0).getTextContent()));
					if (eElement.getElementsByTagName("file").item(0) != null) newChunk.setData(loadChunk(root, eElement.getElementsByTagName("file").item(0).getTextContent()));
					data.addChunk(newChunk);
				}
			} catch (Exception e) {
				Log.warn("Failed to load type 'chunk'");
				Log.stackTrace(e);
			}
		} catch (Exception e) {
			Log.stackTrace(e);
		}
		return data;
	}

	public ChunkObjectData loadChunk(File root, String file) {
		ChunkObjectData data = new ChunkObjectData();
		try {
			File fXmlFile = new File(root + File.separator + file);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			NodeList chunkList = doc.getElementsByTagName("object");
			for (int temp = 0; temp < chunkList.getLength(); temp++) {
				try {
					Node nNode = chunkList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						ObjectData newObject = new ObjectData();
						newObject.setModel(eElement.getAttribute("model"));
						newObject.setLocation(Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent()), Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent()));
						if (eElement.getElementsByTagName("texindex").item(0) != null) newObject.setTexIndex(Integer.parseInt(eElement.getElementsByTagName("texindex").item(0).getTextContent()));
						data.add(newObject);
					}
				} catch (Exception e) {
					Log.warn("Failed to load type 'chunk'");
					Log.stackTrace(e);
				}
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
