package net.roryclaasen.sandbox.RenderEngine.models;

import net.gogo98901.log.Level;
import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.util.Loader;

public class Models {

	public static void load(Loader loader) {
		Log.info("Models... Loading");
		try {
			Log.info("Models... OK");
		} catch (Exception e) {
			Log.severe("Models... FAILED");
			Log.stackTrace(Level.SEVERE, e);
			Log.save();

			System.exit(0);
		}
	}

	public static TexturedModel get(String id) {
		return null;
	}
}
