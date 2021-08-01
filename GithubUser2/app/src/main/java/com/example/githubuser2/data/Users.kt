package com.example.githubuser2.data

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Users(
        var photo: String?,
        var name: String?,
        var user: String?,
        var company: String?,
        var loc: String?,
        var following: String?,
        var repository: String?,
        var followers: String?
): Parcelable