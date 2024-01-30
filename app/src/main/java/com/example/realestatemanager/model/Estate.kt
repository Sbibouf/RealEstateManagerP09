package com.example.realestatemanager.model


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Estate")
data class Estate(

    var type : String? = "",
    var price : String? = "",
    var size : String? = "",
    var numberOfRooms : String? = "",
    var numberOfBedrooms : String? = "",
    var numberOfBathrooms : String? = "",
    var description : String? = "",
    var address : String? = "",
    var city : String? = "",
    var latitude : String? = "",
    var longitude : String? = "",
    var soldState : Boolean? = false,
    var entryDate : String? = "",
    var soldDate : String? = "",
    var agent : String? = "",
    var school : Boolean? = false,
    var shops : Boolean? = false,
    var parc : Boolean? = false,
    var hospital : Boolean? = false,
    var restaurant : Boolean? = false,
    var sport : Boolean? = false
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
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
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
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeValue(soldState)
        parcel.writeString(entryDate)
        parcel.writeString(soldDate)
        parcel.writeString(agent)
        parcel.writeValue(school)
        parcel.writeValue(shops)
        parcel.writeValue(parc)
        parcel.writeValue(hospital)
        parcel.writeValue(restaurant)
        parcel.writeValue(sport)
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