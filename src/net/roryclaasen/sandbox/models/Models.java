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
package net.roryclaasen.sandbox.models;

public enum Models {
	tree("tree001", "trees/pine", "models/trees/pine", true);

	protected String key;
	protected String model;
	protected String texture;
	protected boolean transparent = false;
	protected boolean fakeLighting = false;
	protected float shineDamper = 1;
	protected float refelectivity = 0;

	Models(String key, String model, String texture) {
		this(key, model, texture, false, false, 1, 0);
	}

	Models(String key, String model, String texture, boolean transparent) {
		this(key, model, texture, transparent, false, 1, 0);
	}

	Models(String key, String model, String texture, boolean transparent, boolean fakeLighting) {
		this(key, model, texture, transparent, fakeLighting, 1, 0);
	}

	Models(String key, String model, String texture, float shineDamper, float refelectivity) {
		this(key, model, texture, false, false, shineDamper, refelectivity);
	}

	Models(String key, String model, String texture, boolean transparent, boolean fakeLighting, float shineDamper, float refelectivity) {
		this.key = key;
		this.model = model;
		this.texture = texture;
		this.transparent = transparent;
		this.shineDamper = shineDamper;
		this.refelectivity = refelectivity;
	}

	public TexturedModel get() {
		return ModelLoader.get(key);
	}
	
	public String getModel(){
		return model;
	}

	public String getKey() {
		return key;
	}
}
