/*
   Copyright 2016 Rory Claasen

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

import net.roryclaasen.sandbox.RenderEngine.ImageRenderer;
import net.roryclaasen.sandbox.RenderEngine.post.shaders.ContrastShader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class ContrastChanger {

	private ImageRenderer renderer;
	private ContrastShader shader;

	public ContrastChanger() {
		renderer = new ImageRenderer();
		shader = new ContrastShader();
	}

	public void render(int texture) {
		shader.start();
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		shader.stop();
	}

	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}
}
