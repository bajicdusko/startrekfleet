package com.bajicdusko.androiddomain

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
interface ApiCallback<T> {
  fun onSuccess(items: T)
  fun onFailure(throwable: Throwable)
}