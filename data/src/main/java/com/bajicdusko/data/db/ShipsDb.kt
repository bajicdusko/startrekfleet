package com.bajicdusko.data.db

import androidx.room.Entity
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */

@Entity(tableName = "Ships")
data class ShipsDb(val shipClass: String, val name: String, val registry: String, val depiction: String) {
  fun toShip(): Ship = Ship(name, registry, depiction, ShipClass(shipClass))
}