package com.example.news.opengl

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.opengles.GL10

class Square {
    private val vertexBuffer: FloatBuffer
    private val textureBuffer: FloatBuffer
    private var textureId = 0
    private val vertices = floatArrayOf(
        -57.6f, 32.4f, 0.0f,  // Верхний левый угол
        57.6f, 32.4f, 0.0f,  // Верхний правый угол
        57.6f, -32.4f, 0.0f,  // Нижний правый угол
        -57.6f, -32.4f, 0.0f // Нижний левый угол
    )


    private val textureCoords = floatArrayOf(
        0.0f, 0.0f,  // Top left
        1.0f, 0.0f,  // Top right
        1.0f, 1.0f,  // Bottom right
        0.0f, 1.0f // Bottom left
    )

    init {
        val bb = ByteBuffer.allocateDirect(vertices.size * 4)
        bb.order(ByteOrder.nativeOrder())
        vertexBuffer = bb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)

        val tb = ByteBuffer.allocateDirect(textureCoords.size * 4)
        tb.order(ByteOrder.nativeOrder())
        textureBuffer = tb.asFloatBuffer()
        textureBuffer.put(textureCoords)
        textureBuffer.position(0)
    }

    fun loadTexture(gl: GL10, context: Context, resourceId: Int) {
        val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)
        val textures = IntArray(1)

        gl.glGenTextures(1, textures, 0)
        textureId = textures[0]

        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId)
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0)
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR.toFloat())
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR.toFloat())

        bitmap.recycle()
    }

    fun draw(gl: GL10) {
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY)

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer)

        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 4)

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY)
    }
}