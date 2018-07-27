package com.bajicdusko.startrekfleet.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bajicdusko.startrekfleet.R

/**
 * Created by Dusko Bajic on 26.07.18.
 * GitHub @bajicdusko
 */
class SimpleDividerItemDecoration(context: Context) : RecyclerView.ItemDecoration() {

  private var dividerDrawable: Drawable
  private var paddingLeftRight: Int = 0

  init {
    dividerDrawable = ContextCompat.getDrawable(context, R.drawable.line_divider)!!
  }

  constructor(
      context: Context,
      @DrawableRes dividerDrawable: Int,
      @DimenRes paddingLeftRight: Int
  ) : this(context) {
    this.dividerDrawable = ContextCompat.getDrawable(context, dividerDrawable)!!
    this.paddingLeftRight = context.resources.getDimension(paddingLeftRight).toInt()
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

    val left: Int
    val right: Int
    val width = parent.width

    if (paddingLeftRight == 0) {
      left = parent.paddingLeft
      right = parent.width - parent.paddingRight
    } else {
      left = paddingLeftRight
      right = width - paddingLeftRight
    }

    val childCount = parent.childCount
    for (i in 0 until childCount) {
      val child: View = parent.getChildAt(i)

      var params: RecyclerView.LayoutParams
      params = child.layoutParams as RecyclerView.LayoutParams
      val top = child.bottom + params.bottomMargin
      val bottom = top + (dividerDrawable.intrinsicHeight)

      dividerDrawable.setBounds(left, top, right, bottom)
      dividerDrawable.draw(c)
    }
  }
}