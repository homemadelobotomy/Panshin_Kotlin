package com.example.panshin_homework

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf

class MainActivity : ComponentActivity() {
    private var listSize = 0
    private val dataList  = mutableStateListOf<String>()
    private var itemsNumber  = 1
    override fun onCreate(savedInstanceState: Bundle?) {

        if (savedInstanceState != null){
            listSize = savedInstanceState.getInt("key_size")
            itemsNumber = savedInstanceState.getInt("items_number")
            for(i in itemsNumber  ..listSize + itemsNumber - 1){
                dataList.add("$i")
            }

        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ListScreen(dataList, addItem = {addItem()}, deleteItem = {deleteItem()})
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("items_number", itemsNumber)
        outState.putInt("key_size",dataList.size)

    }

    private fun addItem(){
        listSize++
        val gg = listSize + itemsNumber - 1
        dataList.add("$gg")
    }
    private fun deleteItem(){
        dataList.removeAt(0)
        itemsNumber++
        listSize--

    }

}




