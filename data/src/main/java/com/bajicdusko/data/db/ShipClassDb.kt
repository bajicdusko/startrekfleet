package com.bajicdusko.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bajicdusko.androiddomain.model.ShipClass

/**
 * Created by Dusko Bajic on 25.07.18.
 * GitHub @bajicdusko
 */
@Entity(tableName = "shipClass")
data class ShipClassDb(@PrimaryKey val name: String)