package com.example.football.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImageWithUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}