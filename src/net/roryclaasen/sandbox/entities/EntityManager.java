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
package net.roryclaasen.sandbox.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.roryclaasen.sandbox.RenderEngine.MasterRenderer;
import net.roryclaasen.sandbox.entities.light.Light;

public class EntityManager {

	private Player player;
	private List<Entity> entities = new ArrayList<Entity>();
	private Light sun;
	private List<Light> lights = new ArrayList<Light>();

	public void addPlayer(Player player) {
		this.player = player;
	}

	public void add(Entity entity) {
		if (entity instanceof EntityLight) add(((EntityLight) entity).getLight());
		entities.add(entity);
	}

	public void add(Light light) {
		lights.add(light);
	}

	public void addSun(Light light) {
		lights.add(0, sun = light);
	}

	public void render(MasterRenderer renderer) {
		for (Entity entity : entities) {
			renderer.processEntity(entity);
		}
		// renderer.processEntity(player);
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public List<Light> getLights() {
		return lights;
	}

	public List<Light> getNearestLights() {
		boolean hasSun = false;
		List<Light> returnLlist = new ArrayList<Light>();
		if (sun != null) {
			returnLlist.add(sun);
			hasSun = true;
		}
		double[] distance = new double[lights.size()];
		for (int i = 0; i < distance.length; i++) {
			distance[i] = lights.get(i).getDistanceFrom(player);
		}
		Arrays.sort(distance);
		double[] finalDistance = new double[4];
		if (distance.length > 0) finalDistance[0] = distance[0];
		if (distance.length > 1) finalDistance[1] = distance[1];
		if (distance.length > 2) finalDistance[2] = distance[2];
		if (distance.length > 3) finalDistance[3] = distance[3];
		if (hasSun) finalDistance[3] = -1;
		for (Light light : lights) {
			if (light != sun) {
				for (double d : distance) {
					if (light.getDistanceFrom(player) == d) returnLlist.add(light);
				}
			}
		}
		return returnLlist;
	}

	public Light getSun() {
		return sun;
	}

	public Player getPlayer() {
		return player;
	}
}
