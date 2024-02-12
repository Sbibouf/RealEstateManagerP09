package com.example.realestatemanager.model

import android.os.Parcel
import android.os.Parcelable

data class SearchCriteria(
    var type : String? = "Tous les biens immobiliers",
    var minPrice : Int = 0,
    var maxPrice : Int = 1000000000,
    var minSize : Int = 0,
    var maxSize : Int = 10000,
    var minNumberOfRooms : Int = 0,
    var maxNumberOfRooms : Int = 25,
    var minNumberOfBedrooms : Int = 0,
    var maxNumberOfBedrooms : Int = 15,
    var minNumberOfBathrooms : Int = 0,
    var maxNumberOfBathrooms : Int = 15,
    var school : Boolean? = false,
    var shops : Boolean? = false,
    var parc : Boolean? = false,
    var hospital : Boolean? = false,
    var restaurant : Boolean? = false,
    var sport : Boolean? = false,
    var entryDate : String? = "",
    var entryDateMilli : Long? = 0,
    var soldState : Boolean? = false,
    var photoCount : Int = 0

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeInt(minPrice)
        parcel.writeInt(maxPrice)
        parcel.writeInt(minSize)
        parcel.writeInt(maxSize)
        parcel.writeInt(minNumberOfRooms)
        parcel.writeInt(maxNumberOfRooms)
        parcel.writeInt(minNumberOfBedrooms)
        parcel.writeInt(maxNumberOfBedrooms)
        parcel.writeInt(minNumberOfBathrooms)
        parcel.writeInt(maxNumberOfBathrooms)
        parcel.writeValue(school)
        parcel.writeValue(shops)
        parcel.writeValue(parc)
        parcel.writeValue(hospital)
        parcel.writeValue(restaurant)
        parcel.writeValue(sport)
        parcel.writeString(entryDate)
        parcel.writeValue(entryDateMilli)
        parcel.writeValue(soldState)
        parcel.writeInt(photoCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchCriteria> {
        override fun createFromParcel(parcel: Parcel): SearchCriteria {
            return SearchCriteria(parcel)
        }

        override fun newArray(size: Int): Array<SearchCriteria?> {
            return arrayOfNulls(size)
        }
    }


}