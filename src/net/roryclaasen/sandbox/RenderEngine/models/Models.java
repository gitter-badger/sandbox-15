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
package net.roryclaasen.sandbox.RenderEngine.models;

import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.RenderEngine.texture.ModelTexture;
import net.roryclaasen.sandbox.util.Loader;

public class Models {

	public static TexturedModel tree;

	public static void load(Loader loader) {
		Log.info("Models... Loading");
		
		tree = new TexturedModel(loader.loadModel("trees/pine"), new ModelTexture(loader.loadTexture("models/trees/pine")).setTransparency(true));
		
		Log.info("Models... OK");
	}
}
