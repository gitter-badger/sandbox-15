package net.roryclaasen.sandbox.crash;

import java.io.PrintWriter;
import java.io.StringWriter;

import net.gogo98901.log.Log;

public class CrashMessage {
	private String message;

	public CrashMessage(String message) {
		this.message = message;
	}

	public CrashMessage(Exception exception) {
		StringWriter errors = new StringWriter();
		exception.printStackTrace(new PrintWriter(errors));
		this.message = errors.toString();
	}

	public String get() {
		return message;
	}

	public String getAll() {
		String format = message;
		format += "\n\nFull Log:\n"; // TODO Get log;
		format += "\n\n" + Thread.getAllStackTraces();
		return format;
	}
}
