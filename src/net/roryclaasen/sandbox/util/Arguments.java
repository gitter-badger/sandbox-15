package net.roryclaasen.sandbox.util;

public class Arguments {

	private String[] args;
	
	private boolean dev;

	public Arguments(String[] args) {
		this.args = args;
		check();
	}

	private void check() {
		for (String arg : args) {
			check(arg);
		}
	}

	private void check(String string) {

	}

	public String[] getArgs() {
		return args;
	}
}
