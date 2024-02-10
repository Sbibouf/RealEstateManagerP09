package com.example.realestatemanager.model


import android.content.ContentValues
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
    var sport: Boolean? = false,
    var entryDateMilli : Long? = 0L
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
        sport = parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        entryDateMilli = parcel.readValue(Long::class.java.classLoader) as? Long
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
        parcel.writeValue(entryDateMilli)
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

        fun fromContentValues(values : ContentValues) : Estate{
            val estate = Estate()
            if(values.containsKey("type")) estate.type = values.getAsString("type")
            if(values.containsKey("price")) estate.price = values.getAsString("price")
            if(values.containsKey("size")) estate.size = values.getAsString("size")
            if(values.containsKey("numberOfRooms")) estate.numberOfRooms = values.getAsString("numberOfRooms")
            if(values.containsKey("numberOfBedrooms")) estate.numberOfBedrooms = values.getAsString("numberOfBedrooms")
            if(values.containsKey("numberOfBathrooms")) estate.numberOfBathrooms = values.getAsString("numberOfBathrooms")
            if(values.containsKey("description")) estate.description = values.getAsString("description")
            if(values.containsKey("address")) estate.address = values.getAsString("address")
            if(values.containsKey("city")) estate.city = values.getAsString("city")
            if(values.containsKey("latitude")) estate.latitude = values.getAsString("latitude")
            if(values.containsKey("longitude")) estate.longitude = values.getAsString("longitude")
            if(values.containsKey("soldState")) estate.soldState = values.getAsBoolean("soldState")
            if(values.containsKey("entryDate")) estate.entryDate = values.getAsString("entryDate")
            if(values.containsKey("soldDate")) estate.soldDate = values.getAsString("soldDate")
            if(values.containsKey("agent")) estate.agent = values.getAsString("agent")
            if(values.containsKey("school")) estate.school = values.getAsBoolean("school")
            if(values.containsKey("shops")) estate.shops = values.getAsBoolean("shops")
            if(values.containsKey("parc")) estate.parc = values.getAsBoolean("parc")
            if(values.containsKey("hospital")) estate.hospital = values.getAsBoolean("hospital")
            if(values.containsKey("restaurant")) estate.restaurant = values.getAsBoolean("restaurant")
            if(values.containsKey("sport")) estate.sport = values.getAsBoolean("sport")
            if(values.containsKey("entryDateMilli")) estate.sport = values.getAsBoolean("entryDateMilli")
            if(values.containsKey("id")) estate.id = values.getAsLong("id")

            return estate
        }
    }




}