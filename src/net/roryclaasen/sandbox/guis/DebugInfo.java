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
package net.roryclaasen.sandbox.guis;

import java.util.ArrayList;
import java.util.List;

import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.RenderEngine.font.BorderEffect;
import net.roryclaasen.sandbox.RenderEngine.font.GUIText;
import net.roryclaasen.sandbox.RenderEngine.font.TextMaster;
import net.roryclaasen.sandbox.RenderEngine.font.data.FontType;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

public class DebugInfo {
	public static final BorderEffect DEBUG_EFFECT = new BorderEffect(new Vector3f(0.2f, 0.2f, 0.2f)).setBorderWidth(5f);
	public final static Vector2f COORDS = new Vector2f(0, 0);
	
	private static Vector3f color = new Vector3f(0, 1, 0);
	private static FontType font = TextMaster.moire;
	private static float size = 1f;
	
	private static float distance = 0.0245f;


	private static List<Object[]> lines = new ArrayList<Object[]>();

	private DebugInfo() {
	}

	public static void add(String key, String text) {
		GUIText line = new GUIText(text, size, font, new Vector2f(COORDS.getX(), COORDS.getY() + (distance * lines.size())), 1F, false);
		line.setColor(color);
		line.border(DEBUG_EFFECT);
		lines.add(new Object[] { key, line });
	}

	public static void add(String key, int index, String text) {
		GUIText line = new GUIText(text, size, font, new Vector2f(COORDS.getX(), COORDS.getY() + (distance * index)), 1F, false);
		line.setColor(color);
		line.border(DEBUG_EFFECT);
		lines.add(index, new Object[] { key, line });
		updateAllPositions();
	}

	public static void update(String key, String text) {
		int arraryPosition = getPosition(key);
		if (arraryPosition >= 0) {
			Object[] line = lines.get(arraryPosition);
			((GUIText) line[1]).update(text, -1f, null, null, -1f, false, null);
		}
	}

	public static void updateAllPositions() {
		for (int i = 0; i < lines.size(); i++) {
			Object[] line = lines.get(i);
			GUIText text = (GUIText) line[1];
			text.update(null, -1f, null, new Vector2f(COORDS.getX(), COORDS.getY() + (distance * i)), -1f, false, null);
		}
	}


	public static void remove(String key) {
		int position = getPosition(key);
		if (position >= 0){
			Object[] line = lines.get(position);
			((GUIText) line[1]).remove();
			lines.remove(position);
		}

		updateAllPositions();
	}

	public static int getPosition(String key) {
		for (int i = 0; i < lines.size(); i++) {
			Object[] line = lines.get(i);
			if (line[0].equals(key)) return i;
		}
		Log.warn("No line found with key '" + key + "'");
		return -1;
	}
}
