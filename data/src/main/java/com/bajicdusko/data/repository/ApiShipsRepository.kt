package com.bajicdusko.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bajicdusko.androiddomain.ApiCallback
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.repository.ShipsRepository
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.data.api.StarTrekFleetApi
import com.bajicdusko.data.api.execute
import com.bajicdusko.androiddomain.wrappedData
import com.bajicdusko.androiddomain.wrappedError

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
class ApiShipsRepository constructor(private val starTrekFleetApi: StarTrekFleetApi,
    private val dbShipsRepository: DbShipsRepository) : ShipsRepository {

  override fun getShipsPerShipClass(shipClass: ShipClass): LiveData<ResponseWrapper<List<Ship>>> {
    return if (dbShipsRepository.getEntriesCount() == 0) {

      Transformations.map(getAllShips()) { responseWrapper ->
        if (responseWrapper.error != null) {
          wrappedData { responseWrapper.data?.get(shipClass.name) ?: emptyList() }
        } else {
          wrappedError { responseWrapper.error!! }
        }
      }
    } else {
      dbShipsRepository.getShipsPerShipClass(shipClass)
    }
  }

  override fun getAllShips(): LiveData<ResponseWrapper<Map<String, List<Ship>>>> {
    val responseLiveData = MutableLiveData<ResponseWrapper<Map<String, List<Ship>>>>()
    val appCallback: ApiCallback<Map<String, List<Ship>>> = object : ApiCallback<Map<String, List<Ship>>> {
      override fun onSuccess(items: Map<String, List<Ship>>) {
        responseLiveData.value = wrappedData { items }
      }

      override fun onFailure(throwable: Throwable) {
        responseLiveData.value = wrappedError { throwable }
      }
    }

    appCallback.execute<Map<String, List<Ship>>>(starTrekFleetApi.getWholeCollection())
    return responseLiveData
  }

  override fun getEntriesCount(): Int {
    throw NotImplementedError()
  }
}