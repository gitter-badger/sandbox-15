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
package net.roryclaasen.sandbox.util.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.gogo98901.log.Log;
import net.roryclaasen.Bootstrap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigLoader {

	private JSONParser parser;
	private static JSONObject config;

	protected List<Config<?>> configList = new ArrayList<Config<?>>();

	public ConfigLoader() {
		parser = new JSONParser();
		config = new JSONObject();
		Log.info("Config set up");
	}

	public void load() {
		Log.info("Config loading");
		if (!new File(getFile()).exists()) {
			Log.warn("Config not found");
			new File(Bootstrap.GAME_PATH).mkdirs();
			addMissingValues();
			save();
			Log.info("Config loaded");
		} else {
			try {
				Object obj = parser.parse(new FileReader(getFile()));
				config = (JSONObject) obj;
				addMissingValues();
				Log.info("Config loaded");
			} catch (IOException | ParseException e) {
				Log.warn("Config failed to load");
				Log.stackTrace(e);
			}
		}
	}

	private void addMissingValues() {
		int count = 0;
		for (Config<?> cfg : Config.list) {
			if (!config.containsKey(cfg.getName())) {
				count++;
				set(cfg.getName(), cfg.getDefaultValue());
			}
		}
		if (count != 0) Log.info("Config added " + count + " missing value(s)");
	}

	public void save() {
		Log.info("Config saving");
		try {
			FileWriter file = new FileWriter(getFile());
			file.write(config.toJSONString());
			file.flush();
			file.close();
			Log.info("Config saved");
		} catch (IOException e) {
			Log.warn("Config failed to saved");
			Log.stackTrace(e);
		}
	}

	public void reset() {
		for (Config<?> cfg : Config.list) {
			set(cfg.getName(), cfg.getDefaultValue());
		}
	}

	@SuppressWarnings("unchecked")
	protected static void set(String name, Object newValue) {
		config.put(name, newValue);
	}

	protected static Object get(String name, Object defaultValue) {
		if (!config.containsKey(name)) set(name, defaultValue);
		return config.get(name);
	}

	public static String getFile() {
		return Bootstrap.GAME_PATH + "/config.cfg";
	}

}
