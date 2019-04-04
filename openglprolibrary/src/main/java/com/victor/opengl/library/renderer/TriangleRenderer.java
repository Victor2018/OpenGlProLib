package com.victor.opengl.library.renderer;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.victor.opengl.library.R;
import com.victor.opengl.library.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: TriangleRenderer.java
 * Author: Victor
 * Date: 2019/4/4 10:01
 * Description: 三角形渲染器
 * 参考https://github.com/danginsburg/opengles3-book/blob/master/Android_Java/Chapter_2/
 * Hello_Triangle/src/com/openglesbook/hellotriangle/HelloTriangleRenderer.java
 * -----------------------------------------------------------------
 */
public class TriangleRenderer implements GLSurfaceView.Renderer {
    private String TAG = "TriangleRenderer";
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private FloatBuffer mVertices;
    private FloatBuffer colorBuffer;
    private int vertexCount = 3;

    private int mProgramObj;

    /**
     * 定义三角形顶点坐标
     */
    private final float[] mVerticesData = new float[]{
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    private float color[] = {
            //R    G     B     A
            1.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 1.0f, 0.5f,
            1.0f, 0.0f, 1.0f, 1.0f
    };

    /**
     * 顶点着色器
     */
    private String vertexShaderCode =
            "#version 300 es\n" +
                    "layout (location = 0) in vec4 v_position;\n" +
                    "layout (location = 1) in vec4 a_color;\n" +
                    "out vec4 v_color;\n" +
                    "void main()\n" +
                    "{\n" +
                    "   gl_Position = v_position;\n" +
                    "   gl_PointSize = 10.0;\n" +
                    "   v_color = a_color;\n" +
                    "}\n";

    /**
     * 片段着色器
     */
    private String fragShaderCode =
            "#version 300 es\n" +
                    "precision mediump float;\n" +
                    "in vec4 v_color;\n" +//input vertex color from vertex shader
                    "out vec4 fragColor;\n" +//out framgent color
                    "void main()\n" +
                    "{\n" +
                    "   fragColor = v_color;\n" +
                    "}\n";

    public TriangleRenderer (Context context) {
        mContext = context;
        //分配内存空间,每个浮点型占4字节空间
        mVertices = ByteBuffer.allocateDirect ( mVerticesData.length * 4 )
                .order ( ByteOrder.nativeOrder() ).asFloatBuffer();
        //传入指定的坐标数据
        mVertices.put(mVerticesData).position ( 0 );

        colorBuffer = ByteBuffer.allocateDirect ( color.length * 4 )
                .order ( ByteOrder.nativeOrder() ).asFloatBuffer();
        colorBuffer.put(color).position(0);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        // Load the vertex/fragment shaders from assets
        final int vertexShaderId = ShaderUtil.compileVertexShader(mContext,"vertex_triangle_shader.glsl");
        final int fragmentShaderId = ShaderUtil.compileFragmentShader(mContext,"fragment_triangle_shader.glsl");

        // Load the vertex/fragment shaders from raw
//        final int vertexShaderId = ShaderUtil.compileVertexShader(mContext, R.raw.vertex_triangle_shader);
//        final int fragmentShaderId = ShaderUtil.compileFragmentShader(mContext,R.raw.fragment_triangle_shader);

        // Create the program object
        mProgramObj = ShaderUtil.linkProgram(vertexShaderId, fragmentShaderId);

        GLES30.glClearColor ( 0.6f, 0.6f, 0.8f, 0.5f );
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Set the viewport
        GLES30.glViewport ( 0, 0, mWidth, mHeight );

        // Clear the color buffer
        GLES30.glClear ( GLES30.GL_COLOR_BUFFER_BIT );
        // Use the program object
        GLES30.glUseProgram (mProgramObj);

        // Load the vertex data
        GLES30.glVertexAttribPointer ( 0, vertexCount, GLES30.GL_FLOAT, false, 0, mVertices );
        GLES30.glEnableVertexAttribArray ( 0 );

        // 设置绘制三角形的颜色
        GLES30.glVertexAttribPointer ( 1, 4, GLES30.GL_FLOAT, false, 0, colorBuffer );
        GLES30.glEnableVertexAttribArray ( 1 );

        GLES30.glDrawArrays (GLES30.GL_TRIANGLES, 0, 3 );

        //禁止顶点数组的句柄
        GLES30.glDisableVertexAttribArray(0);
        GLES30.glDisableVertexAttribArray(1);
    }


}
