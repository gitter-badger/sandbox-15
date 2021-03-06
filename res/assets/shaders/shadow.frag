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
#version 330

in vec2 textureCoords;

out vec4 out_colour;

uniform sampler2D modelTexture;

void main(void)  {
	float alpha = texture(modelTexture, textureCoords).a;
	if(alpha < 0.5) {
		discard;
	}
	out_colour = vec4(1.0);
}
