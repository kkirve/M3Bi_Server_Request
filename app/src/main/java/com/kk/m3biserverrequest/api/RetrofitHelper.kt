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
    val headerInterceptor = object: Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            var request = chain.request()

            request = request.newBuilder()
                .addHeader("x-device-type", Build.DEVICE)
                .addHeader("Accept-Language", Locale.getDefault().language)

                .build()

            val response = chain.proceed(request)
            return response
        }
    }

/*    val certificatePinner = CertificatePinner.Builder()
        .add(
            "https://reqres.in/",
            "sha256/ZCOF65ADBWPDK8P2V7+mqodtvbsTRR/D74FCU+CEEA="
        )
        .build()*/

/*    val inputStream = resources.openRawResource(R.raw.mycer)
    val keyStoreType = KeyStore.getDefaultType()
    val keyStore :KeyStore= KeyStore.getInstance(keyStoreType)
    keyStore.load(inputStream, null)

    val tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm()
    val trustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
    trustManagerFactory.init(keyStore)*/


    // Create OkHttp Client
    private val okHttp = OkHttpClient.Builder()
        .callTimeout(50000, TimeUnit.SECONDS)
        .addInterceptor(headerInterceptor)
        .addInterceptor(logger)
        //.sslSocketFactory(sslContext.getSocketFactory())

    //return retrofit instance
    fun getInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())
            .build()
    }
}