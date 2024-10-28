package com.example.news

import android.opengl.GLSurfaceView
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.news.opengl.MyGLRenderer
import com.example.news.ui.theme.NewsTheme

class MainActivity : ComponentActivity() {
    private val viewModel: News by viewModels()
    private lateinit var gLSurfaceView: GLSurfaceView
    private lateinit var myGLRenderer: MyGLRenderer // Добавлено

    private var isGLSurfaceViewVisible by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsTheme {
                Box(modifier = Modifier.fillMaxSize()) {
                    MainWindow(viewModel)

                    if (isGLSurfaceViewVisible) {
                        AndroidView(
                            modifier = Modifier.fillMaxSize(),
                            factory = { context ->
                                gLSurfaceView = GLSurfaceView(context).apply {
                                    setEGLContextClientVersion(1)
                                    setRenderer(MyGLRenderer(context).also {
                                        myGLRenderer = it // Сохраняем renderer
                                    })
                                    renderMode = GLSurfaceView.RENDERMODE_CONTINUOUSLY
                                }
                                gLSurfaceView
                            }
                        )

                        // Кнопки внизу сцены с солнечной системой
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { myGLRenderer.selectPreviousPlanet() }, // Используем myGLRenderer
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text("Влево")
                            }

                            Button(
                                onClick = {
                                    Toast.makeText(
                                        this@MainActivity,
                                        myGLRenderer.showPlanetInfo(), // Используем myGLRenderer
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            ) {
                                Text("Информация")
                            }

                            Button(
                                onClick = { myGLRenderer.selectNextPlanet() }, // Используем myGLRenderer
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text("Вправо")
                            }
                        }

                    } else {
                        // Кнопка "Показать галактику"
                        Button(
                            onClick = { isGLSurfaceViewVisible = true },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        ) {
                            Text("Показать галактику")
                        }
                    }
                }
            }
        }
    }
}