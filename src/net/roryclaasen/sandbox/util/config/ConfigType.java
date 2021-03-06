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
package net.roryclaasen.sandbox.util.config;

public class ConfigType<Type> {

	private String name;
	private Type type;

	protected ConfigType(String name, Type defaultValue) {
		this.name = name;
		this.type = defaultValue;
		Config.list.add(this);
	}

	public void set(Type value) {
		ConfigLoader.set(this.name, value);
	}

	@SuppressWarnings("unchecked")
	public Type get() {
		return (Type) ConfigLoader.get(this.name, this.type);
	}

	public Type getDefaultValue() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void reset() {
		ConfigLoader.set(this.name, type);
	}
}
