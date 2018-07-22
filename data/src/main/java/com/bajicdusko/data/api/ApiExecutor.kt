package com.bajicdusko.data.api

import com.bajicdusko.androiddomain.ApiCallback
import com.bajicdusko.data.exception.ApiException
import com.bajicdusko.data.exception.EmptyResponseException
import com.bajicdusko.data.exception.NoResponseException
import com.bajicdusko.data.exception.ResponseFailedWithUknownErrorException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */

fun <T> ApiCallback<T>.execute(endpoint: Call<T>){
  endpoint.enqueue(object  : Callback<T> {
    override fun onFailure(call: Call<T>?, t: Throwable?) {
      onFailure(t ?: ResponseFailedWithUknownErrorException())
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
      response?.let {
        if(!it.isSuccessful){
          onFailure(ApiException())
        } else {
          it.body()?.let {
            onSuccess(it)
          } ?: onFailure(EmptyResponseException())
        }
      } ?: onFailure(NoResponseException())
    }
  })
}