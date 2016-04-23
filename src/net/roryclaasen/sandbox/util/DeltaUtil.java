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
package net.roryclaasen.sandbox.util;

import net.roryclaasen.sandbox.guis.DebugInfo;

import org.lwjgl.Sys;

public class DeltaUtil {

	private long lastFrame;

	private int fps;

	private long lastFPS;
	
	private static float delta;

	public void start() {
		getDelta();
		lastFPS = getTime();
		DebugInfo.add("fps", 0, fps + " :fps");
	}

	public void update() {
		long time = getTime();
		delta = (time - lastFrame)  /1000f;
		lastFrame = time;

		if (getTime() - lastFPS > 1000) {
			DebugInfo.update("fps", fps + " :fps");
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public static float getDelta() {
		return delta;
	}

	public int getFps() {
		return fps;
	}
}
