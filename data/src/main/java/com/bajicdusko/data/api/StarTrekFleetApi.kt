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
const val shipClassesUrl = "https://gist.githubusercontent.com/bajicdusko/b9488eb0925fea469b706269a5a286d3/raw/0cf5b020efbcb6acb23dae994c222f2af2e490ac/startrek_fleet_ship_classes.json"
const val wholeCollectionUrl = "https://gist.githubusercontent.com/bajicdusko/287e34247bdc67b0352d02d2038f209d/raw/5807e926b9bb2184549dcdad5d46701983eeecc4/startrek_fleet.json"

interface StarTrekFleetApi {

  @GET
  fun getShipClasses(@Url url: String = shipClassesUrl): Call<List<ShipClass>>

  @GET
  fun getWholeCollection(@Url url: String = wholeCollectionUrl) : Call<Map<String, List<Ship>>>
}