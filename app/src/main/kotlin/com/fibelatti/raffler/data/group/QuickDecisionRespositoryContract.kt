package com.fibelatti.raffler.data.group

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.fibelatti.raffler.data.models.QuickDecision
import io.reactivex.Single

@Dao
interface QuickDecisionRespositoryContract {
    @Query("select * from " + QuickDecision.TABLE_NAME)
    fun fetchAllQuickDecisions(): Single<List<QuickDecision>>
}