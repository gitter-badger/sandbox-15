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
