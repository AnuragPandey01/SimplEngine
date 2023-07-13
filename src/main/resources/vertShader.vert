#version 400

in vec3 position;

out vec3 colour;

void main(){
    gl_Position = vec4(position,1.0);
    colour = vec3(position.x+0.5,position.y+0.5,position.z+0.5);
}