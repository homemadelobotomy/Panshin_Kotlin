package com.example.panshin_homework

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ImageApi {
    @GET("photos/random/?client_id=X79-CUmynexhT219vf3CE69iYjKZZXStAX82PqIlsUM")

//    suspend fun getImages(
//        @Query("page") page:Int,
//        @Query("per_page") perPage: Int
//    ): Response<List<ImageItem>>
    suspend fun getImages(

        @Query("count") perPage: Int
    ): Response<List<ImageItem>>
}

class RetrofitController(api: String) : RequestController {

    private val retrofit = Retrofit.Builder()
        .baseUrl(api)
        .addConverterFactory(
            Json { ignoreUnknownKeys = true }
                .asConverterFactory(
                    "application/json; charset=UTF8".toMediaType()
                )
        )
        .build()

    private val imageApi = retrofit.create(ImageApi::class.java)

    override suspend fun requestImage(page: Int,perPage: Int): Result {
        //val response = imageApi.getImages(page,perPage)
        val response = imageApi.getImages(perPage)
        return if (response.isSuccessful) {
            response.body()?.let {images ->
                Result.Ok(images)
            } ?: Result.Error("Empty images")
        } else {
            Result.Error(response.code().toString())
        }
    }
}

