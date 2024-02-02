package com.example.realestatemanager.model


import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Estate")
data class Estate(

    var type: String? = "",
    var price: String? = "",
    var size: String? = "",
    var numberOfRooms: String? = "",
    var numberOfBedrooms: String? = "",
    var numberOfBathrooms: String? = "",
    var description: String? = "",
    var address: String? = "",
    var city: String? = "",
    var latitude: String? = "",
    var longitude: String? = "",
    var soldState: Boolean? = false,
    var entryDate: String? = "",
    var soldDate: String? = "",
    var agent: String? = "",
    var school: Boolean? = false,
    var shops: Boolean? = false,
    var parc: Boolean? = false,
    var hospital: Boolean? = false,
    var restaurant: Boolean? = false,
    var sport: Boolean? = false
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    constructor(parcel: Parcel) : this(
        type = parcel.readString(),
        price = parcel.readString(),
        size = parcel.readString(),
        numberOfRooms = parcel.readString(),
        numberOfBedrooms = parcel.readString(),
        numberOfBathrooms = parcel.readString(),
        description = parcel.readString(),
        address = parcel.readString(),
        city = parcel.readString(),
        latitude = parcel.readString(),
        longitude = parcel.readString(),
        soldState = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        entryDate = parcel.readString(),
        soldDate = parcel.readString(),
        agent = parcel.readString(),
        school = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        shops = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parc = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        hospital = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        restaurant = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        sport = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
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