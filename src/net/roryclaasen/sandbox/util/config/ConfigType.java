package net.roryclaasen.sandbox.util.config;

public class ConfigType<T> {

	private String name;
	private T type;

	public ConfigType(String name, T defaultValue) {
		this.name = name;
		this.type = defaultValue;
		Config.list.add(this);
	}

	public void set(T value) {
		ConfigLoader.set(this.name, value);
	}

	@SuppressWarnings("unchecked")
	public T get() {
		return (T) ConfigLoader.get(this.name, this.type);
	}

	public T getDefaultValue() {
		return type;
	}

	public String getName() {
		return name;
	}
}
