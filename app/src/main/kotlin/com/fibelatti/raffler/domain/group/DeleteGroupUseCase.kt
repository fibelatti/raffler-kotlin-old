package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.presentation.models.Group
import io.reactivex.Completable
import javax.inject.Inject

class DeleteGroupUseCase @Inject constructor(private val database: AppDatabase) {
    fun deleteGroup(group: Group): Completable {
        database.runInTransaction({
            database.getGroupRepository()
                .deleteGroupById(group.id)
            database.getQuickDecisionRepository()
                .deleteQuickDecisionById(group.id.toString())
        })

        return Completable.complete()
    }
}
