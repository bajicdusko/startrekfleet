package com.bajicdusko.startrekfleet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bajicdusko.data.interactor.GetShipClasses

/**
 * Created by Dusko Bajic on 24.07.18.
 * GitHub @bajicdusko
 */
class ViewModelFactory : ViewModelProvider.Factory {

  companion object {
    lateinit var shipClasses: GetShipClasses
  }

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return when {
      modelClass.isAssignableFrom(ShipClassesViewModel::class.java) -> ShipClassesViewModel(shipClasses)
      else -> throw IllegalArgumentException("Unknown ViewModel ${modelClass.name}")
    } as T
  }
}