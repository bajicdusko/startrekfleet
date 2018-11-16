package com.bajicdusko.data.mapper

import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.data.db.ShipClassDb
import org.junit.Assert
import org.junit.Test

/**
 * Created by Dusko Bajic on 07.09.18.
 * GitHub @bajicdusko
 */
class ShipClassMapperTest {

  private val shipClass = ShipClass("Akira")
  private val shipClassDb = ShipClassDb("Archer")

  @Test
  fun shipClassMapper_givenShipClass_shouldMapToShipClassDb (){
    val shipClassDb = shipClass.asDb()
    Assert.assertEquals(shipClass.name, shipClassDb.name)
  }

  @Test
  fun shipClassMapper_givenShipClassDb_shouldMapToShipClass (){
    val shipClass = shipClassDb.asShipClass()
    Assert.assertEquals(shipClassDb.name, shipClass.name)
  }
}