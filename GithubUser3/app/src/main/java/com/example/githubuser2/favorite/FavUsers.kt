package com.example.githubuser2.favorite

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FavUsers (
    var photo: String?,
    var name: String?,
    var user: String?,
    var company: String?,
    var loc: String?,
    var repository: String?,
    var following: String?,
    var followers: String?
    ): Parcelable