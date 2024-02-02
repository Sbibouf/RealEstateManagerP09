package com.example.realestatemanager.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation

data class EstateWithPhotos(
    @Embedded
    val estate: Estate?,
    @Relation(
        parentColumn = "id",
        entityColumn = "estateId"
    )
    val photos: List<EstatePhoto>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        estate = parcel.readParcelable(Estate::class.java.classLoader),
        photos = parcel.createTypedArrayList(EstatePhoto)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(estate, flags)
        parcel.writeTypedList(photos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EstateWithPhotos> {
        override fun createFromParcel(parcel: Parcel): EstateWithPhotos {
            return EstateWithPhotos(parcel)
        }

        override fun newArray(size: Int): Array<EstateWithPhotos?> {
            return arrayOfNulls(size)
        }
    }
}