package com.bajicdusko.startrekfleet.ship

import com.bajicdusko.androiddomain.model.Ship
import com.bajicdusko.startrekfleet.R
import com.bajicdusko.startrekfleet.base.BaseListAdapter

class ShipsAdapter : BaseListAdapter() {

  private val ships: MutableList<Ship> = mutableListOf()

  override fun getLayoutId(): Int = R.layout.list_item_ship

  override fun getItemAt(position: Int): Any = ships[position]

  override fun getItemCount(): Int = ships.size

  fun onData(ships: List<Ship>) {
    this.ships.clear()
    this.ships.addAll(ships)
    notifyDataSetChanged()
  }

  fun onClear() {
    this.ships.clear()
    notifyDataSetChanged()
  }
}