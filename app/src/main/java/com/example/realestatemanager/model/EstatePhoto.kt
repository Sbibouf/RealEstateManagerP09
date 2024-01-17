package com.example.realestatemanager.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.net.URI

@Entity(tableName = "EstatePhoto")
data class EstatePhoto(
                       var uri : String?,
                       var name : String?) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uri)
        parcel.writeString(name)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EstatePhoto> {
        override fun createFromParcel(parcel: Parcel): EstatePhoto {
            return EstatePhoto(parcel)
        }

        override fun newArray(size: Int): Array<EstatePhoto?> {
            return arrayOfNulls(size)
        }
    }
}