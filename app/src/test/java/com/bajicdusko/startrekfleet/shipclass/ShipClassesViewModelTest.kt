package com.bajicdusko.startrekfleet.shipclass

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.wrappedData
import com.bajicdusko.data.interactor.GetShipClasses
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
class ShipClassesViewModelTest {

  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  @Mock lateinit var getShipClasses: GetShipClasses

  @Test fun onLoad() {

    doReturn(MutableLiveData<ResponseWrapper<List<ShipClass>>>().also {
      it.value = wrappedData {
        listOf(
            ShipClass("Akira"),
            ShipClass("Archer")
        )
      }
    }).`when`(getShipClasses).buildUseCase(Unit)

    val shipClassesViewModel = ShipClassesViewModel(getShipClasses)
    val shipClassesLiveData = shipClassesViewModel.observe()

    shipClassesLiveData.observeForever {
      Assert.assertEquals(2, it.data!!.size)
    }
  }
}