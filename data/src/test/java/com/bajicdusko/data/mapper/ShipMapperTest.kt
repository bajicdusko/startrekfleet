package com.bajicdusko.data.mapper

import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.data.db.ShipsDb
import org.junit.Assert
import org.junit.Test

/**
 * Created by Dusko Bajic on 07.09.18.
 * GitHub @bajicdusko
 */
class ShipMapperTest {

  private val shipClass = ShipClass("Akira")
  private val ship = Ship("Test ship", "NCCTEST", "DP1", shipClass)
  private val shipDb = ShipsDb(1, "Akira", "Test ship 2", "NCCTEST2", "DP2")

  @Test
  fun shipMapper_givenShip_shouldMapToShipDb (){
    val shipDb = ship.asShipDb()
    Assert.assertEquals(ship.name, shipDb.name)
    Assert.assertEquals(ship.designation, shipDb.registry)
    Assert.assertEquals(ship.depiction, shipDb.depiction)
    Assert.assertEquals(ship.shipClass.name, shipDb.shipClass)
  }

  @Test
  fun shipMapper_givenShipDb_shouldMapToShip (){
    val ship = shipDb.asShip()
    Assert.assertEquals(shipDb.name, ship.name)
    Assert.assertEquals(shipDb.registry, ship.designation)
    Assert.assertEquals(shipDb.depiction, ship.depiction)
    Assert.assertEquals(shipDb.shipClass, ship.shipClass.name)
  }
}