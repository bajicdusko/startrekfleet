package com.bajicdusko.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * Created by Dusko Bajic on 25.07.18.
 * GitHub @bajicdusko
 */

@Dao
interface ShipClassDao {

  @Query("SELECT * FROM shipClass")
  fun getShipClasses(): LiveData<List<ShipClassDb>>

  @Query("SELECT COUNT(*) FROM shipClass")
  fun getCount(): LiveData<Int>
}