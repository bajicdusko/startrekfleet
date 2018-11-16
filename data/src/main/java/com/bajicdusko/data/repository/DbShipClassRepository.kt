package com.bajicdusko.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.bajicdusko.androiddomain.BackgroundExecutor.Companion.threadPool
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.repository.ShipClassRepository
import com.bajicdusko.androiddomain.wrappedData
import com.bajicdusko.data.db.ShipClassDao
import com.bajicdusko.data.mapper.asDbShipClasses
import com.bajicdusko.data.mapper.asShipClass

/**
 * Created by Dusko Bajic on 25.07.18.
 * GitHub @bajicdusko
 */
class DbShipClassRepository(private val shipClassDao: ShipClassDao) : ShipClassRepository {
  override fun getShipClasses(): LiveData<ResponseWrapper<List<ShipClass>>> {
    return Transformations.map(shipClassDao.getShipClasses()) { dbShipClasses ->
      val shipClasses = mutableListOf<ShipClass>()
      dbShipClasses.forEach {
        shipClasses.add(it.asShipClass())
      }
      wrappedData { shipClasses.toList() }
    }
  }

  override fun getEntriesCount(): LiveData<Int> = shipClassDao.getCount()

  fun insert(items: List<ShipClass>, afterInsert: (() -> Unit)?) {
    threadPool.execute(fn = {
      shipClassDao.insertAll(items.asDbShipClasses())
    }, callback = afterInsert)
  }
}