package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import io.reactivex.Single
import javax.inject.Inject
import com.fibelatti.raffler.presentation.models.Group as PresentationModel

class SaveGroupUseCase @Inject constructor(private val database: AppDatabase) {
    fun saveGroup(group: PresentationModel): Single<Boolean> {
        return database.getGroupRepository()
                .saveGroup(GroupMapper.toDataModel(group))
    }
}
