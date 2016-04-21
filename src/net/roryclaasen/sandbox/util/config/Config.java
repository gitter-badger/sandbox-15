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

public class Config {
	protected static List<ConfigType<?>> list = new ArrayList<ConfigType<?>>();

	// Display
	public static ConfigType<Integer> width = new ConfigType<Integer>("width", 1280);
	public static ConfigType<Integer> height = new ConfigType<Integer>("height", 720);
	public static ConfigType<Integer> fpsCap = new ConfigType<Integer>("fps-cap", 120);

	// Camera & Movement
	public static ConfigType<Integer> fov = new ConfigType<Integer>("camera-fov", 70);
	public static ConfigType<Float> sensitivity = new ConfigType<Float>("mouse-sensitivity", 0.5f);
	public static ConfigType<Float> movingSpeed = new ConfigType<Float>("player-moving-speed", 120F);

	// World
	public static ConfigType<Float> skyRotate = new ConfigType<Float>("sky-rotate-speed", 0.5F);

	// Render
	public static ConfigType<Boolean> anisotropic = new ConfigType<Boolean>("anisotropic-filtering", true);
	public static ConfigType<Boolean> antialiasing = new ConfigType<Boolean>("antialiasing", false);
	public static ConfigType<Integer> antialiasingSample = new ConfigType<Integer>("antialiasing-sample", 4);
	
	// Miscellaneous
	public static ConfigType<String> language = new ConfigType<String>("language", "en_UK");
}
