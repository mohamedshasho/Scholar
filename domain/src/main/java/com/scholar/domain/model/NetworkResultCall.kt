package com.scholar.domain.model

import okhttp3.Request
import okio.Timeout
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>
) : Call<NetworkResult<T>> {

    override fun enqueue(callback: Callback<NetworkResult<T>>) {
        proxy.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val networkResult = handleApi {
                    response
                }
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }
            override fun onFailure(call: Call<T>, t: Throwable) {
                val networkResult = NetworkResult.Exception<T>(t)
                callback.onResponse(this@NetworkResultCall, Response.success(networkResult))
            }
        })
    }

    override fun execute(): Response<NetworkResult<T>> = throw NotImplementedError()
    override fun clone(): Call<NetworkResult<T>> = NetworkResultCall(proxy.clone())
    override fun request(): Request = proxy.request()
    override fun timeout(): Timeout = proxy.timeout()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() { proxy.cancel() }
}

fun <T : Any> handleApi(
    execute:  () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        val error :String? = response.errorBody()?.string()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            val errorObj = error?.let { JSONObject(it) }
            val message = errorObj?.getString("message")
            NetworkResult.Error(code = response.code(), error = message)
        }
    } catch (e: HttpException) {
        NetworkResult.Error(code = e.code(), error = e.message())
    } catch (e: Throwable) {
        NetworkResult.Exception(e)
    }
}