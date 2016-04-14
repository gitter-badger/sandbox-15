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

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visiblity;

out vec4 out_Colour;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColour[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float refelectivity;
uniform vec3 skyColour;

void main(void) {
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitToCameraVector = normalize(toCameraVector);

	vec4 blendMapColour = texture(blendMap, pass_textureCoords);

	vec2 tiledCoords = pass_textureCoords * 40.0;
	float backTextureAmount = 1 - (blendMapColour.r + blendMapColour.g + blendMapColour.b);

	vec4 backgroundColour = texture(backgroundTexture, tiledCoords) * backTextureAmount;
	vec4 rTextureColour = texture(rTexture, tiledCoords) * blendMapColour.r;
	vec4 gTextureColour = texture(gTexture, tiledCoords) * blendMapColour.g;
	vec4 bTextureColour = texture(bTexture, tiledCoords) * blendMapColour.b;

	vec4 totalColour = backgroundColour + rTextureColour + gTextureColour + bTextureColour;

	vec3 totalDefuse = vec3(0.0);
	vec3 totalSpecular = vec3(0.0);

	for(int i = 0; i < 4; i++) {
		float distance = length(toLightVector[i]);
		float attFactor = attenuation[i].x + (attenuation[i].y * distance) +  (attenuation[i].z * distance * distance);

		vec3 unitLightVector = normalize(toLightVector[i]);

		float nDotl = dot(unitNormal, unitLightVector);
		float brightness = max(nDotl, 0.0);

		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

		float specularFactor = dot(reflectedLightDirection, unitToCameraVector);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		totalDefuse = totalDefuse + (brightness * lightColour[i]) / attFactor;
		totalSpecular = totalSpecular + (dampedFactor * refelectivity * lightColour[i]) / attFactor;
	}

	totalDefuse = max(totalDefuse, 0.2);

	out_Colour = vec4(totalDefuse, 1.0) * totalColour + vec4(totalSpecular, 1.0);
	out_Colour = mix(vec4(skyColour, 1.0), out_Colour, visiblity);
}
