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
package net.roryclaasen.sandbox.util.config;

public enum Config {
	// Display
	width("width", 1280), height("height", 720), fpsCap("fps-cap", 120),

	// Camera & Movement
	fov("camera-fov", 70), sensitivity("mouse-sensitivity", 0.5f), movingSpeed("player-moving-speed", 120F),

	// World
	skyRotate("sky-rotate-speed", 0.5F),

	// Render
	anisotropic("anisotropic-filtering", true), antialiasing("antialiasing", false), antialiasingSample("antialiasing-sample", 4)

	// END OF CONFIG
	;

	private String name;
	private Object defaultValue;

	Config(String name, Object defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}

	public void set(Object newValue) {
		ConfigLoader.set(this.name, newValue);
	}

	public Object get() {
		return ConfigLoader.get(this.name, this.defaultValue);
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public String getString() {
		return "" + get();
	}

	public int getIntager() {
		return Integer.parseInt(getString());
	}

	public boolean getBoolean() {
		return Boolean.parseBoolean(getString());
	}

	public float getFloat() {
		return Float.parseFloat(getString());
	}
}
