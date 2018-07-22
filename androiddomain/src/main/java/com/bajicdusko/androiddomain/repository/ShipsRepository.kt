package com.bajicdusko.androiddomain.repository

import androidx.lifecycle.LiveData
import com.bajicdusko.androiddomain.ApiCallback
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
interface ShipsRepository {
  fun getShipsPerShipClass(shipClass: ShipClass): LiveData<ResponseWrapper<List<Ship>>>
  fun getAllShips(): LiveData<ResponseWrapper<Map<String, List<Ship>>>>
  fun getEntriesCount(): Int
}