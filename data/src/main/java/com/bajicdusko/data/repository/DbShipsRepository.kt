package com.bajicdusko.data.repository

import androidx.arch.core.util.Function
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.repository.ShipsRepository
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.data.db.ShipsDao
import com.bajicdusko.data.exception.SourceLiveDataValueEmptyException
import com.bajicdusko.androiddomain.wrappedData
import com.bajicdusko.androiddomain.wrappedError
import com.bajicdusko.data.db.ShipsDb

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
class DbShipsRepository constructor(private val shipsDao: ShipsDao) : ShipsRepository {

  override fun getShipsPerShipClass(shipClass: ShipClass): LiveData<ResponseWrapper<List<Ship>>> =
      Transformations.map(shipsDao.getAllShipsPerClass(shipClass.name)) { shipsDb ->
        val ships = mutableListOf<Ship>()
        shipsDb?.forEach { shipDb ->
          ships.add(shipDb.toShip())
        }
        wrappedData { ships.toList() }
      }

  override fun getAllShips(): LiveData<ResponseWrapper<Map<String, List<Ship>>>> =
      Transformations.map(shipsDao.getAllShips()) { shipsDb ->
        val shipsByShipClass = mutableMapOf<String, List<Ship>>()
        val shipsGroupedByShipClass = shipsDb.groupBy { it.shipClass }

        shipsGroupedByShipClass.keys.forEach { key ->
          val ships = mutableListOf<Ship>()
          shipsGroupedByShipClass[key]?.forEach {
            ships.add(it.toShip())
          }

          shipsByShipClass[key] = ships
        }

        wrappedData { shipsByShipClass.toMap() }
      }

  override fun getEntriesCount(): LiveData<Int> = shipsDao.getEntriesCount()
}