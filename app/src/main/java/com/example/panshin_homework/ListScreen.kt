package com.example.panshin_homework

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row


import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ListScreen(dataList: List<String>, addItem: () -> Unit,deleteItem:() -> Unit)  {

    val currentOrientationLandscape =
        (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE)
    Column (modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(if (currentOrientationLandscape) 4 else 3),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)

            ) {
                items(dataList) { item ->
                    ListItem(item)
                }
            }

    Row  (modifier = Modifier
        .fillMaxWidth()
        , horizontalArrangement = Arrangement.Center,
        ) {
        FloatingActionButton(
            onClick = deleteItem,
            modifier = Modifier
                .padding(10.dp),
            containerColor = colorResource(R.color.light_purple)
        ) {
            Icon(Icons.Filled.Clear, "Удалить")
        }
        FloatingActionButton(
            onClick = addItem,
            modifier = Modifier
                .padding(10.dp),
            containerColor = colorResource(R.color.light_purple)
        ) {
            Icon(Icons.Filled.Add, "Добавить")
        }
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
            color = Color.White,
            fontSize = 32.sp
        )
    }
}