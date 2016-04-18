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

import java.util.ArrayList;
import java.util.List;

import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.RenderEngine.texture.ModelTexture;
import net.roryclaasen.sandbox.util.Loader;

public class ModelLoader {

	private static List<Object[]> models = new ArrayList<Object[]>();

	public static void load(Loader loader) {
		Log.info("Models... Loading");
		for (Models model : Models.values()) {
			ModelTexture texture = new ModelTexture(loader.loadTexture(model.texture));

			texture.setFakeLighting(model.fakeLighting);
			texture.setTransparency(model.transparent);
			texture.setShine(model.shineDamper, model.refelectivity);
			addModel(model.key, new TexturedModel(loader.loadModel(model.model), texture));
		}
		
		Log.info("Models... Loaded");
	}

	private static void addModel(String key, TexturedModel model) {
		models.add(new Object[]{key, model});
		Log.info("Added model " + key);
	}

	public static TexturedModel get(String key) {
		for (Object[] data : models) {
			if (data[0].equals(key)) return (TexturedModel) data[1];
		}
		Log.warn("No model found with key '" + key + "'");
		return null;
	}
}
