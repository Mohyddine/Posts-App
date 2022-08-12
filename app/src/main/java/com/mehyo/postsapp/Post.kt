package com.mehyo.postsapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(val title: String, val desc: String) : Parcelable
