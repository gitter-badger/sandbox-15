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

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.opengl.Display;

public class MetaFile {

	private static final int PAD_TOP = 0;
	private static final int PAD_LEFT = 1;
	private static final int PAD_BOTTOM = 2;
	private static final int PAD_RIGHT = 3;

	private static final int DESIRED_PADDING_NORMAL = 3;
	private static final int DESIRED_PADDING_DISTANCE = 8;

	private static final String SPLITTER = " ";
	private static final String NUMBER_SEPARATOR = ",";

	private FontType font;

	private double aspectRatio;

	private double verticalPerPixelSize;
	private double horizontalPerPixelSize;
	private double spaceWidth;
	private int[] padding;
	private int paddingWidth;
	private int paddingHeight;

	private Map<Integer, Character> metaData = new HashMap<Integer, Character>();

	private BufferedReader reader;
	private Map<String, String> values = new HashMap<String, String>();

	/**
	 * Opens a font file in preparation for reading.
	 * 
	 * @param file
	 *            - the font file.
	 */
	protected MetaFile(FontType font, String file) {
		this.font = font;
		this.aspectRatio = (double) Display.getWidth() / (double) Display.getHeight();
		openFile(file);
		loadPaddingData();
		loadLineSizes();
		int imageWidth = getValueOfVariable("scaleW");
		loadCharacterData(imageWidth);
		close();
	}

	protected double getSpaceWidth() {
		return spaceWidth;
	}

	protected Character getCharacter(int ascii) {
		return metaData.get(ascii);
	}

	/**
	 * Read in the next line and store the variable values.
	 * 
	 * @return {@code true} if the end of the file hasn't been reached.
	 */
	private boolean processNextLine() {
		values.clear();
		String line = null;
		try {
			line = reader.readLine();
		} catch (IOException e1) {
		}
		if (line == null) {
			return false;
		}
		for (String part : line.split(SPLITTER)) {
			String[] valuePairs = part.split("=");
			if (valuePairs.length == 2) {
				values.put(valuePairs[0], valuePairs[1]);
			}
		}
		return true;
	}

	/**
	 * Gets the {@code int} value of the variable with a certain name on the
	 * current line.
	 * 
	 * @param variable
	 *            - the name of the variable.
	 * @return The value of the variable.
	 */
	private int getValueOfVariable(String variable) {
		return Integer.parseInt(values.get(variable));
	}

	/**
	 * Gets the array of ints associated with a variable on the current line.
	 * 
	 * @param variable
	 *            - the name of the variable.
	 * @return The int array of values associated with the variable.
	 */
	private int[] getValuesOfVariable(String variable) {
		String[] numbers = values.get(variable).split(NUMBER_SEPARATOR);
		int[] actualValues = new int[numbers.length];
		for (int i = 0; i < actualValues.length; i++) {
			actualValues[i] = Integer.parseInt(numbers[i]);
		}
		return actualValues;
	}

	/**
	 * Closes the font file after finishing reading.
	 */
	private void close() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Opens the font file, ready for reading.
	 * 
	 * @param file
	 *            - the font file.
	 */
	private void openFile(String file) {
		if (!file.endsWith(".fnt")) file += ".fnt";
		try {
			reader = net.gogo98901.util.Loader.getBufferedStreamReader("assets/fonts/" + file);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Couldn't read font meta file!");
		}
	}

	/**
	 * Loads the data about how much padding is used around each character in
	 * the texture atlas.
	 */
	private void loadPaddingData() {
		processNextLine();
		this.padding = getValuesOfVariable("padding");
		this.paddingWidth = padding[PAD_LEFT] + padding[PAD_RIGHT];
		this.paddingHeight = padding[PAD_TOP] + padding[PAD_BOTTOM];
	}

	/**
	 * Loads information about the line height for this font in pixels, and uses
	 * this as a way to find the conversion rate between pixels in the texture
	 * atlas and screen-space.
	 */
	private void loadLineSizes() {
		processNextLine();
		int lineHeightPixels = getValueOfVariable("lineHeight") - paddingHeight;
		verticalPerPixelSize = TextMeshCreator.LINE_HEIGHT / (double) lineHeightPixels;
		horizontalPerPixelSize = verticalPerPixelSize / aspectRatio;
	}

	/**
	 * Loads in data about each character and stores the data in the {@link Character} class.
	 * 
	 * @param imageWidth
	 *            - the width of the texture atlas in pixels.
	 */
	private void loadCharacterData(int imageWidth) {
		processNextLine();
		processNextLine();
		while (processNextLine()) {
			Character c = loadCharacter(imageWidth);
			if (c != null) {
				metaData.put(c.getId(), c);
			}
		}
	}

	/**
	 * Loads all the data about one character in the texture atlas and converts
	 * it all from 'pixels' to 'screen-space' before storing. The effects of
	 * padding are also removed from the data.
	 * 
	 * @param imageSize
	 *            - the size of the texture atlas in pixels.
	 * @return The data about the character.
	 */
	private Character loadCharacter(int imageSize) {
		int id = getValueOfVariable("id");
		if (id == TextMeshCreator.SPACE_ASCII) {
			this.spaceWidth = (getValueOfVariable("xadvance") - paddingWidth) * horizontalPerPixelSize;
			return null;
		}
		double xTex = ((double) getValueOfVariable("x") + (padding[PAD_LEFT] - getDesiredPadding())) / imageSize;
		double yTex = ((double) getValueOfVariable("y") + (padding[PAD_TOP] - getDesiredPadding())) / imageSize;
		int width = getValueOfVariable("width") - (paddingWidth - (2 * getDesiredPadding()));
		int height = getValueOfVariable("height") - ((paddingHeight) - (2 * getDesiredPadding()));
		double quadWidth = width * horizontalPerPixelSize;
		double quadHeight = height * verticalPerPixelSize;
		double xTexSize = (double) width / imageSize;
		double yTexSize = (double) height / imageSize;
		double xOff = (getValueOfVariable("xoffset") + padding[PAD_LEFT] - getDesiredPadding()) * horizontalPerPixelSize;
		double yOff = (getValueOfVariable("yoffset") + (padding[PAD_TOP] - getDesiredPadding())) * verticalPerPixelSize;
		double xAdvance = (getValueOfVariable("xadvance") - paddingWidth) * horizontalPerPixelSize;
		return new Character(id, xTex, yTex, xTexSize, yTexSize, xOff, yOff, quadWidth, quadHeight, xAdvance);
	}

	private int getDesiredPadding() {
		if (font.isDistanceField()) return DESIRED_PADDING_DISTANCE;
		return DESIRED_PADDING_NORMAL;
	}
}
