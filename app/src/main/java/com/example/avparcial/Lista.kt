import android.os.Parcel
import android.os.Parcelable
import com.example.avparcial.Itens

data class Lista(
    val id: String,
    val nome: String,
    val imagemUrl: String?,
    var itens_da_lista: ArrayList<Itens>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.createTypedArrayList(Itens.CREATOR)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nome)
        parcel.writeString(imagemUrl)
        parcel.writeTypedList(itens_da_lista)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lista> {
        override fun createFromParcel(parcel: Parcel): Lista {
            return Lista(parcel)
        }

        override fun newArray(size: Int): Array<Lista?> {
            return arrayOfNulls(size)
        }
    }
}
