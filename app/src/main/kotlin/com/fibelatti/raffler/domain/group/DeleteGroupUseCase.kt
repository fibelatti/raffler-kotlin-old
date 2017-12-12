package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.presentation.models.Group
import io.reactivex.Completable
import javax.inject.Inject

class DeleteGroupUseCase @Inject constructor(private val database: AppDatabase) {
    fun deleteGroup(group: Group): Completable {
         database.getGroupRepository()
                .deleteGroupById(group.id)

        return Completable.complete()
    }
}
