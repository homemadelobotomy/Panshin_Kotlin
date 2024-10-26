package com.example.panshin_homework

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ListScreen() {

    val dataList = remember { mutableStateListOf<String>() }
    val itemCount = rememberSaveable { mutableIntStateOf(0) }
    val currentOrientationLandscape =
        (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)
    if (dataList.isEmpty()) {
        for (i in 1..itemCount.intValue) {
            dataList.add("$i")
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(if (currentOrientationLandscape) 4 else 3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter)

        ) {
            items(dataList) { item ->
                ListItem(item)
            }
        }

        Button(
            onClick = {
                itemCount.intValue += 1
                dataList.add("${itemCount.intValue}")
            },
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.BottomCenter)

        ) {
            Text(text = "Добавить элемент")
        }
    }
}


fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier) : Modifier {
    return if (condition){
        then(modifier(Modifier))
    }
    else{
        this
    }
}
@Composable
fun ListItem(number: String) {

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .aspectRatio(1f)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(Color.Red)
            .conditional(number.toInt() % 2 == 1){
                background(Color.Blue)
            }

    ) {
        Text(
            text = number,
            color = Color.White ,
            modifier = Modifier,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 32.sp
        )
    }
}