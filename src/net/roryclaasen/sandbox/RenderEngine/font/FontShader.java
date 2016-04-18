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

import net.roryclaasen.sandbox.RenderEngine.shaders.ShaderProgram;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class FontShader extends ShaderProgram {

	private static final String VERT_FILE = "font.vert", FRAG_FILE = "font.frag";

	private int location_translation;
	private int location_colour;

	private int location_width;
	private int location_edge;
	private int location_borderWidth;
	private int location_borderEdge;
	private int location_borderOffset;
	private int location_borderColour;

	public FontShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_translation = super.getUniformLocation("translation");
		location_colour = super.getUniformLocation("colour");

		location_width = super.getUniformLocation("width");
		location_edge = super.getUniformLocation("edge");
		location_borderWidth = super.getUniformLocation("borderWidth");
		location_borderEdge = super.getUniformLocation("borderEdge");
		location_borderOffset = super.getUniformLocation("offset");
		location_borderColour = super.getUniformLocation("outlineColour");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	public void loadColour(Vector3f colour) {
		super.loadVector(location_colour, colour);
	}

	public void loadTranslation(Vector2f translation) {
		super.loadVector(location_translation, translation);
	}

	public void loadBorderEffects(BorderEffect effect) {
		super.loadFloat(location_width, effect.getWidth());
		super.loadFloat(location_edge, effect.getEdge());
		super.loadFloat(location_borderWidth, effect.getBorderWidth());
		super.loadFloat(location_borderEdge, effect.getBorderEdge());
		super.loadVector(location_borderOffset, effect.getOffset());
		super.loadVector(location_borderColour, effect.getColor());
	}
}
