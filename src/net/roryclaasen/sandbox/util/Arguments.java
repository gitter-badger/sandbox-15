package net.roryclaasen.sandbox.util;

import net.gogo98901.log.Log;

public class Arguments {

	private String[] args;

	private boolean debug;
	private boolean log;

	public Arguments(String[] args) {
		this.args = args;
		check();
	}

	private void check() {
		Log.info("Checking arguments");
		for (String arg : args) {
			check(arg);
		}
	}

	private void check(String string) {
		if (string.startsWith("-")) {
			string = string.replaceFirst("-", "");
			if (string.equals("debug")) debug = true;
			if (string.equals("log")) log = true;
		}
	}

	public String[] getArgs() {
		return args;
	}

	public boolean isDebugMode() {
		return debug;
	}

	public boolean doSaveLog() {
		return log;
	}
}
