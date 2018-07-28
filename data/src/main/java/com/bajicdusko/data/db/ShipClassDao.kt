package com.bajicdusko.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(shipClassDbs: List<ShipClassDb>): List<Long>
}