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

public class Character {

	private int id;
	private double xTextureCoord;
	private double yTextureCoord;
	private double xMaxTextureCoord;
	private double yMaxTextureCoord;
	private double xOffset;
	private double yOffset;
	private double sizeX;
	private double sizeY;
	private double xAdvance;

	/**
	 * @param id
	 *            - the ASCII value of the character.
	 * @param xTextureCoord
	 *            - the x texture coordinate for the top left corner of the
	 *            character in the texture atlas.
	 * @param yTextureCoord
	 *            - the y texture coordinate for the top left corner of the
	 *            character in the texture atlas.
	 * @param xTexSize
	 *            - the width of the character in the texture atlas.
	 * @param yTexSize
	 *            - the height of the character in the texture atlas.
	 * @param xOffset
	 *            - the x distance from the curser to the left edge of the
	 *            character's quad.
	 * @param yOffset
	 *            - the y distance from the curser to the top edge of the
	 *            character's quad.
	 * @param sizeX
	 *            - the width of the character's quad in screen space.
	 * @param sizeY
	 *            - the height of the character's quad in screen space.
	 * @param xAdvance
	 *            - how far in pixels the cursor should advance after adding
	 *            this character.
	 */
	protected Character(int id, double xTextureCoord, double yTextureCoord, double xTexSize, double yTexSize, double xOffset, double yOffset, double sizeX, double sizeY, double xAdvance) {
		this.id = id;
		this.xTextureCoord = xTextureCoord;
		this.yTextureCoord = yTextureCoord;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.xMaxTextureCoord = xTexSize + xTextureCoord;
		this.yMaxTextureCoord = yTexSize + yTextureCoord;
		this.xAdvance = xAdvance;
	}

	protected int getId() {
		return id;
	}

	protected double getxTextureCoord() {
		return xTextureCoord;
	}

	protected double getyTextureCoord() {
		return yTextureCoord;
	}

	protected double getXMaxTextureCoord() {
		return xMaxTextureCoord;
	}

	protected double getYMaxTextureCoord() {
		return yMaxTextureCoord;
	}

	protected double getxOffset() {
		return xOffset;
	}

	protected double getyOffset() {
		return yOffset;
	}

	protected double getSizeX() {
		return sizeX;
	}

	protected double getSizeY() {
		return sizeY;
	}

	protected double getxAdvance() {
		return xAdvance;
	}

}
