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
import net.roryclaasen.language.LangUtil;
import net.roryclaasen.sandbox.util.config.Config;

import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;
import org.newdawn.slick.opengl.PNGDecoder;

public class DisplayManager {

	private static final int WIDTH = Config.width.get(), HEIGHT = Config.height.get();
	private static final int FPS_CAP = Config.fpsCap.get();

	public DisplayManager() {
		Log.info("Display width: " + WIDTH);
		Log.info("Display height: " + HEIGHT);
		Log.info("FPS Cap: " + FPS_CAP);
	}

	public void createDisplay() {
		Log.info("Creating Display");
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			PixelFormat pixelFormat = new PixelFormat();

			Log.info("Display Adapter: " + Display.getAdapter());
			Log.info("Antialiasing: " + Config.antialiasing.get());
			if (Config.antialiasing.get()) pixelFormat = pixelFormat.withSamples(Config.antialiasingSample.get());

			Log.info("LWJGL version: " + org.lwjgl.Sys.getVersion());
			Display.create(pixelFormat, attribs);
			Log.info("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));

			Display.setTitle(LangUtil.get("sandbox.title"));
			if (Config.antialiasing.get()) GL11.glEnable(GL13.GL_MULTISAMPLE);
			Display.setIcon(getIcons());
			
			Display.setInitialBackground(1f, 1f, 1f);
			Log.info("Display Created");
		} catch (Exception e) {
			Log.stackTrace(Level.SEVERE, e);
			System.exit(-1);
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);
	}

	private ByteBuffer[] getIcons() throws IOException {
		return new ByteBuffer[]{loadIcon("icon16.png"), loadIcon("icon32.png"),};
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
	}

	public void destroyDisplay() {
		Display.destroy();
		Log.info("Display Destroyed");
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
