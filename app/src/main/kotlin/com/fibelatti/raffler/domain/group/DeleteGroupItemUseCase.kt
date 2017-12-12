package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.GroupItem
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import io.reactivex.Completable
import javax.inject.Inject

class DeleteGroupItemUseCase @Inject constructor(private val database: AppDatabase) {
    fun deleteGroupItems(groupItems: List<GroupItem>): Completable {
        database.getGroupItemRepository()
                .deleteGroupItemsById(groupItems.map { groupItem -> groupItem.id })

        return Completable.complete()
    }
}
