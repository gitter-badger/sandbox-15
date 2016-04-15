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
package net.roryclaasen.sandbox.crash;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrashHandler implements UncaughtExceptionHandler {

	private static Map<Thread, List<Throwable>> throwables = new HashMap<Thread, List<Throwable>>();

	public static void add(Throwable e) {
		add(Thread.currentThread(), e);
	}

	public static void add(Thread t, Throwable e) {
		List<Throwable> excepton = throwables.get(t);
		if (excepton == null) {
			excepton = new ArrayList<Throwable>();
			throwables.put(t, excepton);
		}
		excepton.add(e);
	}

	public static void show(Throwable e) {
		add(e);
		show();
	}

	public static void show(Thread t, Throwable e) {
		add(t, e);
		show();
	}

	public static void show() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				CrashWindow window = new CrashWindow(throwables);
				window.show();
			}
		}).start();
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		add(t, e);
	}
}
