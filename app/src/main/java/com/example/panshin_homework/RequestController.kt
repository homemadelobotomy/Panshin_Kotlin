package com.example.panshin_homework


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


interface RequestController {

    //suspend fun requestImage(perPage: Int): Result
    suspend fun requestImage(page: Int, perPage: Int): Result
}

sealed interface Result {
    data class Ok(val images: List<ImageItem>) : Result
    data class Error(val error: String) : Result
}

@Serializable
data class Origin(
    @SerialName("raw") val rawUrl:String
)
@Serializable
data class ImageItem(
    @SerialName("urls") val urls: Origin
)