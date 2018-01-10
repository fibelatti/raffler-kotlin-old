package com.fibelatti.raffler.presentation.models

import android.os.Parcel
import android.os.Parcelable
import com.fibelatti.raffler.core.extensions.createParcel

data class GroupItem(val id: Long, val name: String, val isIncluded: Boolean = true) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as Long,
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as Boolean)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeValue(isIncluded)
    }

    override fun describeContents() = 0

    companion object {
        val CREATOR = createParcel { GroupItem(it) }
    }
}
