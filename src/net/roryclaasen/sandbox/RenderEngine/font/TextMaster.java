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
package net.roryclaasen.sandbox.RenderEngine.font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.RenderEngine.FontRenderer;
import net.roryclaasen.sandbox.util.Loader;

public class TextMaster {

	// FONTS

	public static FontType arial, sans, moire, moireDistance;

	// TextMaster

	private static Loader loader;
	private static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	private static FontRenderer renderer;

	public static void init(Loader loader) {
		TextMaster.loader = loader;
		renderer = new FontRenderer();

		loadFonts();
	}

	private static void loadFonts() {
		Log.info("Fonts... Loading");
		arial = new FontType(loader.loadTextureFont("arial"), "arial");
		sans = new FontType(loader.loadTextureFont("sans"), "sans");
		moire = new FontType(loader.loadTextureFont("moire"), "moire");
		moire = new FontType(loader.loadTextureFont("moire.dist"), "moire.dist");
		Log.info("Fonts... Done");
	}

	public static void render() {
		renderer.render(texts);
	}

	public static void loadText(GUIText text) {
		FontType font = text.getFont();
		TextMeshData data = font.loadText(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GUIText> textBatch = texts.get(font);
		if (textBatch == null) {
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}

	public static void removeText(GUIText text) {
		List<GUIText> textBatch = texts.get(text.getFont());
		textBatch.remove(text);
		if (textBatch.isEmpty()) {
			texts.remove(text.getFont());
		}
	}

	public static void cleanUp() {
		renderer.cleanUp();
	}
}
