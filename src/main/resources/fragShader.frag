#version 400

in vec2 pass_textureCoords;

uniform sampler2D textureSampler;

void main(){
    gl_FragColor = texture(textureSampler, pass_textureCoords);
}