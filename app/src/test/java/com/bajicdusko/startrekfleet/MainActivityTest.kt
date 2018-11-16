package com.bajicdusko.startrekfleet

import androidx.appcompat.app.AppCompatDelegate
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentController
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.wrappedData
import com.bajicdusko.data.interactor.GetShipClasses
import com.bajicdusko.startrekfleet.shipclass.ShipClassesViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


/**
 * Created by Dusko Bajic on 08.08.18.
 * GitHub @bajicdusko
 */

class MainActivityTest {

  @JvmField @Rule val taskExecutorRule = InstantTaskExecutorRule()

  @Mock lateinit var appCompatDelegateMock: AppCompatDelegate
  @Mock lateinit var fragmentControllerMock: FragmentController

  @Before
  fun setUp(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  fun mainActivity_shouldDisplayShipClasses() {

    val mainActivity = Mockito.spy(MainActivity()).also {
      it.mockActivityInternals(appCompatDelegateMock, fragmentControllerMock)
    }

    val getShipClasses = Mockito.mock(GetShipClasses::class.java)
    val shipClassesViewModelMock = Mockito.spy(ShipClassesViewModel(getShipClasses))
    mainActivity.shipClassesViewModel = shipClassesViewModelMock

    val shipClassesLiveData = MutableLiveData<ResponseWrapper<List<ShipClass>>>()
    val responseWrapper = wrappedData { emptyList<ShipClass>() }
    shipClassesLiveData.value  = responseWrapper

    Mockito.`when`(shipClassesViewModelMock.observe()).thenReturn(shipClassesLiveData)

    mainActivity.onStart()

    Mockito.verify(mainActivity).renderResult(Matchers.any(responseWrapper::class.java))
  }
}