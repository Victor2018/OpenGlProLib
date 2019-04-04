#version 300 es
//三角形片段着色器
precision mediump float;
in vec4 v_color;//input vertex color from vertex shader
out vec4 fragColor;//out framgent color
void main() {
     fragColor = v_color;
}