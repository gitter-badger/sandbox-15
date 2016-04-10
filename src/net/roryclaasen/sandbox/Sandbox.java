package net.roryclaasen.sandbox;

import net.gogo98901.log.Log;
import net.roryclaasen.Bootstrap;
import net.roryclaasen.sandbox.util.Arguments;
import net.roryclaasen.sandbox.util.Options;

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
		Log.info("Starting " + Bootstrap.TITLE);
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
