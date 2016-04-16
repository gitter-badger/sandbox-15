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
package net.roryclaasen.sandbox.terrain;

import java.util.ArrayList;
import java.util.List;

import net.roryclaasen.sandbox.RenderEngine.texture.TerrainTexture;
import net.roryclaasen.sandbox.RenderEngine.texture.TerrainTexturePack;
import net.roryclaasen.sandbox.RenderEngine.water.WaterTile;
import net.roryclaasen.sandbox.util.Loader;
import net.gogo98901.log.Log;

public class TerrainManager {

	private Loader loader;

	private static List<Terrain> terrains = new ArrayList<Terrain>();
	private List<WaterTile> waters = new ArrayList<WaterTile>();

	private TerrainTexturePack pack0;

	public TerrainManager(Loader loader) {
		this.loader = loader;
		load();
	}

	private void load() {
		TerrainTexture terrainBackground = new TerrainTexture(loader.loadTexture("grass_1"));
		TerrainTexture terrainR = new TerrainTexture(loader.loadTexture("mud_0"));
		TerrainTexture terrainG = new TerrainTexture(loader.loadTexture("grass_2"));
		TerrainTexture terrainB = new TerrainTexture(loader.loadTexture("path_0"));

		pack0 = new TerrainTexturePack(terrainBackground, terrainR, terrainG, terrainB);
	}

	public TerrainTexturePack getPack(int id) {
		switch (id) {
		case 0:
			return pack0;
		default:
			return pack0;
		}
	}

	public List<Terrain> getTerrains() {
		return terrains;
	}
	
	public Terrain getTerrain() {
		return getTerrains().get(0);
	}

	public void add(Terrain terrain) {
		terrains.add(terrain);
	}

	public List<WaterTile> getWaters() {
		return waters;
	}

	public void addWater(WaterTile water) {
		waters.add(water);
	}

	public static Terrain getCurrentTerrain(float x, float z) {
		if (terrains.size() > 0) {
			for (Terrain t : terrains) {
				if (x >= t.getX() && x <= t.getX() + Terrain.getSize()) {
					if (z >= t.getZ() && z <= t.getZ() + Terrain.getSize()) {
						return t;
					}
				}
			}
			return terrains.get(0);
		}
		Log.severe("The terrains list is empty");
		return null;
	}
}
