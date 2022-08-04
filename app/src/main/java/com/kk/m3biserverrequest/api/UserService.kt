package com.kk.m3biserverrequest.api

import com.kk.m3biserverrequest.models.QuotesList
import com.kk.m3biserverrequest.models.UserList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import retrofit2.http.Streaming

interface UserService {

    //call web api with this end point and param
    //BASE_URL="https://reqres.in/api/"
    //BASE_URL+"/users"+?page=1
    //https://reqres.in/api/users?page=1

//    @Headers("Host: <calculated when request is sent>",
//        "User-Agent: PostmanRuntime/7.29.2",
//        "Accept: */*",
//        "Accept-Encoding: gzip, deflate, br",
//        "Connection: keep-alive")

/*    @Headers(
        "Content-Type:application/json"
    )*/
    @Streaming
    @GET("/users")
    suspend fun getUsers(@Query("page") page_no: Int): Response<UserList>

    //testing quote data
    //https://quotable.io/quotes?page=1
    @GET("/quotes")
    suspend fun getQuotes(@Query("page") page_no: Int): Response<QuotesList>

}