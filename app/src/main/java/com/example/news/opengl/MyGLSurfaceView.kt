package com.example.news.opengl

import android.content.Context
import android.opengl.GLSurfaceView

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {
    private val renderer: MyGLRenderer

    init {
        // Create an OpenGL ES 1.0 context
        setEGLContextClientVersion(1)

        renderer = MyGLRenderer(context)

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(renderer)
        renderMode = RENDERMODE_CONTINUOUSLY
    }
}