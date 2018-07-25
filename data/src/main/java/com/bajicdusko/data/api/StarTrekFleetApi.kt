package com.bajicdusko.data.api

import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.androiddomain.model.ShipClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Dusko Bajic on 22.07.18.
 * GitHub @bajicdusko
 */
const val shipClassesUrl = "https://gist.githubusercontent.com/bajicdusko/b9488eb0925fea469b706269a5a286d3/raw/136dc67fbe845b091a862c583c027ff8b1eb3fe3/startrek_fleet_ship_classes.json"
const val wholeCollectionUrl = "https://gist.githubusercontent.com/bajicdusko/287e34247bdc67b0352d02d2038f209d/raw/49d0b33e2118220bb88d72e0231560115e22fa93/startrek_fleet.json"

interface StarTrekFleetApi {

  @GET
  fun getShipClasses(@Url url: String = shipClassesUrl): Call<List<ShipClass>>

  @GET
  fun getWholeCollection(@Url url: String = wholeCollectionUrl) : Call<Map<String, List<Ship>>>
}