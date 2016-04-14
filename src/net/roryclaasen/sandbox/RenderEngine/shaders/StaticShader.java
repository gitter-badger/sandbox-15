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

import java.util.List;

import net.roryclaasen.sandbox.entities.Camera;
import net.roryclaasen.sandbox.entities.light.Light;
import net.roryclaasen.sandbox.util.Maths;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class StaticShader extends ShaderProgram {

	private static final String VERT_FILE = "shader.vert", FRAG_FILE = "shader.frag";

	private static final int MAX_LIGHTS = 4;

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_refelectivity;
	private int location_fakeLighting;
	private int location_skyColour;
	private int location_numberOfRows;
	private int location_offset;
	private int location_plane;

	public StaticShader() {
		super(VERT_FILE, FRAG_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColour = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_refelectivity = super.getUniformLocation("refelectivity");
		location_fakeLighting = super.getUniformLocation("useFakeLighting");
		location_skyColour = super.getUniformLocation("skyColour");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
		location_plane = super.getUniformLocation("plane");

		for (int i = 0; i < MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}

	public void loadClipPlane(Vector4f plane) {
		super.loadVector(location_plane, plane);
	}

	public void loadNumberOfRows(int rows) {
		super.loadFloat(location_numberOfRows, rows);
	}

	public void loadTextureOffset(float x, float y) {
		super.loadVector(location_offset, new Vector2f(x, y));
	}

	public void loadSkyClourVariable(float r, float g, float b) {
		super.loadVector(location_skyColour, new Vector3f(r, g, b));
	}

	public void loadUseFakeLightingVariable(boolean fakeLight) {
		super.loadBoolean(location_fakeLighting, fakeLight);
	}

	public void loadShineVariables(float damper, float refelectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_refelectivity, refelectivity);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadLights(List<Light> lights) {
		for (int i = 0; i < MAX_LIGHTS; i++) {
			if (i < lights.size()) {
				Light light = lights.get(i);
				super.loadVector(location_lightPosition[i], light.getPosition());
				super.loadVector(location_lightColour[i], light.getColor());
				super.loadVector(location_attenuation[i], light.getAttenuation());
			} else {
				super.loadVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(location_lightColour[i], new Vector3f(0, 0, 0));
				super.loadVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}

	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}
}
