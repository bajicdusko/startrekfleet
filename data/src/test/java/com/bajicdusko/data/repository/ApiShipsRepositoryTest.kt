package com.bajicdusko.data.repository

import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.data.api.StarTrekFleetApi
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.data.api.wholeCollectionUrl
import com.google.gson.Gson
import okhttp3.*
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Dusko Bajic on 25.07.18.
 * GitHub @bajicdusko
 */

@RunWith(MockitoJUnitRunner::class)
class ApiShipsRepositoryTest {

  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  @Mock lateinit var shipClass: ShipClass
  @Mock lateinit var starTrekFleetApi: StarTrekFleetApi
  @Mock lateinit var dbShipsRepository: DbShipsRepository

  @Before
  fun setUp(){

    Mockito.`when`(shipClass.name).thenReturn("Akira")

    val mockedCall: Call<Map<String, List<Ship>>> = Mockito.mock(
        Call::class.java
    ) as Call<Map<String, List<Ship>>>

    Mockito.`when`(
        starTrekFleetApi.getWholeCollection(wholeCollectionUrl)
    ).thenReturn(mockedCall)

    val wholeCollection: Map<String, List<Ship>> = mapOf(
        "Akira" to listOf(
            Ship("USS Discovery", "NCC-1031", "Test ship", shipClass),
            Ship("USS Enterprise", "NCC-1071", "Explorer", shipClass)
        ),
        "Archer" to listOf(
            Ship("USS Defiant", "NCC-1031", "Test ship", shipClass)
        )
    )

    Mockito.doAnswer {
      val callback = it.getArgument<Callback<Map<String, List<Ship>>>>(0)
      callback.onResponse(mockedCall, Response.success(wholeCollection))
    }.`when`(mockedCall).enqueue(
        Mockito.any(Callback::class.java) as Callback<Map<String, List<Ship>>>?)

    Mockito.`when`(dbShipsRepository.getEntriesCount()).thenReturn(MutableLiveData<Int>().also {
      it.value = 0
    })
  }

  @Test
  @Suppress("UNCHECKED_CAST")
  fun apiShipsRepository_getShipsPerShipClassTest() {

    /**
     * Method call
     */
    val apiShipsRepository = ApiShipsRepository(starTrekFleetApi, dbShipsRepository)
    val shipsPerShipClassLiveData = apiShipsRepository.getShipsPerShipClass(shipClass)

    /**
     * Assertion
     */
    shipsPerShipClassLiveData.observeForever {
      Assert.assertEquals(it!!.data!!.size, 2)
      Assert.assertEquals(it!!.data!![1].name, "USS Enterprise")
    }
  }

  @Test
  fun apiShipsRepository_getAllShipsTest(){

    /**
     * Method call
     */
    val apiShipsRepository = ApiShipsRepository(starTrekFleetApi, dbShipsRepository)
    val allShipsLiveData = apiShipsRepository.getAllShips()

    /**
     * Assertion
     */
    Assert.assertEquals(allShipsLiveData.value!!.data!!.size, 2)
    Assert.assertEquals(allShipsLiveData.value!!.data!!["Archer"]!!.size, 1)
    Assert.assertEquals(allShipsLiveData.value!!.data!!["Archer"]!![0].name, "USS Defiant")
  }
}