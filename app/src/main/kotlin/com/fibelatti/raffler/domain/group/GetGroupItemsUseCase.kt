package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.GroupItem
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import io.reactivex.Single
import com.fibelatti.raffler.presentation.models.GroupItem as PresentationModel

class GetGroupItemsUseCase(private val database: AppDatabase) {
    fun getAllGroupItemsByGroupId(groupId: Long): Single<List<PresentationModel>> =
        database.getGroupItemRepository()
            .getAllGroupItemsByGroupId(groupId)
            .onErrorReturn { emptyList() }
            .flattenAsObservable<GroupItem> { list -> list }
            .map { groupItem ->
                GroupItemMapper.toPresentationModel(groupItem)
            }
            .toList()
}
