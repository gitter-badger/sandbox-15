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

public class EntityData extends ObjectData {
	private int rotx, roty, rotz;

	public int getRotationX() {
		return rotx;
	}

	public void setRotationX(int rotx) {
		this.rotx = rotx;
	}

	public int getRotationY() {
		return roty;
	}

	public void setRotationY(int roty) {
		this.roty = roty;
	}

	public int getRotationZ() {
		return rotz;
	}

	public void setRotationZ(int rotz) {
		this.rotz = rotz;
	}

}
