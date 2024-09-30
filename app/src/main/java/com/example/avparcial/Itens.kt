package com.example.avparcial

import android.os.Parcel
import android.os.Parcelable

data class Itens(
    val icone: Int?,
    val nome_item: String,
    val quantidade: String,
    val unidade: String,
    val categoria: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(icone)
        parcel.writeString(nome_item)
        parcel.writeString(quantidade)
        parcel.writeString(unidade)
        parcel.writeString(categoria)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Itens> {
        override fun createFromParcel(parcel: Parcel): Itens {
            return Itens(parcel)
        }

        override fun newArray(size: Int): Array<Itens?> {
            return arrayOfNulls(size)
        }
    }
}
