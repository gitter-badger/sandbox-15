package net.roryclaasen;

import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.Options;
import net.roryclaasen.sandbox.Sandbox;
import net.roryclaasen.sandbox.util.Arguments;

public class Bootstrap {
	
	public static final String TITLE = "Sandbox", VERSION = "0.1";

	private static Arguments arguments;
	private static Options options;

	public static void main(String[] args) {
		Log.info(TITLE);
		arguments = new Arguments(args);
		if (init(arguments)) {
			start(arguments, options);
		}
	}

	private static boolean init(Arguments arguments) {
		options = new Options();
		return true;
	}

	private static void start(Arguments arguments, Options options) {
		Sandbox sandbox = new Sandbox(arguments, options);
		sandbox.start();
	}

}
