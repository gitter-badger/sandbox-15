package net.roryclaasen.sandbox.RenderEngine.skybox;

import net.roryclaasen.sandbox.RenderEngine.DisplayManager;
import net.roryclaasen.sandbox.entities.EntityManager;
import net.roryclaasen.sandbox.entities.Player;
import net.roryclaasen.sandbox.entities.light.Light;

import org.lwjgl.util.vector.Vector3f;

public class Skybox {
	private static float length = 4f;

	private static float time = 0, lastDay = 15000 * length, lastNight = 10000 * length, transition = 8500;
	private static float blendFactor = 0.0f, blendDay = 0.0f, blendNight = 1.0f;
	private float speed = 0.0025f;

	private static Vector3f fogColourDay = new Vector3f(0.5444F, 0.62F, 0.69F);
	private static Vector3f fogColourNight = new Vector3f(0.01F, 0.01F, 0.01F);
	private static Vector3f fogColour = new Vector3f(fogColourDay.getX(), fogColourDay.getY(), fogColourDay.getZ());

	public static float getBlendFactor() {
		return blendFactor;
	}

	public static void setDay() {
		time = 0;
		blendFactor = 0.0f;
		fogColour.set(fogColourDay.x, fogColourDay.y, fogColourDay.z);
	}

	public static void setNight(boolean doTime) {
		if (doTime) time = lastDay;
		blendFactor = 1.0f;
		fogColour.set(fogColourNight.x, fogColourNight.y, fogColourNight.z);
	}

	public boolean isDay() {
		return time >= 0 && time <= lastDay;
	}

	public boolean isNight() {
		return time >= lastDay && time <= lastDay + lastNight;
	}

	public static Vector3f getFogColour() {
		return fogColour;
	}

	public void update(EntityManager entityManager) {
		time += DisplayManager.getFrameTimeSeconds() * 1000;
		if (entityManager.getSun() != null) {
			Light sun = entityManager.getSun();
			Player player = entityManager.getPlayer();
			if (isDay()) {
				sun.setPosition(new Vector3f(player.getX(), player.getY() + 100 * 2, player.getZ()));
				sun.setColor(new Vector3f(1, 1, 1));
			}
			if (isNight()) {
				sun.setPosition(new Vector3f(player.getX(), player.getY() + 250 * 2, player.getZ()));
				sun.setColor(new Vector3f(0, 0, 0));
			}
		}
		if (time >= lastDay && time <= lastDay + transition) {
			if (blendFactor < blendNight) blendFactor += speed;

			if (fogColour.x > fogColourNight.x) fogColour.x -= speed;
			else fogColour.x = fogColourNight.x;
			if (fogColour.y > fogColourNight.y) fogColour.y -= speed;
			else fogColour.y = fogColourNight.y;
			if (fogColour.z > fogColourNight.z) fogColour.z -= speed;
			else fogColour.z = fogColourNight.z;
		}
		if (time >= lastDay + transition && time <= lastDay + transition + 10) setNight(false);
		if (time >= lastDay + lastNight && time <= lastDay + lastNight + transition) {
			if (blendFactor > blendDay) blendFactor -= speed;

			if (fogColour.x < fogColourDay.x) fogColour.x += speed;
			if (fogColour.y < fogColourDay.y) fogColour.y += speed;
			if (fogColour.z < fogColourDay.z) fogColour.z += speed;
		}
		if (time >= lastDay + lastNight + transition) setDay();
	}

	public void start() {
		setDay();
	}
}
