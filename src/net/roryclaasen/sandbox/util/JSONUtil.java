package net.roryclaasen.sandbox.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.gogo98901.util.Loader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtil {

	private JSONUtil() {
	}

	private static JSONParser parser = new JSONParser();

	public static JSONObject read(String file) throws FileNotFoundException, IOException, ParseException {
		return read(new File(file));
	}

	public static JSONObject read(File file) throws FileNotFoundException, IOException, ParseException {
		if (!file.exists()) return null;
		return (JSONObject) (Object) parser.parse(new FileReader(file));
	}


	public static JSONObject readFromJar(String file) throws FileNotFoundException, IOException, ParseException {
		return (JSONObject) (Object) parser.parse(Loader.getInputPutStreamReader(file));
	}

	public static boolean has(JSONObject data, String key) {
		// if (data == null) throw new NullPointerException("JSON Object does not exist");
		return data.containsKey(key);
	}

	public static Object get(JSONObject data, String key, Object defaultValue) {
		if (has(data, key)) return data.get(key);
		return defaultValue;
	}

	public static JSONObject getObject(JSONObject data, String key) {
		if (has(data, key)) return (JSONObject) data.get(key);
		return null;
	}

	public static String getString(JSONObject data, String key, String defaultValue) {
		return (String) get(data, key, defaultValue);
	}

	public static int getInteger(JSONObject data, String key, int defaultValue) {
		return Integer.parseInt(get(data, key, defaultValue).toString());
	}

	public static float getFloat(JSONObject data, String key, float defaultValue) {
		return Float.parseFloat(get(data, key, defaultValue).toString());
	}

	public static double getDouble(JSONObject data, String key, double defaultValue) {
		return Double.parseDouble(get(data, key, defaultValue).toString());
	}

	public static boolean getBoolean(JSONObject data, String key, boolean defaultValue) {
		return Boolean.parseBoolean(get(data, key, defaultValue).toString());
	}

	public static JSONArray getArray(JSONObject data, String key) {
		return (JSONArray) get(data, key, new JSONArray());
	}
}
