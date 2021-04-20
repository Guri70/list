package com.app.listshort

interface OnActionListener<T> {
    fun notify(model: T, position: Int)
}