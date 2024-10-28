package com.example.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay

@Composable
fun MainWindow(viewModel: News) {
    var firstRender by remember { mutableStateOf(true) }

    if (viewModel.showWindow) {
        if (firstRender) {
            viewModel.startNews()
            viewModel.changeNews()
            firstRender = false
        }

        // Запускаем корутину для периодического обновления новостей
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(Unit) {
            while (true) {
                delay(1000) // Задержка 1 секунда
                viewModel.changeNews()
            }
        }

        Box(modifier = Modifier.fillMaxSize().background(Color(0xFF90EE90))) {
            // Здесь можно разместить другие элементы интерфейса
            Column(modifier = Modifier.fillMaxSize())
            {
                Row{
                    Box(modifier = Modifier.padding(all = 5.dp).clickable { viewModel.click1() }){

                        Box(){
                            Column(modifier = Modifier
                                .width( 200.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(color = Color.LightGray)
                                .padding(all = 5.dp)){

                                Text(text = viewModel.newsString1, modifier = Modifier.height(240.dp))
                                Row(modifier = Modifier.height(30.dp)) {
                                    Text(text = "  ❤\uFE0F  ")
                                    Text(text = viewModel.likes1.toString())
                                }
                            }
                        }
                    }

                    Box(modifier = Modifier.padding(all = 5.dp).clickable { viewModel.click2() }){

                        Box(){
                            Column(modifier = Modifier
                                .width( 200.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(color = Color.LightGray)
                                .padding(all = 5.dp)){

                                Text(text = viewModel.newsString2, modifier = Modifier.height(240.dp))
                                Row(modifier = Modifier.height(30.dp)) {
                                    Text(text = "  ❤\uFE0F  ")
                                    Text(text = viewModel.likes2.toString())
                                }
                            }
                        }
                    }
                }
                Row{
                    Box(modifier = Modifier.padding(all = 5.dp).clickable { viewModel.click3() }){

                        Box(){
                            Column(modifier = Modifier
                                .width( 200.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(color = Color.LightGray)
                                .padding(all = 5.dp)){

                                Text(text = viewModel.newsString3, modifier = Modifier.height(240.dp))
                                Row(modifier = Modifier.height(30.dp)) {
                                    Text(text = "  ❤\uFE0F  ")
                                    Text(text = viewModel.likes3.toString())
                                }
                            }
                        }
                    }

                    Box(modifier = Modifier.padding(all = 5.dp).clickable { viewModel.click4() }){

                        Box(){
                            Column(modifier = Modifier
                                .width(200.dp)
                                .clip(RoundedCornerShape(16.dp)) // Устанавливаем радиус закругления
                                .background(color = Color.LightGray)
                                .padding(all = 5.dp)){

                                Text(text = viewModel.newsString4, modifier = Modifier.height(240.dp))
                                Row(modifier = Modifier.height(30.dp)) {
                                    Text(text = "  ❤\uFE0F  ")
                                    Text(text = viewModel.likes4.toString())
                                }
                            }
                        }
                    }
                }


            }
        }

    }
}