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
package net.roryclaasen.sandbox.RenderEngine.post;

import net.roryclaasen.sandbox.RenderEngine.models.RawModel;
import net.roryclaasen.sandbox.util.Loader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class PostProcessing {

	private static final float[] POSITIONS = {-1, 1, -1, -1, 1, 1, 1, -1};
	private static RawModel quad;

	private static ContrastChanger contrastChanger;

	public static void init(Loader loader) {
		quad = loader.loadToVAO(POSITIONS, 2);

		contrastChanger = new ContrastChanger();
	}

	public static void doPostProcessing(int colourTexture) {
		start();
		contrastChanger.render(colourTexture);
		end();
	}

	public static void cleanUp() {
		contrastChanger.cleanUp();
	}

	private static void start() {
		GL30.glBindVertexArray(quad.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	private static void end() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}

}
