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
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.gogo98901.log.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.lwjgl.util.vector.Vector2f;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ChunkData {
	private JSONParser parser;

	private int id;
	private Vector2f location;
	private String blendMap;
	private int texturePack;
	private ChunkObjectData data;

	public ChunkData(JSONObject data,File root) {this.
		parser = new JSONParser();
	
		this.id = Integer.parseInt((String) data.get("id"));
		this.location = new Vector2f( Integer.parseInt((String) data.get("x")),  Integer.parseInt((String) data.get("y")));
		
		this.blendMap = (String) data.get("belnd-map");
		this.texturePack = Integer.parseInt((String) data.get("texturepack"));
		loadData(root);
		}

	private void loadData(File root) {
		File worldFile = new File(root + File.separator + "chunk");

		JSONObject world = (JSONObject) (Object) parser.parse(new FileReader(worldFile));
		
		ChunkObjectData data = new ChunkObjectData();
		
		JSONArray chunkArrary = (JSONArray) world.get("chunks");
		Iterator<JSONObject> iterator = chunkArrary.iterator();
		while (iterator.hasNext()) {
			data.addChunk(new ChunkData(iterator.next(), root + File.separator));
		}
		
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

}
