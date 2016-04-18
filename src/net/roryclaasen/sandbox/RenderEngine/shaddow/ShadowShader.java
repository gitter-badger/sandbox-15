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
package net.roryclaasen.sandbox.RenderEngine.shaddow;

import net.roryclaasen.sandbox.RenderEngine.shaders.ShaderProgram;

import org.lwjgl.util.vector.Matrix4f;

public class ShadowShader extends ShaderProgram {

	private static final String VERT_FILE = "shadow.vert", FRAG_FILE = "shadow.frag";
	private int location_mvpMatrix;

	public ShadowShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	public void getAllUniformLocations() {
		location_mvpMatrix = super.getUniformLocation("mvpMatrix");

	}

	public void loadMvpMatrix(Matrix4f mvpMatrix) {
		super.loadMatrix(location_mvpMatrix, mvpMatrix);
	}

	@Override
	public void bindAttributes() {
		super.bindAttribute(0, "in_position");
		super.bindAttribute(1, "in_textureCoords");
	}

}
