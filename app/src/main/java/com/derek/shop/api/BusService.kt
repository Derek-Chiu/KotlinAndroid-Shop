package com.derek.shop.api

import com.derek.shop.Model.Data
import retrofit2.Call
import retrofit2.http.GET

interface BusService {
  @GET("download?id=b3abedf0-aeae-4523-a804-6e807cbad589&rid=bf55b21a-2b7c-4ede-8048-f75420344aed")
  fun listBuses(): Call<Data>
}
