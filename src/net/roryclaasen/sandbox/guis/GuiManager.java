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
package net.roryclaasen.sandbox.guis;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {
	private List<GuiTexture> guis = new ArrayList<GuiTexture>();

	public void add(GuiTexture gui) {
		guis.add(gui);
	}

	public void add(int pos, GuiTexture gui) {
		guis.add(pos, gui);
	}

	public List<GuiTexture> getGuis() {
		return guis;
	}

	public void remove(int index) {
		if (guis.size() < index) guis.remove(index);
	}
	
	public void removeAll(){
		guis.removeAll(guis);
	}
}
