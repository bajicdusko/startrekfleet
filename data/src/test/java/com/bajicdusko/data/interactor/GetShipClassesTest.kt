package com.bajicdusko.data.interactor

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.repository.ShipClassRepository
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
class GetShipClassesTest {

  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  @Mock lateinit var shipClassRepositoryMock: ShipClassRepository

  @Test fun shouldGetShipClasses() {

    doReturn(MutableLiveData<ResponseWrapper<List<ShipClass>>>().also {
      it.value = wrappedData {
        listOf(
            ShipClass("Akira"),
            ShipClass("Archer")
        )
      }
    }).`when`(shipClassRepositoryMock).getShipClasses()

    val getShipClasses = GetShipClasses(shipClassRepositoryMock)
    val shipClassesLiveData = getShipClasses.buildUseCase(Unit)

    shipClassesLiveData.observeForever {
      Assert.assertEquals(2, it.data!!.size)
    }
  }
}