
package com.example.panshin_homework
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState

import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.Text
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale

import androidx.compose.ui.platform.LocalContext

import coil.compose.AsyncImage
import coil.request.ImageRequest

import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.flow.update



/*class ImageViewModel(
    private val requestController: RequestController
) : ViewModel() {

    private val _imageState = MutableStateFlow<Result>(Result.Error("No data yet"))
    val imageState: StateFlow<Result> = _imageState

    fun fetchImages() {
        viewModelScope.launch {
            _imageState.value = requestController.requestImage()
        }
    }
}*/
class ImageViewModel(
    private val requestController: RequestController
) : ViewModel() {

    private val _images = MutableStateFlow<List<ImageItem>>(emptyList())
    val images: StateFlow<List<ImageItem>> = _images.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _page = MutableStateFlow(1)
    val perPage = 5

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    init {
        loadNextPage()
    }
    fun loadNextPage() {
        if(_isLoading.value) return

        viewModelScope.launch {
            _isLoading.value = true
            try {

                //val result =  requestController.requestImage(perPage)
                val result =  requestController.requestImage(_page.value,perPage)
                if(result is Result.Ok){
                    _images.update { currentImages ->
                        (currentImages + result.images).distinctBy { it.urls.rawUrl } }
                    _page.value++
                }
                if (result is Result.Error){
                    _error.value = result.error

                }
            }
            catch (e: Exception){
                _error.value = e.message?: "Network Error"
            }
            finally {
                _isLoading.value = false
            }

        }
    }
}
@Composable
fun MainScreen() {
    val imageViewModel = remember {
        ImageViewModel(RetrofitController("https://api.unsplash.com/"))
    }
    val images by imageViewModel.images.collectAsState()
    val isLoading by imageViewModel.isLoading.collectAsState()
    val error by imageViewModel.error.collectAsState()
    val lazyListState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (images.isEmpty()) {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (error != null) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error:$error")
                    Button(onClick = { imageViewModel.loadNextPage() }) { Text("Retry") }
                }

            } else {
                Text("No data")
            }

        } else {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize()
            ) {
                items(items = images, key = { it.urls.rawUrl }) { image ->
                    ImageItemCard(image)
                }
                item {
                    if (isLoading) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) { CircularProgressIndicator() }
                    }
                }
            }
            LaunchedEffect(lazyListState) {
                snapshotFlow { lazyListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collect { lastVisibleItemIndex ->
                        if (lastVisibleItemIndex != null &&
                            lastVisibleItemIndex >= images.size - 2 &&
                            !isLoading
                        ) {
                            imageViewModel.loadNextPage()
                        }
                    }
            }
        }
        LaunchedEffect(Unit) {
            imageViewModel.loadNextPage()
        }


    }
}
@Composable
fun ImageItemCard(image: ImageItem) {
    Column(modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.urls.rawUrl)
                .addHeader("Authorization", "Client-ID KkEwtM9jsHnAgL2tukfUW0ywNj900soc99VGKes4MxE")
                .crossfade(true)
                .build(),
            contentDescription = "Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .padding(4.dp),
            contentScale = ContentScale.Crop
        )
    }
}


