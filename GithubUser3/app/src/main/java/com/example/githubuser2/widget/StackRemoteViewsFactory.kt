package com.example.githubuser2.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.example.githubuser2.R
import com.example.githubuser2.db.MappingHelper
import com.example.githubuser2.db.UserContract
import com.example.githubuser2.favorite.FavUsers

internal class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory{

    private var mWidgetItems = ArrayList<Bitmap>()
    private var fav = ArrayList<FavUsers>()

    override fun onCreate() {
        val cursor: Cursor? = mContext.contentResolver.query(
                UserContract.UserColumns.CONTENT_URI,
                null, null, null, null)

        fav = MappingHelper.mapCursorToArrayList(cursor)
    }

    override fun onDataSetChanged() {
        for (item: FavUsers in fav){
            val avatar :Bitmap = Glide.with(mContext)
                    .asBitmap()
                    .load(item.photo)
                    .placeholder(R.drawable.github)
                    .error(R.drawable.github)
                    .submit(512, 512)
                    .get()
            mWidgetItems.add(avatar)
        }
    }

    override fun onDestroy() {
    }

    override fun getCount(): Int = fav.size

    override fun getViewAt(position: Int): RemoteViews {
        val stack = RemoteViews(mContext.packageName, R.layout.widget_item)
        stack.setImageViewBitmap(R.id.imageView, mWidgetItems[position])

        val extra :Bundle = bundleOf(FavUsersWidget.EXTRA_ITEM to position)
        val fillIntent = Intent()
        fillIntent.putExtras(extra)

        stack.setOnClickFillInIntent(R.id.imageView, fillIntent)
        return stack
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}