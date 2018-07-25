package com.bajicdusko.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */

@Database(entities = [ShipsDb::class, ShipClassDb::class], version = 1)
abstract class FleetDatabase : RoomDatabase() {
  abstract fun shipsDao(): ShipsDao
  abstract fun shipClasssDao(): ShipClassDao
}