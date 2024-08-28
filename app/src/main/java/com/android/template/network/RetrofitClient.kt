package com.android.template.network

import android.util.Log
import com.android.template.base.App
import com.google.gson.GsonBuilder
import com.android.template.base.SharedPref
import com.android.template.others.Cons
import okhttp3.Authenticator
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Singleton
class RetrofitClient {

    companion object {

        fun getRegisterRetrofit(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .baseUrl(Cons.NETWORK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getRegister())
                .build()
        }
        private fun getRegister(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            /** set your desired log level */
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            httpClient.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(Cache(App.get().cacheDir, 10 * 1024 * 1024)) // 10 MB cache
                .addInterceptor(Interceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Accept", "application/json")
                        .build()
                    chain.proceed(request)
                })
            return httpClient.build()
        }

        fun getUserDetails(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()
            return Retrofit.Builder()
                .baseUrl(Cons.NETWORK_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getUser())
                .build()
        }
        private fun getUser(): OkHttpClient {
            val logging = HttpLoggingInterceptor()
            /** set your desired log level */
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            //httpClient.authenticator(TokenAuthenticator())
            httpClient.connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .cache(Cache(App.get().cacheDir, 10 * 1024 * 1024)) // 10 MB cache
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + (SharedPref.get().getStringValue(Cons.token)))
                        .addHeader("Accept", "application/json").build()
                    Log.e("Token =>", SharedPref.get().getStringValue(Cons.token) ?: "NULL")
                    val response = chain.proceed(request)
                    response
                })
            return httpClient.build()
        }
    }

    private class TokenAuthenticator : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request? {
            if (response.code == 401) {
                val message = "Your Session is Expired. Please login again to Continue."
            }
            return null
        }
    }
}





