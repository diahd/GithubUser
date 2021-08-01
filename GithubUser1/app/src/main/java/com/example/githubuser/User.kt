package com.example.githubuser

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var photo: Int,
    var name: String?,
    var user: String?,
    var company: String?,
    var loc: String?,
    var following: String?,
    var repository: String?,
    var followers: String?

    ): Parcelable