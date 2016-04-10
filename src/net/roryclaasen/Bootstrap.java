package net.roryclaasen;

import java.io.File;

import net.gogo98901.log.Level;
import net.gogo98901.log.Log;
import net.gogo98901.util.Data;
import net.roryclaasen.sandbox.Sandbox;
import net.roryclaasen.sandbox.util.Arguments;
import net.roryclaasen.sandbox.util.Options;
import net.roryclaasen.sandbox.util.config.ConfigLoader;

public class Bootstrap {

	public static final String TITLE = "Sandbox", VERSION = "0.1", GAME_PATH = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "My Games" + File.separator + TITLE + File.separator;

	private static Arguments arguments;
	private static ConfigLoader conf;
	private static Options options;

	public static void main(String[] args) {
		Log.setDateFormat("[dd.MM.yy hh:mm:ss]");
		Log.info(TITLE + "(" + VERSION + ") by Rory Claasen");
		arguments = new Arguments(args);
		if (init(arguments)) {
			start();
		}
		if (conf != null) conf.save();
		Log.info("Goodbye");
	}

	private static boolean init(Arguments arguments) {
		Log.info("Pre-Initializing...");
		try {
			Data.setDefultLookAndFeel();
			Log.setSave(true);
			Log.setSavePath(GAME_PATH, "latest.log");
			if (arguments.doSaveLog()) {
				// TODO Redundant
			}
			if (arguments.isDebugMode()) {
				Log.info("Game running in debug mode");
			}
			conf = new ConfigLoader();
			conf.load();
			options = new Options();
			options.init();
			return true;
		} catch (Exception e) {
			Log.severe("Game failed to run");
			Log.stackTrace(Level.WARNING, e);
			return false;
		}
	}

	private static void start() {
		Log.info("Starting from root...");
		Sandbox sandbox = new Sandbox(arguments, options);
		sandbox.start();
	}
}
