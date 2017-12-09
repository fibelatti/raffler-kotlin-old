package com.fibelatti.raffler.data.group

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.fibelatti.raffler.core.extensions.createParcel
import com.fibelatti.raffler.data.group.Group.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME,
        foreignKeys = [
            ForeignKey(entity = Group::class, parentColumns = [Group.COLUMN_ID], childColumns = [(GroupItem.COLUMN_GROUP_ID)], onDelete = CASCADE)])
data class GroupItem(
        @ColumnInfo(name = COLUMN_ID)
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        @ColumnInfo(name = COLUMN_GROUP_ID)
        val groupId: Long,
        @ColumnInfo(name = COLUMN_NAME)
        val name: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Long::class.java.classLoader) as Long,
            parcel.readValue(Long::class.java.classLoader) as Long,
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(groupId)
        parcel.writeString(name)
    }

    override fun describeContents() = 0

    companion object {
        val CREATOR = createParcel { GroupItem(it) }

        const val TABLE_NAME = "group_items"
        const val COLUMN_ID = "_id"
        const val COLUMN_GROUP_ID = "group_id"
        const val COLUMN_NAME = "item_name"
    }
}
