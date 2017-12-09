package com.fibelatti.raffler.data.models

import android.os.Parcel
import android.os.Parcelable

data class Group(
        val id: Long?,
        val name: String?,
        val items: MutableList<GroupItem>
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            mutableListOf<GroupItem>().apply {
                parcel.readList(this, GroupItem::class.java.classLoader)
            })

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeList(items)
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Group> {
        override fun createFromParcel(parcel: Parcel) = Group(parcel)

        override fun newArray(size: Int): Array<Group?> = arrayOfNulls(size)
    }
}
