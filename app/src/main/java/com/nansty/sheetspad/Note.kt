package com.nansty.sheetspad

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

//Data class pour profiter des fonctions generer par Kotlin ( toString etc.. )
// Class Note , description de nos données
//On hérite de la classe Parcelable et Serializable
data class Note(var title: String = "", var text: String = "", var filename: String = "") : Parcelable, Serializable{
   
    //implementé les methodes  Parcelables

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(text)
        parcel.writeString(filename)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        //Identification de version de note
        private val serialVersionUid: Long = 79797979
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }
}