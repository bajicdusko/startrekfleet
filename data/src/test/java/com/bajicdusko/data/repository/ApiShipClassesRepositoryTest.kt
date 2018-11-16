package com.bajicdusko.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.data.anyNotNull
import com.bajicdusko.data.api.StarTrekFleetApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * Created by Dusko Bajic on 25.07.18.
 * GitHub @bajicdusko
 */

@RunWith(MockitoJUnitRunner::class)
class ApiShipClassesRepositoryTest {

  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  private val shipClass: ShipClass = ShipClass("Akira")
  @Mock private lateinit var starTrekFleetApi: StarTrekFleetApi
  @Mock private lateinit var dbShipClassRepositoryMock: DbShipClassRepository
  @Mock private lateinit var mockedCall: Call<List<ShipClass>>

  @Before
  fun setUp() {

    doReturn(mockedCall).`when`(starTrekFleetApi).getShipClasses(anyString())

    val wholeCollection: List<ShipClass> = listOf(
        ShipClass("Akira"),
        ShipClass("Archer")
    )

    @Suppress("UNCHECKED_CAST")
    doAnswer {
      val callback: Callback<List<ShipClass>> = it.getArgument(0)
      callback.onResponse(mockedCall, Response.success(wholeCollection))
    }.`when`(mockedCall).enqueue(anyNotNull())

    `when`(dbShipClassRepositoryMock.getEntriesCount()).thenReturn(MutableLiveData<Int>().also {
      it.value = 0
    })

    Mockito.doAnswer {
      val afterInsert: () -> Unit = it.getArgument(1)
      afterInsert()
    }.`when`(dbShipClassRepositoryMock).insert(anyList(), anyNotNull())
  }

  @Test
  @Suppress("UNCHECKED_CAST")
  fun apiShipsRepository_givenThatDatabaseIsEmpty_shouldGetAllShipClasses() {

    /**
     * Method call
     */
    val apiShipsRepository = ApiShipClassRepository(starTrekFleetApi, dbShipClassRepositoryMock)
    val shipsPerShipClassLiveData = apiShipsRepository.getShipClasses()

    /**
     * Assertion
     */
    shipsPerShipClassLiveData.observeForever {
      Assert.assertEquals(2, it.data!!.size)
      Assert.assertEquals("Akira", it.data!![0].name)
      Assert.assertEquals("Archer", it.data!![1].name)
    }
  }
}