package com.fibelatti.raffler.data.group

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface GroupItemRepositoryContract {
    @Query("select * from " + GroupItem.TABLE_NAME +
            " where " + GroupItem.COLUMN_GROUP_ID + " = :groupId")
    fun getAllGroupItemsByGroupId(groupId: Long): Single<List<GroupItem>>

    @Query("delete from " + GroupItem.TABLE_NAME + " where " + GroupItem.COLUMN_ID + " in(:groupItemId)")
    fun deleteGroupItemsById(groupItemIds: List<Long>): Single<Boolean>
}
