package com.example.consumerapp.db

import android.database.Cursor
import com.example.consumerapp.favorite.FavUsers

object MappingHelper {
    fun mapCursorToArrayList(userCursor: Cursor?): ArrayList<FavUsers> {
        val favUsers = ArrayList<FavUsers>()

        userCursor?.apply{
            while (moveToNext()){
                val user = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_USERNAME))
                val name = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_NAME))
                val avatar = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_AVA_URL))
                val company = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_COMPANY))
                val location = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_LOCATION))
                val repos = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_REPOS))
                val follg = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_FOLLG))
                val folls = getString(getColumnIndexOrThrow(UserContract.UserColumns.COLUMN_NAME_FOLLS))
                favUsers.add(FavUsers(avatar, name, user, company, location, repos, follg, folls))
            }
        }
        return  favUsers
    }
}