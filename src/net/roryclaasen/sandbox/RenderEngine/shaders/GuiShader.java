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
package net.roryclaasen.sandbox.RenderEngine.shaders;

import org.lwjgl.util.vector.Matrix4f;

public class GuiShader extends ShaderProgram {

	private static final String VERT_FILE = "gui.vert", FRAG_FILE = "gui.frag";
	
	private int location_transformationMatrix;

	public GuiShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}

}
