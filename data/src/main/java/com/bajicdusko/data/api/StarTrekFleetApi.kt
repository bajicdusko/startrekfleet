package com.bajicdusko.data.api

import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
interface StarTrekFleetApi {

  @GET("star_trek_fleet_ship_classes.json")
  fun getShipClasses(): Call<List<ShipClass>>

  @GET("star_trek_fleet.json")
  fun getWholeCollection() : Call<Map<String, List<Ship>>>
}