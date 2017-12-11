package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.GroupItem
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import io.reactivex.Single
import javax.inject.Inject
import com.fibelatti.raffler.presentation.models.GroupItem as PresentationModel

class GetGroupItemsUseCase @Inject constructor(private val database: AppDatabase) {
    fun getAllGroupItemsByGroupId(groupId: Long): Single<List<PresentationModel>> {
        return database.getGroupItemRepository()
                .getAllGroupItemsByGroupId(groupId)
                .flattenAsObservable<GroupItem> { list -> list }
                .map { groupItem ->
                    GroupItemMapper.toPresentationModel(groupItem)
                }
                .toList()
    }
}