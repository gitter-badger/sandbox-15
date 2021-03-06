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
package net.roryclaasen;

import java.io.File;

import net.gogo98901.log.Level;
import net.gogo98901.log.Log;
import net.gogo98901.util.Data;
import net.roryclaasen.sandbox.Sandbox;
import net.roryclaasen.sandbox.util.Arguments;
import net.roryclaasen.sandbox.util.config.ConfigLoader;

public class Bootstrap {

	public static final String GAME_PATH = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "My Games" + File.separator + GameInfo.title + File.separator;

	private static Arguments arguments;
	private static ConfigLoader conf;
	
	private static Version version;

	public static void main(String[] args) {
		Log.setDateFormat("[dd.MM.yy hh:mm:ss]");
		Log.info(GameInfo.title + "(" + GameInfo.version + ") by Rory Claasen");
		arguments = new Arguments(args);
		version = new Version();
		if (init(arguments)) {
			Log.info("Pre-Initializing... OKAY");
			version.startCheck();
			start();
		}
		if (conf != null) conf.save();
		Log.info("Goodbye");
		Log.save();
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
			if (arguments.doLoadConfig()) conf.load();
			return true;
		} catch (Exception e) {
			Log.severe("Game failed to run");
			Log.stackTrace(Level.WARNING, e);
			return false;
		}
	}

	private static void start() {
		Log.info("Starting game: " + GameInfo.title );
		Sandbox sandbox = new Sandbox(arguments);
		sandbox.start();
	}
}
