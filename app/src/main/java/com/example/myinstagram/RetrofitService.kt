package com.example.myinstagram


import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface RetrofitService {
    @GET("json/students/")
    fun getStudentsList(): Call<ArrayList<PersonFromServer>>

    @POST("json/students/")
    fun createStudents(
        @Body params: HashMap<String, Any>
    ): Call<PersonFromServer>

    @POST("json/students/")
    fun createStudentsEasy(
        @Body person: PersonFromServer
    ): Call<PersonFromServer>

    @POST("user/signup/")
    @FormUrlEncoded
    fun register(
        @Field("username") username: String,
        @Field("password1") password1: String,
        @Field("password2") password2: String,
    ):Call<User>

    @POST("user/login/")
    @FormUrlEncoded
    fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ):Call<User>

    @GET("instagram/post/list/all/")
    fun getAllposts():Call<ArrayList<Post>>

    @Multipart
    @POST("instagram/post/")
    fun uploadPost(
        @Part image : MultipartBody.Part,
        @Part ("content")requestBody: RequestBody
    ):Call<Post>

    @GET("instagram/post/list/")
    fun getUserPostList():Call<ArrayList<Post>>

    @GET("youtube/list/")
    fun getYoutubeList():Call<ArrayList<Youtube>>

    @GET("melon/list/")
    fun getSongList():Call<ArrayList<Song>>
}