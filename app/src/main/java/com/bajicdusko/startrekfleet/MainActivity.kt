package com.bajicdusko.startrekfleet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.room.Room
import com.bajicdusko.androiddomain.ResponseWrapper
import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.androiddomain.repository.ShipClassRepository
import com.bajicdusko.data.api.StarTrekFleetApi
import com.bajicdusko.data.db.FleetDatabase
import com.bajicdusko.data.interactor.GetShipClasses
import com.bajicdusko.data.repository.ApiShipClassRepository
import com.bajicdusko.data.repository.DbShipClassRepository
import com.bajicdusko.startrekfleet.databinding.ActivityMainBinding
import com.bajicdusko.startrekfleet.viewmodel.ShipClassesViewModel
import com.bajicdusko.startrekfleet.viewmodel.ViewModelFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

  val tag = this::class.java.simpleName
  lateinit var shipClassesViewModel: ShipClassesViewModel
  lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    initGetShipClasses()
    shipClassesViewModel = ViewModelFactory().create(ShipClassesViewModel::class.java)
    lifecycle.addObserver(shipClassesViewModel)

    observeChanges()
    binding.temp = "Huhu"
  }

  private fun initGetShipClasses() {

    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://test.com")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val api = retrofit.create(StarTrekFleetApi::class.java)

    val fleetDatabase = Room.databaseBuilder(this, FleetDatabase::class.java, "fleet").build()
    val dbShipClassRepository = DbShipClassRepository(fleetDatabase.shipClasssDao())
    val shipClassesRepository: ShipClassRepository = ApiShipClassRepository(api, dbShipClassRepository)
    ViewModelFactory.shipClasses = GetShipClasses(shipClassesRepository)
  }

  private fun observeChanges() {
    shipClassesViewModel.onLoad().observe(this, Observer { renderResult(it) })
  }

  private fun renderResult(result: ResponseWrapper<List<ShipClass>>?) {
    result?.let {
      if (it.error != null){
        renderError(it.error!!)
      } else {
        renderData(it.data)
      }
    }
  }

  private fun renderData(data: List<ShipClass>?) {
    Log.d(tag, data.toString())
  }

  private fun renderError(error: Throwable) {
    Log.e(tag, "On ship classes loading", error)
  }

  override fun onDestroy() {
    super.onDestroy()
    lifecycle.removeObserver(shipClassesViewModel)
  }
}