package com.fibelatti.raffler.data.group

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface GroupRepositoryContract {
    @Query("select * from " + Group.TABLE_NAME +
            " where " + Group.COLUMN_ID + " = :groupId ")
    fun getGroupById(groupId: Long): Single<Group>

    @Query("select * from " + Group.TABLE_NAME)
    fun getAllGroups(): Single<List<Group>>

    @Insert(onConflict = REPLACE)
    fun saveGroup(group: Group): Single<Boolean>

    @Query("delete from " + Group.TABLE_NAME + " where " + Group.COLUMN_ID + " = :groupId")
    fun deleteGroupById(groupId: Long): Single<Boolean>
}
