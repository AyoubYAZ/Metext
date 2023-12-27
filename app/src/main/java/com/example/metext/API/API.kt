package com.example.metext.API

import com.example.metext.model.Posts
import retrofit2.http.GET
interface API {

    // get posts api interface
    @GET("posts/1")
    suspend fun getPosts(): Posts
}