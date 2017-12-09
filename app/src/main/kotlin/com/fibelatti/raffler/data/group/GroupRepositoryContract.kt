package com.fibelatti.raffler.data.group

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.fibelatti.raffler.data.models.Group
import io.reactivex.Single

@Dao
interface GroupRepositoryContract {
    @Query("select * from " + Group.TABLE_NAME +
            " where " + Group.COLUMN_ID + " = :groupId ")
    fun fetchGroupById(groupId: Long): Single<Group>

    @Query("select * from " + Group.TABLE_NAME)
    fun fetchAllGroups(): Single<List<Group>>

    @Insert(onConflict = REPLACE)
    fun saveGroup(group: Group): Single<Boolean>

    @Delete
    fun deleteGroup(group: Group): Single<Boolean>

    @Delete
    fun deleteGroups(vararg groupList: Group): Single<Boolean>
}