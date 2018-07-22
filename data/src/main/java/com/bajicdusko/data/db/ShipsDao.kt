package com.bajicdusko.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */

@Dao
interface ShipsDao {

  @Query("SELECT COUNT(*) FROM ships")
  fun getEntriesCount(): Int

  @Query("SELECT * FROM ships")
  fun getAllShips(): LiveData<List<ShipsDb>>

  @Query("SELECT * FROM ships WHERE shipClass = :shipClassName")
  fun getAllShipsPerClass(shipClassName: String): LiveData<List<ShipsDb>>
}