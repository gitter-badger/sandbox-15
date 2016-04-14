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
 #version 400

in vec3 textureCoords;
out vec4 out_Color;

uniform samplerCube cubeMap1;
uniform samplerCube cubeMap2;
uniform float blendFactor;

uniform vec3 fogColour;

const float lowerLimit = 0.0;
const float upperLimit = 30.0;

void main(void) {
   vec4 texture1 = texture(cubeMap1, textureCoords);
   vec4 texture2 = texture(cubeMap2, textureCoords);
   vec4 finalColor = mix(texture1, texture2, blendFactor);

   float factor = (textureCoords.y - lowerLimit) / (upperLimit - lowerLimit);
   factor = clamp(factor, 0.0, 1.0);

   out_Color = mix(vec4(fogColour, 1.0), finalColor, factor);
}
