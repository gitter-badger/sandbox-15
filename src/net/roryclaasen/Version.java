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
package net.roryclaasen;

import net.gogo98901.log.Log;
import net.roryclaasen.githubcheck.VersionCheck;

public class Version {

	private static VersionCheck check;

	private static boolean hasChecked = false;
	private static boolean isLatest, isLatestPreRelease;

	public Version() {
		check = new VersionCheck("GOGO98901", "sandbox", Bootstrap.VERSION);
		Log.info("[Version] Version check set up with version [" + check.getCurrentVersion() + "]");
	}

	public void startCheck() {
		Log.info("[Version] Starting version check");
		new Thread(new Runnable() {

			@Override
			public void run() {
				isLatest = Version.check.isLatestRelease(true, false);
				isLatestPreRelease = Version.check.isLatestRelease(true, true);
				hasChecked = true;

				if (isLatest) {
					if (!isLatestPreRelease) Log.info("[Version] Running latest version, but there is a new pre release");
					else Log.info("[Version] Running latest version");
				} else {
					Log.info("[Version] Not Running latest version");
				}
			}
		}, Bootstrap.TITLE + " Version Check").start();
	}

	public static boolean isLatest() {
		if (hasChecked) return isLatest;
		Log.warn("[Version] Version check has not been carried out");
		return false;
	}

	public static boolean isLatestPreRelease() {
		if (hasChecked) return isLatestPreRelease;
		Log.warn("[Version] Version check has not been carried out");
		return false;
	}
}
