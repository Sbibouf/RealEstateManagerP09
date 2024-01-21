package com.example.realestatemanager.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "EstatePhoto", foreignKeys = [ForeignKey(entity = Estate::class, parentColumns = ["id"], childColumns = ["estateId"])], indices = [Index("estateId")])
data class EstatePhoto(
    var estateId : Long,
                       var uri : String?,
                       var name : String?) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id : Long = 0

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(estateId)
        parcel.writeString(uri)
        parcel.writeString(name)
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



