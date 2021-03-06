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
package net.roryclaasen.sandbox.RenderEngine.skybox;

import net.roryclaasen.sandbox.entities.EntityManager;
import net.roryclaasen.sandbox.util.Maths;

import org.lwjgl.util.vector.Vector3f;

public class Skybox {


	private static float blendFactor = 0.0f;

	private static Vector3f fogColourDay = Maths.rgbToVec3(255, 255,255);
	private static Vector3f fogColour = new Vector3f(fogColourDay.getX(), fogColourDay.getY(), fogColourDay.getZ());

	public static float getBlendFactor() {
		return blendFactor;
	}

	public static Vector3f getFogColour() {
		return fogColour;
	}

	public void tick(EntityManager entityManager) {}

	public void start() {}
}
