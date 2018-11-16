package com.bajicdusko.startrekfleet.ship

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.wrappedData
import com.bajicdusko.data.interactor.GetShipsPerShipClass
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Dusko Bajic on 08.09.18.
 * GitHub @bajicdusko
 */
@RunWith(MockitoJUnitRunner::class)
class ShipsViewModelTest {

  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  @Mock lateinit var getShipsPerShipClassMock: GetShipsPerShipClass
  private val shipClass: ShipClass = ShipClass("Akira")

  @Test fun loadShipsPerShipClass() {

    doReturn(MutableLiveData<ResponseWrapper<List<Ship>>>().also {
      it.value = wrappedData {
        listOf(
            Ship("Ship1", "NCC1", "DP1", shipClass),
            Ship("Ship2", "NCC2", "DP2", shipClass)
        )
      }
    }).`when`(getShipsPerShipClassMock).buildUseCase(shipClass)

    val shipsViewModel = ShipsViewModel(getShipsPerShipClassMock)
    val shipsPerShipClassLiveData = shipsViewModel.loadShipsPerShipClass(shipClass)

    shipsPerShipClassLiveData.observeForever {
      Assert.assertEquals(2, it.data!!.size)
    }
  }
}