package com.example.realestatemanager.model


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Estate")
data class Estate(

    var type : String?,
    var price : String?,
    var size : String?,
    var numberOfRooms : String?,
    var numberOfBedrooms : String?,
    var numberOfBathrooms : String?,
    var description : String?,
    var address : String?,
    var city : String?,
    var placesOfInterest : String?,
    var state : String?,
    var entryDate : String?,
    var soldDate : String?,
    var agent : String?
    ) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(type)
        parcel.writeString(price)
        parcel.writeString(size)
        parcel.writeString(numberOfRooms)
        parcel.writeString(numberOfBedrooms)
        parcel.writeString(numberOfBathrooms)
        parcel.writeString(description)
        parcel.writeString(address)
        parcel.writeString(city)
        parcel.writeString(placesOfInterest)
        parcel.writeString(state)
        parcel.writeString(entryDate)
        parcel.writeString(soldDate)
        parcel.writeString(agent)
        parcel.writeLong(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Estate> {
        override fun createFromParcel(parcel: Parcel): Estate {
            return Estate(parcel)
        }

        override fun newArray(size: Int): Array<Estate?> {
            return arrayOfNulls(size)
        }
    }


}