package com.bajicdusko.startrekfleet.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseListAdapter : RecyclerView.Adapter<BaseViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
    val binding: ViewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(parent.context), getLayoutId(), parent, false)
    return BaseViewHolder(binding)
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
    val item: Any = getItemAt(position)
    holder.bind(item, listeners)
  }

  open val listeners: Any? = null

  abstract fun getLayoutId(): Int

  abstract fun getItemAt(position: Int): Any
}