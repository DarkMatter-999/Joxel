#version 460 core

in vec3 passColor;
in vec2 passTextureCoords;

out vec4 outColor;

uniform sampler2D textureSampler;

void main() {
	outColor = texture(textureSampler, passTextureCoords);
}
