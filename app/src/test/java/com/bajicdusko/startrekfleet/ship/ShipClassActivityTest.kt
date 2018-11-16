package com.bajicdusko.startrekfleet.ship

import androidx.appcompat.app.AppCompatDelegate
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentController
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.wrappedData
import com.bajicdusko.data.interactor.GetShipsPerShipClass
import com.bajicdusko.startrekfleet.mockActivityInternals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Dusko Bajic on 08.09.18.
 * GitHub @bajicdusko
 */
@RunWith(MockitoJUnitRunner::class)
class ShipClassActivityTest {

  @JvmField @Rule val taskExecutorRule = InstantTaskExecutorRule()

  @Mock lateinit var appCompatDelegateMock: AppCompatDelegate
  @Mock lateinit var fragmentControllerMock: FragmentController

  @Test fun shipClassActivity_shouldDisplayShipsPerClass() {
    val shipClassActivity = spy(ShipClassActivity()).also {
      it.mockActivityInternals(appCompatDelegateMock, fragmentControllerMock)
    }

    val shipClass = ShipClass("Akira")
    shipClassActivity.shipClass = shipClass

    val getShipsPerShipClassMock = mock(GetShipsPerShipClass::class.java)
    val shipsViewModel = spy(ShipsViewModel(getShipsPerShipClassMock))
    shipClassActivity.shipsViewModel = shipsViewModel

    val wrappedShipsResponse = wrappedData { emptyList<Ship>() }
    doReturn(MutableLiveData<ResponseWrapper<List<Ship>>>().also {
      it.value = wrappedShipsResponse
    }).`when`(shipsViewModel).loadShipsPerShipClass(shipClass)

    shipClassActivity.onStart()

    verify(shipClassActivity).onResult(ArgumentMatchers.any(wrappedShipsResponse::class.java))
  }
}