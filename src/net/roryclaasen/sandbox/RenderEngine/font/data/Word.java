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

import java.util.ArrayList;
import java.util.List;

public class Word {

	private List<Character> characters = new ArrayList<Character>();
	private double width = 0;
	private double fontSize;

	public Word(double fontSize) {
		this.fontSize = fontSize;
	}

	public void addCharacter(Character character) {
		characters.add(character);
		width += character.getxAdvance() * fontSize;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public double getWordWidth() {
		return width;
	}
}
