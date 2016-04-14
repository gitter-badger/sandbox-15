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

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class BorderEffect {

	private float width, edge;
	private float borderWidth, borderEdge;

	private Vector2f offset;

	private Vector3f color;

	public BorderEffect(Vector3f color) {
		this.width = 0.5f;
		this.edge = 0.1f;
		this.borderWidth = 0.7f;
		this.borderEdge = 0.1f;
		this.color = color;
		this.offset = new Vector2f(0, 0);
	}

	public BorderEffect() {
		this(new Vector3f(0, 0, 0));
		this.borderWidth = 0f;
	}

	public float getWidth() {
		return width;
	}

	public BorderEffect setWidth(float width) {
		this.width = width;
		return this;
	}

	public float getEdge() {
		return edge;
	}

	public BorderEffect setEdge(float edge) {
		this.edge = edge;
		return this;
	}

	public float getBorderWidth() {
		return borderWidth;
	}

	public BorderEffect setBorderWidth(float borderWidth) {
		this.borderWidth = borderWidth;
		return this;
	}

	public float getBorderEdge() {
		return borderEdge;
	}

	public BorderEffect setBorderEdge(float borderEdge) {
		this.borderEdge = borderEdge;
		return this;
	}

	public Vector2f getOffset() {
		return offset;
	}

	public BorderEffect setOffset(Vector2f offset) {
		this.offset = offset;
		return this;
	}

	public Vector3f getColor() {
		return color;
	}

	public BorderEffect setColor(Vector3f color) {
		this.color = color;
		return this;
	}
}
