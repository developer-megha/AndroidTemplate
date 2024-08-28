package com.android.template.network

import android.text.Html
import android.util.Log
import com.android.template.others.Cons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo {
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): NetworkResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()
                Log.d("NewProject", "safeApiCall: $response")

                if (response.isSuccessful) { // [200..300)
                    NetworkResult.Success(data = response.body()!!)
                } else {
                    when(response.code()) {
                        in 400..499 -> {
                            val errorResponse = try {
                                JSONObject(response.errorBody()!!.charStream().readText())
                            } catch (e: Exception) {
                                null
                            }
                            NetworkResult.Error(
                                errorMessage = errorResponse?.getString("message")
                                    ?: "Something went wrong"
                            )
                        }
                        500 -> {
                            // from response try to show error exception with line code like postman that helps the backend
                            val errorHtml = response.errorBody()?.charStream()?.readText()
                            val exception = Html.fromHtml(errorHtml, Html.FROM_HTML_MODE_LEGACY)
                            Log.d(Cons.TAG, "safeApiCall500: $exception")
                            NetworkResult.Error(errorMessage = "Internal Server Error")
                        }
                        else -> {
                            NetworkResult.Error(errorMessage = "Unknown Error")
                        }
                    }
                }

            } catch (e: HttpException) {
                NetworkResult.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                NetworkResult.Error(errorMessage = "Please check your network connection")
            } catch (e: Exception) {
                Log.d(Cons.TAG, "safeApiCall: $e")
                NetworkResult.Error(errorMessage = "Something went wrong")
            }
        }
    }
}