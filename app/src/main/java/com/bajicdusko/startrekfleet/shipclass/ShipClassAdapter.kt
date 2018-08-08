package com.bajicdusko.startrekfleet.shipclass

import com.bajicdusko.androiddomain.model.ShipClass
import com.bajicdusko.startrekfleet.R
import com.bajicdusko.startrekfleet.base.BaseListAdapter

open class ShipClassAdapter : BaseListAdapter() {

  var listener: Any? = null

  private val shipClasses: MutableList<ShipClass> = mutableListOf()

  override fun getLayoutId(): Int = R.layout.list_item_shipclass

  override fun getItemAt(position: Int): Any = shipClasses[position]

  override fun getItemCount(): Int = shipClasses.size

  fun loadData(shipClasses: List<ShipClass>) {
    this.shipClasses.clear()
    this.shipClasses.addAll(shipClasses)
    notifyDataSetChanged()
  }

  override val listeners: Any?
    get() = listener

  fun clearData() {
    shipClasses.clear()
    notifyDataSetChanged()
  }
}