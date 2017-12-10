package com.fibelatti.raffler.data.quickdecision

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single

@Dao
interface QuickDecisionRepositoryContract {
    @Query("select * from " + QuickDecision.TABLE_NAME)
    fun fetchAllQuickDecisions(): Single<List<QuickDecision>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addQuickDecision(quickDecision: QuickDecision)
}
