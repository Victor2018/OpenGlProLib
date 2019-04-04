#version 300 es
//三角形顶点着色器
layout (location = 0) in vec4 v_position;
layout (location = 1) in vec4 a_color;
out vec4 v_color;
void main() {
     gl_Position = v_position;
     gl_PointSize = 10.0;
     v_color = a_color;
}