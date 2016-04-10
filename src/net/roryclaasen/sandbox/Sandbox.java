package net.roryclaasen.sandbox;

import net.roryclaasen.sandbox.util.Arguments;

public class Sandbox {

	private static Sandbox sandbox;
	private static Arguments arguments;
	private static Options options;

	public Sandbox(Arguments arguments, Options options) {
		Sandbox.sandbox = this;
		Sandbox.arguments = arguments;
		Sandbox.options = options;
	}

	public void start() {

	}

	public static Sandbox getSandboxGame() {
		return sandbox;
	}

	public static Arguments getArguments() {
		return arguments;
	}

	public static Options getOptions() {
		return options;
	}
}
