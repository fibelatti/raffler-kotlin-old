package com.fibelatti.raffler.presentation.models

import android.os.Parcel
import android.os.Parcelable
import com.fibelatti.raffler.core.extensions.createParcel

data class Group(val id: Long, var name: String, var items: List<GroupItem>) : Parcelable {
    val groupItemNames: List<String>
        get() = items.map { item -> item.name }


    val concatenatedGroupItemNames: String
        get() = items.joinToString(",") { item -> item.name }

    val itemsCount: Int
        get() = this.items.size

    val selectedItems: List<GroupItem>
        get() = items.filter { item -> item.isIncluded }

    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as Long,
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

    companion object {
        val CREATOR = createParcel { Group(it) }
    }
}


