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
package net.roryclaasen.sandbox.RenderEngine.font.data;

import net.roryclaasen.sandbox.RenderEngine.font.GUIText;
import net.roryclaasen.sandbox.RenderEngine.font.TextMeshData;

public class FontType {

	private int textureAtlas;
	private TextMeshCreator loader;
	private boolean distanceField;

	public FontType(int textureAtlas, String fontFile) {
		this(textureAtlas, fontFile, false);
	}

	public FontType(int textureAtlas, String fontFile, boolean isDistanceField) {
		this.textureAtlas = textureAtlas;
		this.distanceField = isDistanceField;
		this.loader = new TextMeshCreator(this, fontFile);
	}

	public int getTextureAtlas() {
		return textureAtlas;
	}

	public TextMeshData loadText(GUIText text) {
		return loader.createTextMesh(text);
	}

	public boolean isDistanceField() {
		return distanceField;
	}
}
