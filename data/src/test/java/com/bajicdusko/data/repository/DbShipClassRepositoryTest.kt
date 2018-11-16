package com.bajicdusko.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.bajicdusko.data.db.ShipClassDao
import com.bajicdusko.data.db.ShipClassDb
import org.junit.Assert
import org.junit.Before
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
class DbShipClassRepositoryTest {

  @Rule
  @JvmField
  val rule: TestRule = InstantTaskExecutorRule()

  @Mock lateinit var shipClassDaoMock: ShipClassDao

  @Before fun setUp() {

    doReturn(MutableLiveData<List<ShipClassDb>>().also {
      it.value = listOf(
          ShipClassDb("Akira"),
          ShipClassDb("Archer")
      )
    }).`when`(shipClassDaoMock).getShipClasses()

    doReturn(MutableLiveData<Int>().also {
      it.value = 2
    }).`when`(shipClassDaoMock).getCount()
  }

  @Test fun dbShipClassRepository_givenThatDatabaseIsNotEmpty_shouldGetShipClasses() {
    val dbShipClassRepository = DbShipClassRepository(shipClassDaoMock)
    val shipClasses = dbShipClassRepository.getShipClasses()

    shipClasses.observeForever {
      Assert.assertEquals(2, it.data!!.size)
    }
  }

  @Test fun dbShipClassRepository_givenThatDatabaseIsNotEmpty_shouldGetEntriesCount() {
    val dbShipClassRepository = DbShipClassRepository(shipClassDaoMock)
    val shipClasses = dbShipClassRepository.getEntriesCount()

    shipClasses.observeForever {
      Assert.assertEquals(2, it)
    }
  }
}