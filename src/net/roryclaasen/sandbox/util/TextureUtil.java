package net.roryclaasen.sandbox.util;

import java.util.ArrayList;
import java.util.List;

public class TextureUtil {

	public static int color_black;
	public static int color_blue;
	public static int color_cyan;
	public static int color_grayDark;
	public static int color_grayLight;
	public static int color_green;
	public static int color_lime;
	public static int color_magenta;
	public static int color_orange;
	public static int color_pink;
	public static int color_purple;
	public static int color_red;
	public static int color_white;
	public static int color_yellow;

	private static List<Object[]> textures = new ArrayList<Object[]>();

	private static Loader loader;

	public static void init(Loader loader) {
		TextureUtil.loader = loader;

		color_black = load("colors/black");
		color_blue = load("colors/blue");
		color_cyan = load("colors/cyan");
		color_grayDark = load("colors/gray_dark");
		color_grayLight = load("colors/gray_light");
		color_green = load("colors/green");
		color_lime = load("colors/lime");
		color_magenta = load("colors/magenta");
		color_orange = load("colors/orange");
		color_pink = load("colors/pink");
		color_purple = load("colors/purple");
		color_red = load("colors/red");
		color_white = load("colors/white");
		color_yellow = load("colors/yellow");
	}

	public static int load(String file) {
		if (exists(file)) return get(file);
		int id = loader.loadTexture(file);
		textures.add(new Object[]{file, id});
		return id;
	}

	private static int get(String file) {
		for (Object[] data : textures) {
			if (data[0].equals(file)) return (int) data[1];
		}
		return -1;
	}

	private static boolean exists(String file) {
		for (Object[] data : textures) {
			if (data[0].equals(file)) return true;
		}
		return false;
	}
}
