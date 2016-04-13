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
package net.roryclaasen.sandbox.state;

import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.Sandbox;

import org.lwjgl.opengl.GL11;

public class GameStateManager {

	private Sandbox sandbox;

	public static enum State {
		MENU, GAME
	}

	private State current;
	private GameState currentState;
	private GameState[] states = new GameState[State.values().length];

	public GameStateManager(Sandbox sandbox) {
		this.sandbox = sandbox;
		try {
			states[0] = new GameStateMenu(this);
			states[1] = new GameStateLevel(this);
		} catch (Exception e) {

		}
	}

	public void update() {
		if (currentState != null) currentState.update();
	}

	public void render() {
		if (currentState != null) currentState.render();

		GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
		sandbox.rendererGui.render(sandbox.guiManager.getGuis());
	}

	public void setState(State state) {
		if (state != current) {
			if (currentState != null) currentState.reset();
			currentState = states[State.valueOf((current = state).name()).ordinal()];
			currentState.init();
			Log.info("Changed Game state to " + state);
		}
	}

	public State getCurrent() {
		return current;
	}

	public void cleanUp() {
		for (GameState state : states) {
			state.cleanUp();
		}
	}

	public Sandbox getSandbox() {
		return sandbox;
	}
}
