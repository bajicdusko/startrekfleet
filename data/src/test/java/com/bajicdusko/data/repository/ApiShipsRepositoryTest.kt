package com.bajicdusko.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.androiddomain.model.Ship
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
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Dusko Bajic on 05.09.18.
 * GitHub @bajicdusko
 */
@RunWith(MockitoJUnitRunner::class)
class ApiShipsRepositoryTest {

  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  @Mock private lateinit var starTrekFleetApiMock: StarTrekFleetApi
  @Mock private lateinit var dbShipsRepositoryMock: DbShipsRepository
  @Mock private lateinit var mockedCall: Call<Map<String, List<Ship>>>

  private val shipClassName = "Akira"
  private val shipClassMock = ShipClass(shipClassName)

  @Before fun setUp() {

    val wholeCollection: Map<String, List<Ship>> = mapOf(
        shipClassName to listOf(
            Ship("USS Discovery", "NCC-1031", "Test ship", shipClassMock),
            Ship("USS Enterprise", "NCC-1071", "Explorer", shipClassMock)
        )
    )

    Mockito.doAnswer {
      val callback: Callback<Map<String, List<Ship>>> = it.getArgument(0)
      callback.onResponse(mockedCall, Response.success(wholeCollection))
    }.`when`(mockedCall).enqueue(anyNotNull())

    Mockito.doReturn(mockedCall).`when`(starTrekFleetApiMock).getWholeCollection(
        Mockito.anyString()
    )

    //Mocking the Database insert
    Mockito.doAnswer {
      val afterInsert: () -> Unit = it.getArgument(1)
      afterInsert()
    }.`when`(dbShipsRepositoryMock).insertAll(Mockito.anyMap(), anyNotNull())

  }

  @Test fun apiShipsRepository_givenThatDatabaseIsEmpty_shouldGetShipsPerShipClass() {

    //Mocking database entriesCount
    Mockito.doReturn(MutableLiveData<Int>().also {
      it.value = 0
    }).`when`(dbShipsRepositoryMock).getEntriesCount()

    //Test
    val apiShipsRepository = ApiShipsRepository(starTrekFleetApiMock, dbShipsRepositoryMock)
    val shipsPerShipClass = apiShipsRepository.getShipsPerShipClass(shipClassMock)

    shipsPerShipClass.observeForever {
      Assert.assertEquals(2, it.data?.size ?: 0)
    }
  }

  @Test fun apiShipsRepository_givenThatDatabaseIsEmpty_shouldGetAllShips() {

    //Test
    val apiShipsRepository = ApiShipsRepository(starTrekFleetApiMock, dbShipsRepositoryMock)
    val allShips = apiShipsRepository.getAllShips()

    allShips.observeForever {
      Assert.assertEquals(1, it.data?.size ?: 0)
    }
  }

  @Test(expected = NotImplementedError::class)
  fun apiShipsRepository_givenThatDatabaseIsEmpty_shouldGetEntriesCount() {
    val apiShipsRepository = ApiShipsRepository(starTrekFleetApiMock, dbShipsRepositoryMock)
    apiShipsRepository.getEntriesCount()
  }
}