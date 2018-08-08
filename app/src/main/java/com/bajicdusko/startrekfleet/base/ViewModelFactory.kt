package com.bajicdusko.startrekfleet.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bajicdusko.androiddomain.repository.ShipClassRepository
import com.bajicdusko.androiddomain.repository.ShipsRepository
import com.bajicdusko.data.api.StarTrekFleetApi
import com.bajicdusko.data.db.ShipClassDao
import com.bajicdusko.data.db.ShipsDao
import com.bajicdusko.data.interactor.GetShipClasses
import com.bajicdusko.data.interactor.GetShipsPerShipClass
import com.bajicdusko.data.repository.ApiShipClassRepository
import com.bajicdusko.data.repository.ApiShipsRepository
import com.bajicdusko.data.repository.DbShipClassRepository
import com.bajicdusko.data.repository.DbShipsRepository
import com.bajicdusko.startrekfleet.StarTrekFleetApp
import com.bajicdusko.startrekfleet.ship.ShipsViewModel
import com.bajicdusko.startrekfleet.shipclass.ShipClassesViewModel

/**
 * Created by Dusko Bajic on 24.07.18.
 * GitHub @bajicdusko
 */
class ViewModelFactory(
    private val shipClassDao: ShipClassDao,
    private val shipsDao: ShipsDao,
    private val starTrekFleetApi: StarTrekFleetApi
) : ViewModelProvider.Factory {

  constructor(starTrekFleetApp: StarTrekFleetApp) : this(
      starTrekFleetApp.shipClassDao,
      starTrekFleetApp.shipsDao,
      starTrekFleetApp.starTrekFleetApi
  )

  @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return when {

      modelClass.isAssignableFrom(ShipClassesViewModel::class.java) -> {
        val dbShipClassRepository = DbShipClassRepository(shipClassDao)
        val shipClassesRepository: ShipClassRepository = ApiShipClassRepository(
            starTrekFleetApi, dbShipClassRepository)
        val shipClasses = GetShipClasses(shipClassesRepository)

        ShipClassesViewModel(shipClasses)
      }

      modelClass.isAssignableFrom(ShipsViewModel::class.java) -> {
        val dbShipsRepository = DbShipsRepository(shipsDao)
        val shipsRepository: ShipsRepository = ApiShipsRepository(starTrekFleetApi,
            dbShipsRepository)
        val getShipsPerShipClass = GetShipsPerShipClass(shipsRepository)
        ShipsViewModel(getShipsPerShipClass)
      }
      else -> throw IllegalArgumentException("Unknown ViewModel ${modelClass.name}")
    } as T
  }
}