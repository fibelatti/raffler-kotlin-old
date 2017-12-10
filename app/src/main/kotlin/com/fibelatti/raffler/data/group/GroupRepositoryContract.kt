package com.fibelatti.raffler.data.group

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import io.reactivex.Single

@Dao
interface GroupRepositoryContract {
    @Transaction
    @Query("select * from " + Group.TABLE_NAME +
            " where " + Group.COLUMN_ID + " = :groupId ")
    fun getGroupById(groupId: Long): Single<GroupWithItems>

    @Transaction
    @Query("select * from " + Group.TABLE_NAME)
    fun getAllGroups(): Single<List<GroupWithItems>>

    @Insert(onConflict = REPLACE)
    fun saveGroup(group: Group)

    @Query("delete from " + Group.TABLE_NAME + " where " + Group.COLUMN_ID + " = :groupId")
    fun deleteGroupById(groupId: Long)
}
