package com.victor.opengl;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.victor.opengl.library.renderer.TriangleRenderer;

public class TriangleActivity extends BaseActivity {

    @Override
    protected GLSurfaceView.Renderer bindRenderer() {
        return new TriangleRenderer(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
