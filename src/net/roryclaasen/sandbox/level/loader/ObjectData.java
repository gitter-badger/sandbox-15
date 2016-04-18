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
package net.roryclaasen.sandbox.level.loader;

import net.roryclaasen.sandbox.models.TexturedModel;
import net.roryclaasen.sandbox.util.JSONUtil;

import org.json.simple.JSONObject;
import org.lwjgl.util.vector.Vector3f;

public class ObjectData {

	private String modelId;
	private int texIndex = -1;
	private Vector3f location;

	public ObjectData(JSONObject data) {
		this.modelId = JSONUtil.getString(data, "rotx", null);
		if (data.containsKey("tex-index")) this.texIndex = JSONUtil.getInteger(data, "tex-index", 0);
		float x = JSONUtil.getInteger(data, "x", 0);
		float y = JSONUtil.getInteger(data, "y", 0);
		float z = JSONUtil.getInteger(data, "z", 0);
		this.location = new Vector3f(x, y, z);
	}

	public int getTexIndex() {
		return texIndex;
	}

	public TexturedModel getTexturedModel() {
		return null;
	}

	public String getModelId() {
		return modelId;
	}

	public Vector3f getLocation() {
		return location;
	}
}
