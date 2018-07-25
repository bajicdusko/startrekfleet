package com.bajicdusko.androiddomain.repository

import androidx.lifecycle.LiveData
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
interface ShipClassRepository {
  fun getShipClasses(): LiveData<ResponseWrapper<List<ShipClass>>>
  fun getEntriesCount(): LiveData<Int>
}