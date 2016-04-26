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

import java.io.IOException;

import net.gogo98901.log.Log;
import net.roryclaasen.sandbox.util.JSONUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class GameInfo {
	private static JSONObject info = loadGameInfo();

	public static final  String title = JSONUtil.getString(info, "title", "Sandbox");
	public static final String version = JSONUtil.getString(info, "version", "0.0.0");


	private static JSONObject loadGameInfo(){
		try {
			return JSONUtil.readFromJar("game.info");
		} catch ( IOException | ParseException e) {
			Log.stackTrace(e);
		}
		return new JSONObject();
	}

}
