package com.fibelatti.raffler.data.group

import android.arch.persistence.room.*
import io.reactivex.Single

@Dao
interface GroupItemRepositoryContract {
    @Query("select * from " + GroupItem.TABLE_NAME +
            " where " + GroupItem.COLUMN_GROUP_ID + " =:groupId")
    fun fetchAllGroupItemsByGroupId(groupId: Long): Single<List<GroupItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveGroupItems(vararg groupItem: GroupItem): Single<Boolean>

    @Delete
    fun deleteGroupItems(vararg groupItem: GroupItem): Single<Boolean>
}