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

in vec3 position;
in vec2 textureCoords;
in vec3 normal;

out vec2 pass_textureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector[4];
out vec3 toCameraVector;
out float visiblity;
out vec4 shadowCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[4];
uniform float shadowDistance;

uniform vec4 plane;

uniform mat4 toShadowMapSapce;

// Fog control thing
// const float density = 0.0035;
const float density = 0.0;
const float gradient = 5.0;
const float transitionDistance = 10.0;

void main(void) {
	vec4 worldPosition = transformationMatrix * vec4(position, 1.0);
	vec4 positionRelativeToCam = viewMatrix * worldPosition;

	shadowCoords = toShadowMapSapce * worldPosition;

	gl_ClipDistance[0] = dot(worldPosition, plane);

	gl_Position = projectionMatrix * positionRelativeToCam;
	pass_textureCoords = textureCoords;

	surfaceNormal = (transformationMatrix * vec4(normal, 0.0)).xyz;
	for(int i = 0; i < 4; i++) {
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}
	toCameraVector = (inverse(viewMatrix) * vec4(0.0, 0.0, 0.0, 1.0)).xyz - worldPosition.xyz;

	float distance = length(positionRelativeToCam.xyz);
	visiblity = exp(-pow((distance * density), gradient));
	visiblity = clamp(visiblity, 0.0, 1.0);

	distance = distance - (shadowDistance - transitionDistance);
	distance = distance / transitionDistance;
	shadowCoords.w = clamp(1.0 - distance, 0.0, 1.0);
}
