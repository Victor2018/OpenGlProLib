

参考资料
- 《OpenGL ES 3.0 编程指南第2版
- https://github.com/danginsburg/opengles3-book/blob/master/Android_Java/Chapter_2
- https://www.jianshu.com/p/438de5a40855
- https://www.jianshu.com/p/4ddf560e2e3c
- https://www.jianshu.com/p/6eaa2504a81c

## OpenGL ES3.0 概述

    OpenGL ES 3.0实现了具有可编程着色功能的图形管线，由两个规范组成：OpenGL ES 3.0 API 规范和OpenGL ES着色语言3.0规范(OpenGL ES SL)。
    下图展示了OpenGl ES 3.0 API图形管线图，图中带有阴影方框表示OpenGl ES 3.0 中管线的可编程阶段，下面概述OpenGl ES 3.0图形管线的各个阶段：

- ![image](https://github.com/Victor2018/OpenGlProLib/raw/master/docs/opengl3_graphics_pipeline.png)

## OpenGL ES对Android平台的支持情况

`OpenGL ES当前主要版本有1.0/1.1/2.0/3.0/3.1`。

|OpenGL ES版本|Android版本|描述|
|:-|:-|:-|
|OpenGL ES1.0|Ａndroid 1.0+|OpenGL ES 1.x是针对固定硬件管线的，Ａndroid 1.0和更高的版本支持这个API规范。|
|**OpenGL ES2.0**|**Android 2.2(API 8)+**|OpenGL ES 2.x是针对可编程硬件管线的，不兼容OpenGL ES 1.x，Android 2.2(API 8)和更高的版本支持这个API规范。|
|**OpenGL ES3.0**|**Android 4.3(API 18)+**|向下兼容OpenGL ES 2.x，Android 4.3(API 18)及更高的版本支持这个API规范。|
|OpenGL ES3.1|Android 5.0 (API 21)+|向下兼容OpenGL ES3.0/2.0，Android 5.0（API 21）和更高的版本支持这个API规范|

    注意：在设备上支持OpenGL ES 3.0 API需要设备的制造商提供此图形管道的实现。 运行Android 4.3 或更高版本的设备可能不支持OpenGL ES 3.0 API。

## 坐标系

    - android手机坐标系
    二维坐标系，原点在左上角，x 轴向右，y 轴向下，x y 取值范围为屏幕分辨率

    - OpenGL坐标系
    三维坐标系，原点在中间，x 轴向右，y 轴向上，z 轴朝向我们，x y z 取值范围都是 [-1, 1]：

## 顶点着色器
顶点着色器实现了顶点操作的通用可编程方法，`着色器（Shader）是在GPU上运行的小程序`。
顶点着色器的输入包括：
- 着色器程序一一描述顶点上执行操作的顶点着色器程序源代码或者可执行文件。
- 顶点着色器输人(或者属性)一一用顶点数组提供的每个顶点的数据。
- 统一变量(`uniform`)一一顶点(或者片段)着色器使用的不变数据。
- 采样器一一代表顶点着色器使用纹理的特殊统一变量类型。
顶点着色器的输入输出如下图：
- ![image](https://github.com/Victor2018/OpenGlProLib/raw/master/docs/opengl3_vertex_shader.png)

顶点着色器取得一个位置及相关的颜色数据作为输入属性，用一个 4x4矩阵变换位置，并输出变换后的位置和颜色。

- 顶点着色器实例如下：

#version 300 es
uniform mat4 u_mvpMatrix;//matrix to convert a_position from model space to normalized device space

//attributes input to the vertex shader
int vec4 a_postion;//position value
int vec4 a_color;//input vertex color

//output of vertex shader--->input to fragment shader
out vec4 v_color;//output vertex color
void main(){
	v_color = a_color;
	gl_Position = u_mvpMatrix * a_postion
}

## 图元装配
顶点着色器之后，OpenGL ES 3.0图形管线的下一个阶段是图元装配。图元（Primitive）是三角形、直线或者点精灵等集合对象。
图元的每个顶点被发送到顶点着色器的不同拷贝。在图元装配期间，这些顶点被组合成图元。

## 光栅化
如下图，在此阶段绘制对应的图元（点精灵、直线或者三角形）。光栅化是将图元转化为一组二维片段的过程，
然后，这些片段由片段着色器处理。这些二维片段代表着可在屏幕上绘制的像素。

- ![image](https://github.com/Victor2018/OpenGlProLib/raw/master/docs/openg3_rasterization_stage.png)

## 片段着色器
片段着色器片段着色器为片段上的操作实现了通用的可编程方法。 如小图，对光栅化阶段生成的每个片段执行这个着色器，采用如下输入：

着色器程序——描述片段上所执行操作的片段着色器程序源代码或者可执行文件。
输人变量——光姗化单元用插值为每个片段生成的顶点着色器钧出。
统一变量——片段(或者顶点)着色器使用的不变数据。
采样器——讨七表片段着色器所用纹理的特殊统一变量类型。

- ![image](https://github.com/Victor2018/OpenGlProLib/raw/master/docs/opengl3_fragment_shader.png)

- 片段着色器实例如下：

#version 300 es
precision mediump float;
in vec4 v_color;// input vertex color from vertex shader
out vec4 fragColor;//output fragment color
void main(){
    fragColor = v_color;
}

## 逐片段操作
- ![image](https://github.com/Victor2018/OpenGlProLib/raw/master/docs/fragmented_operation.png)

## 声明OpenGL ES

    如果您的应用程序使用了OpenGL ES，则必须在清单文件中作出声明。 以下是最常见的OpenGL 清单声明：
    <!-- Tell the system this app requires OpenGL ES 3.0. -->
    <uses-feature android:glEsVersion="0x00030000" android:required="true" />



# 关注开发者：
- 邮箱： victor423099@gmail.com
- 新浪微博
- ![image](https://github.com/Victor2018/AppUpdateLib/raw/master/SrceenShot/sina_weibo.jpg)

## License

Copyright (c) 2017 Victor

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
