package com.bajicdusko.startrekfleet.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bajicdusko.startrekfleet.BR

class BaseViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

  fun bind(any: Any, listeners: Any?) {
    binding.setVariable(BR.any, any)

    listeners?.let {
      binding.setVariable(BR.listener, listeners)
    }

    binding.executePendingBindings()
  }
}