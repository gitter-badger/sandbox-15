package net.roryclaasen.sandbox.util.config;

public enum Config {
	// Display
	width("width", 1280), height("height", 720), fpsCap("fps-cap", 120),

	// Camera
	fov("camera-fov", 70), sensitivity("mouse-sensitivity", 0.5f),

	// World
	skyRotate("sky-rotate-speed", 0.5F),
	// Render
	anisotropic("anisotropic-filtering", true), antialiasing("antialiasing", false), antialiasingSample("antialiasing-sample", 4)
	// END OF CONFIG
	;

	private String name;
	private Object defaultValue;

	Config(String name, Object defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}

	public void set(Object newValue) {
		ConfigLoader.set(this.name, newValue);
	}

	public Object get() {
		return ConfigLoader.get(this.name, this.defaultValue);
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public String getString() {
		return "" + get();
	}

	public int getIntager() {
		return Integer.parseInt(getString());
	}

	public boolean getBoolean() {
		return Boolean.parseBoolean(getString());
	}

	public float getFloat() {
		return Float.parseFloat(getString());
	}

}
