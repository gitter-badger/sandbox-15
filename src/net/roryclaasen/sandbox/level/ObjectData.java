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
package net.roryclaasen.sandbox.level;

import net.roryclaasen.sandbox.RenderEngine.models.Models;
import net.roryclaasen.sandbox.RenderEngine.models.TexturedModel;

public class ObjectData {
	private int x, y;
	private String model;
	private int texIndex = -1;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getModel() {
		return model;
	}

	public TexturedModel getTexturedModel() {
		return Models.get(model);
	}

	public int getTexIndex() {
		return texIndex;
	}

	public void setTexIndex(int texIndex) {
		this.texIndex = texIndex;
	}
}
