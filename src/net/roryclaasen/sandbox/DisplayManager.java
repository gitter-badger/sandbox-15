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
package net.roryclaasen.sandbox;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.ByteBuffer;

import net.gogo98901.log.Level;
import net.gogo98901.log.Log;
import net.gogo98901.util.Loader;
import net.roryclaasen.Bootstrap;
import net.roryclaasen.sandbox.crash.CrashHandler;
import net.roryclaasen.sandbox.util.config.Config;

import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.opengl.PNGDecoder;

public class DisplayManager {

	private static final int WIDTH = Config.width.getIntager(), HEIGHT = (int) Config.height.getIntager();
	private static final int FPS_CAP = Config.fpsCap.getIntager();

	private long lastFrameTime = 0;
	private static float delta = 0;

	public void createDisplay() {
		Log.info("Creating Display");
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			PixelFormat pixelFormat = new PixelFormat();
			if (Config.antialiasing.getBoolean()) {
				pixelFormat = pixelFormat.withSamples(Config.antialiasingSample.getIntager());
			}
			Display.create(pixelFormat, attribs);
			Display.setTitle(Bootstrap.TITLE);
			if (Config.antialiasing.getBoolean()) GL11.glEnable(GL13.GL_MULTISAMPLE);
			// Display.setIcon(getIcons());
			Log.info("Display Created");
		} catch (Exception e) {
			Log.stackTrace(Level.SEVERE, e);
			CrashHandler.show(e);
			System.exit(-1);
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}

	@SuppressWarnings("unused")
	private ByteBuffer[] getIcons() throws IOException {
		return new ByteBuffer[] { loadIcon("icon16.png"), loadIcon("icon32.png"), };
	}

	private ByteBuffer loadIcon(String file) throws IOException {
		URL url = Loader.getResource(file);
		InputStream is = url.openStream();
		try {
			PNGDecoder decoder = new PNGDecoder(is);
			ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
			decoder.decode(bb, decoder.getWidth() * 4, PNGDecoder.RGBA);
			bb.flip();
			return bb;
		} finally {
			is.close();
		}
	}

	public void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		long currentTime = getCurrentTime();
		delta = (currentTime - lastFrameTime) / 1000f;
		lastFrameTime = currentTime;
	}

	public static float getFrameTimeSeconds() {
		return delta;
	}

	public void destroyDisplay() {
		Display.destroy();
		Log.info("Display Destroyed");
	}

	private long getCurrentTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public static int getFpsCap() {
		return FPS_CAP;
	}
}
