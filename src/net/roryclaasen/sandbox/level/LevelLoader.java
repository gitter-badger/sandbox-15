package net.roryclaasen.sandbox.level;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.gogo98901.log.Log;
import net.gogo98901.util.Data;
import net.gogo98901.util.zip.UnZipper;
import net.roryclaasen.sandbox.Sandbox;
import net.roryclaasen.sandbox.RenderEngine.texture.TerrainTexture;
import net.roryclaasen.sandbox.entities.Entity;
import net.roryclaasen.sandbox.terrain.Terrain;
import net.roryclaasen.sandbox.terrain.TerrainManager;

import org.lwjgl.util.vector.Vector3f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		if (Data.createPath(getLocation())) {

		}
	}

	public void loadLevel(String name) {
		Log.info("LevelLoader... Loading... '" + name + "'");
		boolean hasUnzipped = false;
		name = name.toLowerCase();
		File root = new File(getLocation() + File.separator + name);
		if (root.isFile()) {
			UnZipper.unZipIt(root.getAbsolutePath(), getLocation() + File.separator + "temp.dworld");
			hasUnzipped = true;
		}
		if (root.isDirectory() || hasUnzipped) {
			worldData = getWorldData(root);
			for (ChunkData chunk : worldData.getChunks()) {
				Terrain t = new Terrain(0, 0, game.loader, game.terrainManager.getPack(chunk.getTexturePack()), new TerrainTexture(game.loader.loadTexture("level/" + chunk.getBlendMap())), "level/" + chunk.getHeightMap());
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
		}
		Log.info("LevelLoader... Loading... OK");
	}

	private WorldData getWorldData(File root) {
		WorldData data = new WorldData();
		try {
			File fXmlFile = new File(root + File.separator + "world");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			NodeList playerList = doc.getElementsByTagName("player");
			try {
				Node nNode = playerList.item(0);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					EntityData player = new EntityData();
					player.setLocation(Integer.parseInt(eElement.getElementsByTagName("x").item(0).getTextContent()), Integer.parseInt(eElement.getElementsByTagName("y").item(0).getTextContent()));
					player.setRotationX(Integer.parseInt(eElement.getElementsByTagName("rotx").item(0).getTextContent()));
					player.setRotationY(Integer.parseInt(eElement.getElementsByTagName("roty").item(0).getTextContent()));
					player.setRotationZ(Integer.parseInt(eElement.getElementsByTagName("rotz").item(0).getTextContent()));
					data.addPlayer(player);
				}
			} catch (Exception e) {
				Log.warn("Failed to load type 'player'");
				Log.stackTrace(e);
			}
			NodeList chunkList = doc.getElementsByTagName("chunk");
			try {
				Node nNode = chunkList.item(0);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;

					ChunkData newChunk = new ChunkData();
					newChunk.setHeightMap(eElement.getElementsByTagName("hightmmap").item(0).getTextContent());
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
		return "levels";
	}
}
