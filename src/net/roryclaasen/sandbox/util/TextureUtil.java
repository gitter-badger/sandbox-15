package net.roryclaasen.sandbox.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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

	public static int level_ground0;
	public static int level_groundr;
	public static int level_groundg;
	public static int level_groundb;

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

		level_ground0 = loadTextureFromColour(new Color(0, 255, 0));
		level_groundr = loadTextureFromColour(new Color(189, 189, 189));
		level_groundg = loadTextureFromColour(new Color(221, 147, 68));
		level_groundb = loadTextureFromColour(new Color(139, 69, 19));
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

	private static final int BYTES_PER_PIXEL = 4;

	private static final int DEFULT_WIDTH = 2, DEFULT_HEIGHT = 2;

	public static int loadTextureFromColour(Color color) {
		return loadTextureFromColour(color, DEFULT_WIDTH, DEFULT_HEIGHT);
	}

	public static int loadTextureFromColour(Color color, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(color);
		g2d.fillRect(0, 0, width, height);
		return loadTexture(image);
	}

	public static int loadTexture(BufferedImage image) {
		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); // 4 for RGBA, 3 for RGB

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));
				buffer.put((byte) ((pixel >> 8) & 0xFF));
				buffer.put((byte) (pixel & 0xFF));
				buffer.put((byte) ((pixel >> 24) & 0xFF));
			}
		}

		buffer.flip();

		int textureID = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

		return textureID;
	}
}
