package com.fibelatti.raffler.data.group

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.fibelatti.raffler.data.group.Group.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class Group(
    @ColumnInfo(name = COLUMN_ID)
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = COLUMN_NAME)
    val name: String) {
    @Ignore constructor() : this(0, "")

    companion object {
        const val TABLE_NAME = "groups"
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "group_name"
    }
}
