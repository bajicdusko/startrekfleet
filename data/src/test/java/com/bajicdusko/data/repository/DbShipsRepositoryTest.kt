package com.bajicdusko.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.data.db.ShipsDao
import com.bajicdusko.data.db.ShipsDb
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Dusko Bajic on 07.09.18.
 * GitHub @bajicdusko
 */
@RunWith(MockitoJUnitRunner::class)
class DbShipsRepositoryTest {

  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  @Mock lateinit var shipsDaoMock: ShipsDao

  private val shipClass = ShipClass("Akira")

  @Test fun dbShipsRepository_givenThatDatabaseIsNotEmpty_shouldGetShipsPerShipClass() {

    doReturn(MutableLiveData<List<ShipsDb>>().also {
      it.value = listOf(
          ShipsDb(1, "Akira", "Akira class ship1", "NCC-AC1", ""),
          ShipsDb(2, "Akira", "Akira class ship2", "NCC-AC2", "")
      )
    }).`when`(shipsDaoMock).getAllShipsPerClass(shipClass.name)

    val dbShipsRepository = DbShipsRepository(shipsDaoMock)
    val shipsPerShipClass = dbShipsRepository.getShipsPerShipClass(shipClass)

    shipsPerShipClass.observeForever {
      Assert.assertEquals(2, it.data!!.size)
    }
  }

  @Test fun dbShipsRepository_givenThatDatabaseIsNotEmpty_shouldGetAllShips() {

    doReturn(MutableLiveData<List<ShipsDb>>().also {
      it.value = listOf(
          ShipsDb(1, "Akira", "Akira class ship1", "NCC-AC1", ""),
          ShipsDb(2, "Akira", "Akira class ship2", "NCC-AC2", ""),
          ShipsDb(3, "Archer", "Archer class ship1", "NCC-AR3", "")
      )
    }).`when`(shipsDaoMock).getAllShips()

    val dbShipsRepository = DbShipsRepository(shipsDaoMock)
    val shipsPerShipClass = dbShipsRepository.getAllShips()

    shipsPerShipClass.observeForever {
      Assert.assertEquals(2, it.data!!.size)
      Assert.assertEquals(1, it.data!!["Archer"]!!.size)
    }
  }

  @Test fun dbShipsRepository_givenThatDatabaseIsNotEmpty_shouldGetEntriesCount() {

    doReturn(MutableLiveData<Int>().also {
      it.value = 5
    }).`when`(shipsDaoMock).getEntriesCount()

    val dbShipsRepository = DbShipsRepository(shipsDaoMock)
    val entriesCount = dbShipsRepository.getEntriesCount()

    entriesCount.observeForever {
      Assert.assertEquals(5, it)
    }
  }
}