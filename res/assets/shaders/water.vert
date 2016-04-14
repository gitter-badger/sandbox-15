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
 #version 400 core

in vec2 position;

out vec4 clipSpace;
out vec2 textureCords;
out vec3 toCameraVector;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;
uniform vec3 cameraPosition;

const float tiling = 6.0;

void main(void) {
	vec4 worldPosition = modelMatrix * vec4(position.x, 0.0, position.y, 1.0);

	clipSpace = projectionMatrix * viewMatrix * worldPosition;
	gl_Position = clipSpace;

	textureCords = vec2(position.x / 2.0 + 0.5, position.y / 2.0 + 0.5) * tiling;

	toCameraVector = cameraPosition - worldPosition.xyz;
}
