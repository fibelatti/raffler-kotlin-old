package com.fibelatti.raffler.data.group

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.fibelatti.raffler.data.group.GroupItem.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME,
    foreignKeys = [
        ForeignKey(entity = Group::class, parentColumns = [Group.COLUMN_ID], childColumns = [(GroupItem.COLUMN_GROUP_ID)], onDelete = CASCADE)])
data class GroupItem(
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = COLUMN_GROUP_ID)
    val groupId: Long,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String
) {
    @Ignore constructor() : this(0, 0, "")

    companion object {
        const val TABLE_NAME = "group_items"
        const val COLUMN_ID = "_id"
        const val COLUMN_GROUP_ID = "group_id"
        const val COLUMN_NAME = "item_name"
    }
}
