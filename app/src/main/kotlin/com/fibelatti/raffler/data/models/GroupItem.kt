package com.fibelatti.raffler.data.models

import android.os.Parcel
import android.os.Parcelable

data class GroupItem(
        val id: Long?,
        val groupId: Long?,
        val name: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(groupId)
        parcel.writeString(name)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<GroupItem> {
        override fun createFromParcel(parcel: Parcel) = GroupItem(parcel)

        override fun newArray(size: Int): Array<GroupItem?> = arrayOfNulls(size)
    }
}
