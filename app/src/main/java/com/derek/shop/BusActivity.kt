package com.derek.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.derek.shop.Model.Bus
import com.derek.shop.api.BusService
import kotlinx.android.synthetic.main.activity_bus.*
import kotlinx.android.synthetic.main.row_bus.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BusActivity : AppCompatActivity() {

  var busesInfo: List<Bus>? = null
  val retrofit = Retrofit.Builder()
    .baseUrl("https://data.tycg.gov.tw/opendata/datalist/datasetMeta/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_bus)

    doAsync {
      val busService = retrofit.create(BusService::class.java)
      val busListData = busService.listBuses()
        .execute()
        .body()

      busesInfo = busListData?.datas

      uiThread {
        bus_recyclerview.layoutManager = LinearLayoutManager(this@BusActivity)
        bus_recyclerview.setHasFixedSize(true)
        bus_recyclerview.adapter = BusAdapter()
      }
    }
  }

  inner class BusAdapter: RecyclerView.Adapter<BusHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusHolder {
      val view = LayoutInflater.from(parent.context).inflate(R.layout.row_bus, parent, false)
      return BusHolder(view)
    }

    override fun getItemCount(): Int {
      return busesInfo?.size?: 0
    }

    override fun onBindViewHolder(holder: BusHolder, position: Int) {
      val bus = busesInfo?.get(position)
      holder.bindView(bus!!)
    }

  }

  inner class BusHolder(view: View): RecyclerView.ViewHolder(view) {
    val busID = view.bus_id
    val routeID = view.route_id
    val speed = view.speed

    fun bindView(bus: Bus) {
      busID.text = bus.BusID
      routeID.text = bus.RouteID
      speed.text = bus.Speed
    }
  }
}
