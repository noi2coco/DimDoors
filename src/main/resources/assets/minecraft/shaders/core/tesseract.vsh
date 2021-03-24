#version 150

in vec4 Position;
in vec4 Color;
in vec2 UV0;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat4 Transform4d;

out vec4 vertexColor;
out vec2 texCoord0;

void main() {
    vec4 transformed = Transform4d * Position;

    transformed = vec4(transformed.xyz * ((1/transformed.w) + 1), 1.0);

    gl_Position = ProjMat * ModelViewMat * transformed;

    vertexColor = Color;
    texCoord0 = UV0;
}
