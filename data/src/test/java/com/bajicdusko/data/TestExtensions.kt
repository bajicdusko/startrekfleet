package com.bajicdusko.data

import org.mockito.Mockito

/**
 * Created by Dusko Bajic on 06.09.18.
 * GitHub @bajicdusko
 */

@Suppress("UNCHECKED_CAST")
fun <T> anyNotNull(): T {
  Mockito.any<T>()
  return null as T
}

inline fun <reified T : Any> mock(): T = Mockito.mock(T::class.java)