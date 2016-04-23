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
package net.roryclaasen.sandbox.mode;

import java.util.Random;

import net.roryclaasen.sandbox.GameMaster;
import net.roryclaasen.sandbox.Sandbox;

public abstract class ModeBase {

	protected Random random = new Random();

	protected Sandbox _sand;
	protected GameMaster _master;

	private boolean focus, visible;

	public ModeBase(Sandbox sandbox,GameMaster gameMaster) {
		this._sand = sandbox;
		this._master = gameMaster;
		visible = true;
		focus = false;
	}

	public void renderBase() {
		if (visible) render();
	}

	public void tickBase(float delta) {
		if (visible) tick(delta);
	}

	public abstract void init();

	@Deprecated
	protected abstract void render();

	@Deprecated
	protected abstract void tick(float delta);

	public abstract void cleanUp();

	public void setFocus(boolean focus) {
		if (!visible) focus = false;
		this.focus = focus;
	}

	public boolean hasFocus() {
		return focus;
	}

	public void setVisible(boolean visible) {
		if (!visible) focus = false;
		this.visible = visible;
	}

	public boolean isVisible() {
		return visible;
	}

}
