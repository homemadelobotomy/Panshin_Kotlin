package com.example.panshin_homework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf

class MainActivity : ComponentActivity() {
    private var listSize = 0
    private val dataList  = mutableStateListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {

        if (savedInstanceState != null){
            listSize = savedInstanceState.getInt("key_size")
            for(i in 1..listSize){
                dataList.add("$i")
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ListScreen(dataList, addItem = {addItem()})
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("key_size",dataList.size)
    }

    private fun addItem(){
        listSize++
        dataList.add("$listSize")
    }
}




