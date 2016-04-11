/** 
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
package net.roryclaasen.sandbox.RenderEngine.texture;

public class ModelTexture {
	private int textureID;

	private float shineDamper = 1;
	private float refelectivity = 0;
	
	private boolean hasTransparency = false;
	private boolean fakeLighting = false;
	
	private int numberOfRows = 1;

	public ModelTexture(int id) {
		this.textureID = id;
	}

	public int getID() {
		return textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public float getRefelectivity() {
		return refelectivity;
	}

	public void setRefelectivity(float refelectivity) {
		this.refelectivity = refelectivity;
	}

	public boolean hasTransparency() {
		return hasTransparency;
	}

	public ModelTexture setTransparency(boolean hasTransparency) {
		this.hasTransparency = hasTransparency;
		return this;
	}

	public boolean useFakeLighting() {
		return fakeLighting;
	}

	public ModelTexture setFakeLighting(boolean fakeLighting) {
		this.fakeLighting = fakeLighting;
		return this;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}

	public ModelTexture setNumberOfRows(int numberOfRows) {
		this.numberOfRows = numberOfRows;
		return this;
	}

	public ModelTexture setShine(float shineDamper, float refelectivity) {
		setShineDamper(shineDamper);
		setRefelectivity(refelectivity);
		return this;
	}
}
