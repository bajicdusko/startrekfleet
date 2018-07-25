package com.bajicdusko.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.repository.ShipClassRepository
import com.bajicdusko.androiddomain.wrappedData
import com.bajicdusko.data.db.ShipClassDao

/**
 * Created by Dusko Bajic on 25.07.18.
 * GitHub @bajicdusko
 */
class DbShipClassRepository(val shipClassDao: ShipClassDao) : ShipClassRepository {
  override fun getShipClasses(): LiveData<ResponseWrapper<List<ShipClass>>> {
    return Transformations.map(shipClassDao.getShipClasses()) {
      val shipClasses = mutableListOf<ShipClass>()
      it.forEach {
        shipClasses.add(it.toShipClass())
      }
      wrappedData { shipClasses.toList() }
    }
  }

  override fun getEntriesCount(): LiveData<Int> = shipClassDao.getCount()
}