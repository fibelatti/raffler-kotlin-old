package com.fibelatti.raffler.data.models

import android.os.Parcel
import android.os.Parcelable

data class QuickDecision(
        val key: String?,
        val name: String?,
        val values: MutableList<String>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            mutableListOf<String>().apply {
                parcel.readList(this, String::class.java.classLoader)
            })

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(key)
        parcel.writeString(name)
        parcel.writeList(values)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<QuickDecision> {
        override fun createFromParcel(parcel: Parcel) = QuickDecision(parcel)

        override fun newArray(size: Int): Array<QuickDecision?> = arrayOfNulls(size)
    }
}
