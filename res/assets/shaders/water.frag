#version 400 core

out vec4 out_Color;

in vec4 clipSpace;
in vec2 textureCords;
in vec3 toCameraVector;

uniform sampler2D refelectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvMap;

uniform float moveFactor;

const float waveStrength = 0.02;

void main(void) {
	vec2 nds = (clipSpace.xy / clipSpace.w) / 2.0 + 0.5;
	vec2 refractTexCoords = vec2(nds.x, nds.y);
	vec2 refelectTexCoords = vec2(nds.x, -nds.y);

	vec2 distortion1 = (texture(dudvMap, vec2(textureCords.x + moveFactor, textureCords.y)).rg * 2.0 - 1.0) * waveStrength;
	vec2 distortion2 = (texture(dudvMap, vec2(-textureCords.x + moveFactor, textureCords.y + moveFactor)).rg * 2.0 - 1.0) * waveStrength;
	vec2 totalDistortion = distortion1 + distortion2;

	refractTexCoords += totalDistortion;
	refractTexCoords = clamp(refractTexCoords, 0.001, 0.999);

	refelectTexCoords += totalDistortion;
	refelectTexCoords.x = clamp(refelectTexCoords.x, 0.001, 0.999);
	refelectTexCoords.y = clamp(refelectTexCoords.y, -0.999, -0.001);

	vec4 refelectColour = texture(refelectionTexture, refelectTexCoords);
	vec4 refractColour = texture(refractionTexture, refractTexCoords);

	vec3 viewVector = normalize(toCameraVector);
	float refelectiveFactor = dot(viewVector, vec3(0.0, 1.0, 0.0));
	refelectiveFactor = pow(refelectiveFactor, 0.75);

	out_Color = mix(refelectColour, refractColour, refelectiveFactor);
	out_Color = mix(out_Color, vec4(0.0, 0.3, 0.5, 1.0), 0.2);
}
