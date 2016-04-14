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
package net.roryclaasen.sandbox.RenderEngine.particle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import net.roryclaasen.sandbox.RenderEngine.ParticleRenderer;
import net.roryclaasen.sandbox.entities.Camera;
import net.roryclaasen.sandbox.util.Loader;

public class ParticleMaster {

	private static List<Particle> particles = new ArrayList<Particle>();
	private static ParticleRenderer renderer;

	public static void init(Loader loader, Matrix4f projectionMatrix) {
		renderer = new ParticleRenderer(loader, projectionMatrix);
	}

	public static void update() {
		Iterator<Particle> iterator = particles.iterator();
		while (iterator.hasNext()) {
			Particle p = iterator.next();
			boolean stillAlive = p.update();
			if (!stillAlive) {
				iterator.remove();
			}
		}
	}

	public static void renderParticles(Camera camera) {
		renderer.render(particles, camera);
	}
	
	public static void addParticle(Particle particle){
		particles.add(particle);
	}

	public static void cleanUp() {
		renderer.cleanUp();
	}
}
