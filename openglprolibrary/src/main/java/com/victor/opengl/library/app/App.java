package com.victor.opengl.library.app;

import android.app.Application;

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: AspectFrameLayout.java
 * Author: Victor
 * Date: 2019/3/14 9:16
 * Description:
 * -----------------------------------------------------------------
 */

public class App extends Application {
    private static App instance;

    public App() {
        instance = this;
    }

    public static App get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


}
