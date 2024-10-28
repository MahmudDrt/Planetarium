package com.example.news.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.opengl.GLU
import com.example.newsapp.R
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MyGLRenderer(private val context: Context) : GLSurfaceView.Renderer {
    private lateinit var background: Square
    private lateinit var sun: Sphere
    private lateinit var earth: Sphere
    private lateinit var moon: Sphere
    private lateinit var venus: Sphere
    private lateinit var mars: Sphere
    private lateinit var jupiter: Sphere
    private lateinit var saturn: Sphere
    private lateinit var mercury: Sphere
    private lateinit var uranus: Sphere
    private lateinit var neptune: Sphere

    // Кубы для планет
    private lateinit var earthCube: Cube
    private lateinit var moonCube: Cube
    private lateinit var venusCube: Cube
    private lateinit var marsCube: Cube
    private lateinit var jupiterCube: Cube
    private lateinit var saturnCube: Cube
    private lateinit var mercuryCube: Cube
    private lateinit var uranusCube: Cube
    private lateinit var neptuneCube: Cube

    private lateinit var planetSelector: PlanetSelector

    // Angles for rotation
    private var angleSun = 0.0f
    private var angleEarth = 0.0f
    private var angleMoon = 0.0f
    private var angleEarthOrbit = 0.0f
    private var angleVenusOrbit = 0.0f
    private var angleMarsOrbit = 0.0f
    private var angleJupiterOrbit = 0.0f
    private var angleSaturnOrbit = 0.0f
    private var angleMercuryOrbit = 0.0f
    private var angleUranusOrbit = 0.0f
    private var angleNeptuneOrbit = 0.0f

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        gl.glEnable(GL10.GL_DEPTH_TEST)
        gl.glEnable(GL10.GL_TEXTURE_2D)

        // Create background square
        background = Square()
        background.loadTexture(gl, context, R.drawable.galaxy)

        // Create spheres
        sun = Sphere(0.6f)
        earth = Sphere(0.35f)
        moon = Sphere(0.2f)
        venus = Sphere(0.35f)
        mars = Sphere(0.3f)
        jupiter = Sphere(0.6f)
        saturn = Sphere(0.7f)
        mercury = Sphere(0.25f)
        uranus = Sphere(0.4f)
        neptune = Sphere(0.4f)

        // Load textures for spheres
        sun.loadTexture(gl, context, R.drawable.sun)
        earth.loadTexture(gl, context, R.drawable.earth)
        moon.loadTexture(gl, context, R.drawable.moon)
        venus.loadTexture(gl, context, R.drawable.venus)
        mars.loadTexture(gl, context, R.drawable.mars)
        jupiter.loadTexture(gl, context, R.drawable.jupiter)
        saturn.loadTexture(gl, context, R.drawable.saturn)
        mercury.loadTexture(gl, context, R.drawable.mercury)
        uranus.loadTexture(gl, context, R.drawable.uranus)
        neptune.loadTexture(gl, context, R.drawable.neptune)

        // Создание кубов для каждой планеты (размеры скорректированы)
        earthCube = Cube(0.36f)  // Куб вокруг Земли
        moonCube = Cube(0.21f)  // Куб вокруг Луны
        venusCube = Cube(0.36f) // Куб вокруг Венеры
        marsCube = Cube(0.31f)  // Куб вокруг Марса
        jupiterCube = Cube(0.65f) // Куб вокруг Юпитера
        saturnCube = Cube(0.75f) // Куб вокруг Сатурна
        mercuryCube = Cube(0.26f) // Куб вокруг Меркурия
        uranusCube = Cube(0.41f)  // Куб вокруг Урана
        neptuneCube = Cube(0.41f) // Куб вокруг Нептуна

        planetSelector = PlanetSelector(
            mercuryCube, venusCube, earthCube, moonCube, // Порядок планет в Солнечной системе
            marsCube, jupiterCube, saturnCube, uranusCube, neptuneCube
        )
    }

    override fun onDrawFrame(gl: GL10) {
        // Очистка буферов цвета и глубины
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT or GL10.GL_DEPTH_BUFFER_BIT)

        gl.glLoadIdentity()

        // Отрисовка фона
        gl.glPushMatrix()
        gl.glTranslatef(0.0f, 0.0f, -40.0f)
        background.draw(gl)
        gl.glPopMatrix()

        gl.glTranslatef(0.0f, 0.0f, -15.0f)

        // Отрисовка Солнца
        gl.glPushMatrix()
        gl.glRotatef(angleSun, 0.0f, 1.0f, 0.0f)
        sun.draw(gl)
        gl.glPopMatrix()

        // Отрисовка планет и их кубов
        drawPlanet(gl, mercury, mercuryCube, angleMercuryOrbit, 4.0f)
        drawPlanet(gl, venus, venusCube, angleVenusOrbit, 5.0f)
        drawPlanet(gl, earth, earthCube, angleEarthOrbit, 6.0f)
        drawPlanet(gl, mars, marsCube, angleMarsOrbit, 7.5f)
        drawPlanet(gl, jupiter, jupiterCube, angleJupiterOrbit, 10.0f)
        drawPlanet(gl, saturn, saturnCube, angleSaturnOrbit, 12.0f)
        drawPlanet(gl, uranus, uranusCube, angleUranusOrbit, 14.0f)
        drawPlanet(gl, neptune, neptuneCube, angleNeptuneOrbit, 16.0f)

        // Отрисовка Луны и её куба (отдельно, т.к. вращается вокруг Земли)
        gl.glPushMatrix()
        gl.glRotatef(angleEarthOrbit, 0.0f, 1.0f, 0.0f)
        gl.glTranslatef(6.0f, 0.0f, 0.0f)
        gl.glRotatef(angleMoon, 0.0f, 1.0f, 0.0f)
        gl.glTranslatef(0.5f, 0.0f, 0.0f)
        moon.draw(gl)
        if (planetSelector.getSelectedPlanetCube() == moonCube) {
            moonCube.draw(gl, 1.0f, 1.0f, 1.0f, 0.5f)
        }
        gl.glPopMatrix()

        // Обновление углов
        angleSun += 0.1f
        angleEarth += 2.0f
        angleMoon += 7.0f
        angleEarthOrbit += 0.2f
        angleVenusOrbit += 0.3f
        angleMarsOrbit += 0.2f
        angleJupiterOrbit += 0.1f
        angleSaturnOrbit += 0.05f
        angleMercuryOrbit += 0.4f
        angleUranusOrbit += 0.05f
        angleNeptuneOrbit += 0.03f
    }


    private fun drawPlanet(gl: GL10, planet: Sphere, planetCube: Cube, angle: Float, distance: Float) {
        gl.glPushMatrix()
        gl.glRotatef(angle, 0.0f, 1.0f, 0.0f)
        gl.glTranslatef(distance, 0.0f, 0.0f)
        planet.draw(gl, 1.0f, 1.0f, 1.0f)
        if (planetSelector.getSelectedPlanetCube() == planetCube) {
            planetCube.draw(gl, 1.0f, 1.0f, 1.0f, 0.5f)
        }
        gl.glPopMatrix()
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        gl.glViewport(0, 0, width, height)
        gl.glMatrixMode(GL10.GL_PROJECTION)
        gl.glLoadIdentity()

        val aspectRatio = width.toFloat() / height
        GLU.gluPerspective(gl, 45.0f, aspectRatio, 1.0f, 100.0f)

        gl.glMatrixMode(GL10.GL_MODELVIEW)
        gl.glLoadIdentity()
    }

    // Добавьте эти функции в MyGLRenderer
    fun selectNextPlanet() {
        planetSelector.selectNextPlanet()
    }

    fun selectPreviousPlanet() {
        planetSelector.selectPreviousPlanet()
    }

    fun showPlanetInfo(): String {
        return planetSelector.showPlanetInfo()
    }
}