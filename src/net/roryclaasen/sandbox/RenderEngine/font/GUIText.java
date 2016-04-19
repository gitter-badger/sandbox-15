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
package net.roryclaasen.sandbox.RenderEngine.font;

import net.roryclaasen.sandbox.RenderEngine.font.data.FontType;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class GUIText {

	private String textString;
	private float fontSize;

	private int textMeshVao;
	private int vertexCount;
	private Vector3f colour = new Vector3f(0f, 0f, 0f);

	private Vector2f position;
	private float lineMaxSize;
	private int numberOfLines;

	private FontType font;

	private boolean centerText = false;

	private BorderEffect effect = new BorderEffect();

	public GUIText(String text, float fontSize, FontType font, Vector2f position, float maxLineLength, boolean centered) {
		this.textString = text;
		this.fontSize = fontSize;
		this.font = font;
		this.position = position;
		this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		TextMaster.loadText(this);
	}

	public GUIText border(BorderEffect effect) {
		this.effect = effect;
		return this;
	}

	public void remove() {
		TextMaster.removeText(this);
	}

	public FontType getFont() {
		return font;
	}

	public void setColor(Vector3f colour) {
		this.colour.set(colour);
	}

	public void setColor(float r, float g, float b) {
		colour.set(r, g, b);
	}

	public Vector3f getColour() {
		return colour;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public Vector2f getPosition() {
		return position;
	}

	public int getMesh() {
		return textMeshVao;
	}

	public void setMeshInfo(int vao, int verticesCount) {
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
	}

	public int getVertexCount() {
		return this.vertexCount;
	}

	public float getFontSize() {
		return fontSize;
	}

	public void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}

	public boolean isCentered() {
		return centerText;
	}

	public float getMaxLineSize() {
		return lineMaxSize;
	}

	public String getTextString() {
		return textString;
	}

	public void update(String text, float fontSize, FontType font, Vector2f position, float maxLineLength, boolean centered, BorderEffect effect) {
		TextMaster.removeText(this);
		if (text != null) this.textString = text;
		if (fontSize != -1f) this.fontSize = fontSize;
		if (font != null) this.font = font;
		if (position != null) this.position = position;
		if (maxLineLength != -1f) this.lineMaxSize = maxLineLength;
		this.centerText = centered;
		if (effect != null) this.effect = effect;
		TextMaster.loadText(this);
	}

	public BorderEffect getBorder() {
		return effect;
	}
}
