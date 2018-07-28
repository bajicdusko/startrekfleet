package com.bajicdusko.data.mapper

import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.data.db.ShipClassDb
import com.bajicdusko.data.db.ShipsDb

/**
 * Created by Dusko Bajic on 28.07.18.
 * GitHub @bajicdusko
 */

fun ShipClassDb.asShipClass() = ShipClass(name)
fun ShipClass.asDb() = ShipClassDb(name)

fun List<ShipClass>.asDbShipClasses(): List<ShipClassDb> {
  val shipClassesDb = mutableListOf<ShipClassDb>()
  forEach {
    shipClassesDb.add(it.asDb())
  }
  return shipClassesDb
}

fun List<ShipClassDb>.asShipClasses(): List<ShipClass> {
  val shipClasses = mutableListOf<ShipClass>()
  forEach {
    shipClasses.add(it.asShipClass())
  }
  return shipClasses
}

fun ShipsDb.asShip() = Ship(name, registry, depiction, ShipClass(shipClass))
fun Ship.asShipDb() = ShipsDb(shipClass.name, name, designation, depiction)

fun List<Ship>.asDbShips(): List<ShipsDb> {
  val shipsDb = mutableListOf<ShipsDb>()
  forEach {
    shipsDb.add(it.asShipDb())
  }
  return shipsDb
}