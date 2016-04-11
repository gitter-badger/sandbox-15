/** 
   Copyright 2016 Rory Claasen

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
package net.roryclaasen.sandbox.level;

import java.util.ArrayList;
import java.util.List;

public class ChunkObjectData {
	private List<ObjectData> objects;

	public ChunkObjectData() {
		objects = new ArrayList<ObjectData>();
	}

	public void add(ObjectData data) {
		objects.add(data);
	}

	public List<ObjectData> getObjects() {
		return objects;
	}
}
