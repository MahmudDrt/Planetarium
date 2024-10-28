package com.example.news.opengl

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer
import javax.microedition.khronos.opengles.GL10

class Cube(val size: Float) {
    private val vertexBuffer: FloatBuffer
    private val indexBuffer: ShortBuffer

    private val vertices = floatArrayOf(
        -size, -size, -size, // 0. left-bottom-back
        size, -size, -size, // 1. right-bottom-back
        size, size, -size, // 2. right-top-back
        -size, size, -size, // 3. left-top-back
        -size, -size, size, // 4. left-bottom-front
        size, -size, size, // 5. right-bottom-front
        size, size, size, // 6. right-top-front
        -size, size, size  // 7. left-top-front
    )

    private val indices = shortArrayOf(
        0, 1, 2, 0, 2, 3, // back
        1, 5, 6, 1, 6, 2, // right
        5, 4, 7, 5, 7, 6, // front
        4, 0, 3, 4, 3, 7, // left
        3, 2, 6, 3, 6, 7, // top
        4, 5, 1, 4, 1, 0  // bottom
    )

    init {
        val vbb = ByteBuffer.allocateDirect(vertices.size * 4)
        vbb.order(ByteOrder.nativeOrder())
        vertexBuffer = vbb.asFloatBuffer()
        vertexBuffer.put(vertices)
        vertexBuffer.position(0)

        val ibb = ByteBuffer.allocateDirect(indices.size * 2)
        ibb.order(ByteOrder.nativeOrder())
        indexBuffer = ibb.asShortBuffer()
        indexBuffer.put(indices)
        indexBuffer.position(0)
    }

    fun draw(gl: GL10, r: Float = 1.0f, g: Float = 1.0f, b: Float = 1.0f, alpha: Float = 1.0f) {
        // Включение прозрачности
        gl.glEnable(GL10.GL_BLEND)
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA)

        // Установка цвета и прозрачности
        gl.glColor4f(r, g, b, alpha)

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY)
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer)

        gl.glDrawElements(GL10.GL_TRIANGLES, indices.size, GL10.GL_UNSIGNED_SHORT, indexBuffer)

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY)

        gl.glDisable(GL10.GL_BLEND) // Отключение прозрачности
    }
}