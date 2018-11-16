package com.bajicdusko.data.interactor

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.repository.ShipsRepository
import com.bajicdusko.androiddomain.wrappedData
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
class GetShipsPerShipClassTest {

  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  @Mock lateinit var shipsRepositoryMock: ShipsRepository

  private val shipClass = ShipClass("Akira")

  @Test fun shouldGetShipsPerShipClass() {

    doReturn(MutableLiveData<ResponseWrapper<List<Ship>>>().also {
      it.value = wrappedData {
        listOf(
            Ship("Ship1", "NCC1", "DP1", shipClass),
            Ship("Ship2", "NCC2", "DP2", shipClass)
        )
      }
    }).`when`(shipsRepositoryMock).getShipsPerShipClass(shipClass)

    val getShipsPerShipClass = GetShipsPerShipClass(shipsRepositoryMock)
    val shipsPerShipClassLiveData = getShipsPerShipClass.buildUseCase(shipClass)

    shipsPerShipClassLiveData.observeForever {
      Assert.assertEquals(2, it.data!!.size)
    }
  }
}