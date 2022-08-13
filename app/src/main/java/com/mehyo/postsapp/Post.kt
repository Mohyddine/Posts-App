package com.mehyo.postsapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    val userId: Int? = null,
    val id: Int? = null,
    val title: String? = null,
    val body: String? = null
) : Parcelable
