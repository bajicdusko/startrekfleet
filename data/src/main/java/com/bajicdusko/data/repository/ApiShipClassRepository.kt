package com.bajicdusko.data.repository

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bajicdusko.androiddomain.ApiCallback
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.repository.ShipClassRepository
import com.bajicdusko.androiddomain.wrappedData
import com.bajicdusko.androiddomain.wrappedError
import com.bajicdusko.data.api.StarTrekFleetApi
import com.bajicdusko.data.api.execute

/**
 * Created by Dusko Bajic on 25.07.18.
 * GitHub @bajicdusko
 */
@VisibleForTesting
open class ApiShipClassRepository(val api: StarTrekFleetApi,
    val dbShipClassRepository: DbShipClassRepository) : ShipClassRepository {
  override fun getShipClasses(): LiveData<ResponseWrapper<List<ShipClass>>> =
      Transformations.switchMap(dbShipClassRepository.getEntriesCount()) {
        if (it == 0) {
          val responseLiveData = MutableLiveData<ResponseWrapper<List<ShipClass>>>()
          val appCallback: ApiCallback<List<ShipClass>> = object : ApiCallback<List<ShipClass>> {
            override fun onSuccess(items: List<ShipClass>) {
              dbShipClassRepository.insert(items) {
                responseLiveData.value = wrappedData { items }
              }
            }

            override fun onFailure(throwable: Throwable) {
              responseLiveData.value = wrappedError { throwable }
            }
          }

          appCallback.execute(api.getShipClasses())
          responseLiveData
        } else {
          dbShipClassRepository.getShipClasses()
        }
      }

  override fun getEntriesCount(): LiveData<Int> {
    throw NotImplementedError()
  }
}