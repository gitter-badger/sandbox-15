/** 
   Copyright 2016 Rory Claasen

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

import org.lwjgl.util.vector.Vector3f;

public class Player extends Entity {

	private boolean turning = false;

	private boolean isInMenu = false;
	// TODO add menus

	private Camera camera;

	public Player(Vector3f position, float rotX, float rotY, float rotZ) {
		super(null, position, rotX, rotY, rotZ, 1F);
		hasHealth = false;
	}

	public boolean isTurning() {
		return turning;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Camera getCamera() {
		return camera;
	}

	public boolean isInMenu() {
		return isInMenu;
	}

	public void setInMenu(boolean inMenu) {
		isInMenu = inMenu;
	}
}
