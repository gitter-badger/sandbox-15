/* Copyright 2016 Rory Claasen

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package net.roryclaasen.sandbox.util;

import net.gogo98901.log.Log;

public class Arguments {

	private String[] args;

	private boolean test;

	private boolean debug;
	private boolean log;
	private boolean doConfig;

	public Arguments(String[] args) {
		this.args = args;
		test = System.getenv("usingCI") != null;
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
			string = string.replaceFirst("-", "").toLowerCase();
			if (string.equals("debug")) debug = true;
			if (string.equals("log")) log = true;
			if (string.equals("noconfig")) doConfig = false;
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

	public boolean doLoadConfig() {
		return doConfig;
	}

	public boolean isRunningAsCI() {
		return test;
	}
}
