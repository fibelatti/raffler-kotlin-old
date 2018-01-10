package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.presentation.models.GroupItem
import io.reactivex.Completable

class DeleteGroupItemUseCase(private val database: AppDatabase) {
    fun deleteGroupItems(groupItems: List<GroupItem>): Completable {
        database.getGroupItemRepository()
            .deleteGroupItemsById(groupItems.map { groupItem -> groupItem.id })

        return Completable.complete()
    }
}
