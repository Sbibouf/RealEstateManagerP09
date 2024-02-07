package com.example.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.example.realestatemanager.data.local.database.EstateDatabase
import com.example.realestatemanager.di.EstateContentProviderEntryPoint
import com.example.realestatemanager.model.Estate
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class EstateContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.example.realestatemanager.provider"
        val URI_ESTATE: Uri = Uri.parse("content://$AUTHORITY/${Estate::class.java.simpleName}")
    }
    @Inject
    lateinit var estateDatabase: EstateDatabase

    override fun onCreate(): Boolean {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            context!!.applicationContext,
            EstateContentProviderEntryPoint::class.java
        )
        estateDatabase = hiltEntryPoint.getEstateDatabase()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor {
        context?.let { ctx ->
            val estateId = ContentUris.parseId(uri)
            val cursor = estateDatabase.libraryDao().getEstateWithCursor(estateId)
            cursor.setNotificationUri(ctx.contentResolver, uri)
            return cursor
        }
        throw IllegalArgumentException("Failed to query row for uri $uri")
    }


    override fun getType(uri: Uri): String {
        return "vnd.android.cursor.item/$AUTHORITY.${Estate::class.java.simpleName}"
    }

    override fun insert(uri: Uri,contentValues: ContentValues?): Uri? {
        context?.let { ctx ->
            contentValues?.let {
                val id = estateDatabase.libraryDao().insert(Estate.fromContentValues(it))
                if (id != 0L) {
                    ctx.contentResolver.notifyChange(uri, null)
                    return ContentUris.withAppendedId(uri, id)
                }
            }
        }
        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        context?.let { ctx ->
            val count = estateDatabase.libraryDao().deleteEstate(ContentUris.parseId(uri))
            ctx.contentResolver.notifyChange(uri, null)
            return count
        }
        throw IllegalArgumentException("Failed to delete row into $uri")
    }

    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        s: String?,
        strings: Array<String>?
    ): Int {
        context?.let { ctx ->
            contentValues?.let {
                val count = estateDatabase.libraryDao().updateEstate(Estate.fromContentValues(it))
                ctx.contentResolver.notifyChange(uri, null)
                return count
            }
        }
        throw IllegalArgumentException("Failed to update row into $uri")
    }
}