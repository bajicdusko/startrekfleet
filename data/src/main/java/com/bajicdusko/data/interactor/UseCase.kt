package com.bajicdusko.data.interactor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.ResponseWrapper

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
interface UseCase<P, T> {

  fun buildUseCase(param: P): LiveData<ResponseWrapper<List<T>>>
}