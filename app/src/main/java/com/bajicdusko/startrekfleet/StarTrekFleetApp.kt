package com.bajicdusko.startrekfleet

import android.app.Application
import androidx.room.Room
import com.bajicdusko.data.api.StarTrekFleetApi
import com.bajicdusko.data.db.FleetDatabase
import com.bajicdusko.data.db.ShipClassDao
import com.bajicdusko.data.db.ShipsDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Dusko Bajic on 24.07.18.
 * GitHub @bajicdusko
 */
open class StarTrekFleetApp : Application() {

  lateinit var starTrekFleetApi: StarTrekFleetApi
  lateinit var shipsDao: ShipsDao
  lateinit var shipClassDao: ShipClassDao

  override fun onCreate() {
    super.onCreate()

    initApi()
    initDb()
  }

  private fun initApi() {
    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://github.com/bajicdusko/startrekfleet/tree/master/data/jsons/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    starTrekFleetApi = retrofit.create(StarTrekFleetApi::class.java)
  }

  private fun initDb() {
    val fleetDatabase = Room.databaseBuilder(this, FleetDatabase::class.java, "fleet").build()
    shipsDao = fleetDatabase.shipsDao()
    shipClassDao = fleetDatabase.shipClassDao()
  }
}