package com.fibelatti.raffler.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.fibelatti.raffler.core.extensions.createParcel
import com.fibelatti.raffler.data.models.Group.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Group(
        @ColumnInfo(name = COLUMN_ID)
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        @ColumnInfo(name = COLUMN_NAME) val name: String,
        val items: MutableList<GroupItem>
) : Parcelable {
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

        const val TABLE_NAME = "groups"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "group_name"
    }
}
