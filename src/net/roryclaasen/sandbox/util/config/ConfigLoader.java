package net.roryclaasen.sandbox.util.config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import net.gogo98901.log.Log;
import net.roryclaasen.Bootstrap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ConfigLoader {

	private JSONParser parser;
	private static JSONObject config;

	public ConfigLoader() {
		parser = new JSONParser();
		config = new JSONObject();
	}

	public void load() {
		Log.info("Config loading");
		if (!new File(getFile()).exists()) {
			Log.warn("Config not found");
			new File(Bootstrap.GAME_PATH).mkdirs();
			addMissingValues();
			save();
		}
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

	private void addMissingValues() {
		int count = 0;
		for (Config cfg : Config.values()) {
			if (!config.containsKey(cfg.name())) {
				count++;
				set(cfg.name(), cfg.getDefaultValue());
			}
		}
		if(count!=0)Log.info("Config added " + count + " missing value(s)");
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

	@SuppressWarnings("unchecked")
	public static void set(String name, Object newValue) {
		config.put(name, newValue);
	}

	public static Object get(String name, Object defaultValue) {
		if (!config.containsKey(name)) set(name, defaultValue);
		return config.get(name);
	}

	public String getFile() {
		return Bootstrap.GAME_PATH + "/config.cfg";
	}

}
