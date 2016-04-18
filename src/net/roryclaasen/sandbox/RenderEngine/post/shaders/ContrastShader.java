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
package net.roryclaasen.sandbox.RenderEngine.post.shaders;

import net.roryclaasen.sandbox.RenderEngine.shaders.PostShaderProgram;


public class ContrastShader extends PostShaderProgram {

	private static final String VERT_FILE = "contrast.vert", FRAG_FILE = "contrast.frag";

	public ContrastShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	public void getAllUniformLocations() {}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}
