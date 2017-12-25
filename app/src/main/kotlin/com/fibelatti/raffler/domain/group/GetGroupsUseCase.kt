package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.GroupWithItems
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import io.reactivex.Single
import javax.inject.Inject
import com.fibelatti.raffler.presentation.models.Group as PresentationModel

class GetGroupsUseCase @Inject constructor(private val database: AppDatabase) {
    fun getAllGroups(): Single<List<PresentationModel>> =
            database.getGroupRepository()
                    .getAllGroups()
                    .onErrorReturn { emptyList() }
                    .flattenAsObservable<GroupWithItems> { list -> list }
                    .map { group ->
                        GroupMapper.toPresentationModel(group)
                    }
                    .toList()

    fun getGroupById(id: Long): Single<PresentationModel> =
            database.getGroupRepository()
                    .getGroupById(id)
                    .map { group ->
                        GroupMapper.toPresentationModel(group)
                    }
}
