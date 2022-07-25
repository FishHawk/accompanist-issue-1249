package com.example.accompanistissue1249

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Reader() }
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun Reader(
) {
    var size by remember { mutableStateOf(10) }
    var showLongText by remember { mutableStateOf(false) }
    Box(modifier = Modifier.fillMaxSize()) {
        val state = rememberSaveable(size, saver = PagerState.Saver) { PagerState() }
        HorizontalPager(
            count = size,
            modifier = Modifier.fillMaxSize(),
            state = state,
        ) {
            Text(text = if (showLongText) "placeholder placeholder placeholder" else "short text")
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart),
        ) {
            val list = remember(size) { mutableStateListOf(state.currentPage) }
            LaunchedEffect(state.currentPage) { list.add(state.currentPage) }
            Text(text = "page history: ${list.joinToString(", ")}")
            Button(onClick = { showLongText = !showLongText }) { Text(text = "toggle text length") }
            Button(onClick = {
                size = if (size == 20) 10 else 20
            }) { Text(text = "change pager size") }
        }
    }
}