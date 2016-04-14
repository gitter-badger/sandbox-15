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
package net.roryclaasen.sandbox.entities.light;

import net.roryclaasen.sandbox.entities.Entity;

import org.lwjgl.util.vector.Vector3f;

public class Light {
	private Vector3f position;
	private Vector3f color;
	private Vector3f attenuation = new Vector3f(1, 0, 0);

	public Light(Vector3f position, Vector3f color,Vector3f attenuation) {
		this.position = position;
		this.color = color;
		this.attenuation = attenuation;
	}

	public Light(Vector3f position, Vector3f color) {
		this(position, color, new Vector3f(1, 0, 0));
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getColor() {
		return color;
	}

	public void setColor(Vector3f color) {
		this.color = color;
	}

	public Vector3f getAttenuation() {
		return attenuation;
	}
	
	public double getDistanceFrom(Entity entity){
		Vector3f p = entity.getPosition();
		return Math.sqrt(Math.pow(Math.sqrt(Math.pow((double) (p.x + position.x), 2) + Math.pow((double) (p.z + position.z), 2)) + Math.pow((double) (p.y + position.y), 2), 2));
	}
}
