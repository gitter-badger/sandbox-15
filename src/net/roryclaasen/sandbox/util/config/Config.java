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

import java.util.ArrayList;
import java.util.List;

public class Config<T> {

	public static List<Config<?>> list = new ArrayList<Config<?>>();

	// Display
	public static Config<Integer> width = new Config<Integer>("width", 1280);
	public static Config<Integer> height = new Config<Integer>("height", 720);
	public static Config<Integer> fpsCap = new Config<Integer>("fps-cap", 120);

	// Camera & Movement
	public static Config<Integer> fov = new Config<Integer>("camera-fov", 70);
	public static Config<Float> sensitivity = new Config<Float>("mouse-sensitivity", 0.5f);
	public static Config<Float> movingSpeed = new Config<Float>("player-moving-speed", 120F);

	// World
	public static Config<Float> skyRotate = new Config<Float>("sky-rotate-speed", 0.5F);

	// Render
	public static Config<Boolean> anisotropic = new Config<Boolean>("anisotropic-filtering", true);
	public static Config<Boolean> antialiasing = new Config<Boolean>("antialiasing", false);
	public static Config<Integer> antialiasingSample = new Config<Integer>("antialiasing-sample", 4);

	private String name;
	private T type;

	private Config(String name, T defaultValue) {
		this.name = name;
		this.type = defaultValue;
		list.add(this);
	}

	public void set(T value) {
		ConfigLoader.set(this.name, value);
	}

	@SuppressWarnings("unchecked")
	public T get() {
		return (T) ConfigLoader.get(this.name, this.type);
	}

	public T getDefaultValue() {
		return type;
	}

	public String getName() {
		return name;
	}
}
