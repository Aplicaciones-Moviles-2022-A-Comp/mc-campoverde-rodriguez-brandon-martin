package com.example.proyecto_campoverdebrandon

import android.os.Parcel
import android.os.Parcelable

class CarritoData(
    var nombre:String?,
    var cantidad:Long,
    var precio:Double
) :Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readLong(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeLong(cantidad)
        parcel.writeDouble(precio)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CarritoData> {
        override fun createFromParcel(parcel: Parcel): CarritoData {
            return CarritoData(parcel)
        }

        override fun newArray(size: Int): Array<CarritoData?> {
            return arrayOfNulls(size)
        }
    }
}