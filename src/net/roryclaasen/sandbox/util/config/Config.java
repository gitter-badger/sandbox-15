package net.roryclaasen.sandbox.util.config;

public enum Config {
	width("width", 720), height("height", 600);

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
}
