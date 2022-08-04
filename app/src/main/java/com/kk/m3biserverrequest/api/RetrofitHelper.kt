package com.kk.m3biserverrequest.api

import android.app.Application
import android.content.Context
import android.os.Build
import com.kk.m3biserverrequest.R
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import java.nio.charset.Charset
import java.security.KeyStore
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

object RetrofitHelper {

    private const val BASE_URL = "https://reqres.in/api/"
    private const val BASE_URL_Quote = "https://quotable.io/"

    // Create Logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    // Create a Custom Interceptor to apply Headers application wide
    val headerInterceptor = object : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            var request = chain.request()

            request = request.newBuilder()
                .addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language)
                .addHeader("Content-Type","application/json")
                .build()

            val response = chain.proceed(request)
            return response
        }
    }

    val ChunksInterceptor    = object :Interceptor {

        val Utf8Charset = Charset.forName ("UTF-8")

        override fun intercept (chain: Interceptor.Chain): Response {
            val originalResponse = chain.proceed (chain.request ())
            val responseBody = originalResponse.body ()
            val source = responseBody!!.source ()

            val buffer = Buffer () // We create our own Buffer

            // Returns true if there are no more bytes in this source
            while (!source.exhausted ()) {
                val readBytes = source.read (buffer, Long.MAX_VALUE) // We read the whole buffer
                val data = buffer.readString (Utf8Charset)

                println ("Read: $readBytes bytes")
                println ("Content: \n $data \n")
            }

            return originalResponse
        }
    }

    val certificatePinner = CertificatePinner.Builder()
        .add(
            "https://reqres.in/",
            "sha256/5kJvNEMw0KjrCAu7eXY5HZdvyCS13BbA0VJG1RSP91w="
        )
        .build()

    private val okHttp1 = OkHttpClient.Builder()
        .callTimeout(6000, TimeUnit.SECONDS)
        .certificatePinner(certificatePinner)
        .addInterceptor(headerInterceptor)
        .addInterceptor(logger)


    private val okHttp = OkHttpClient.Builder()
        .certificatePinner(certificatePinner)
        .addInterceptor(ChunksInterceptor)
        .addInterceptor(logger)



    //return retrofit instance
    fun getInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
            .build()
    }



}