package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.GroupItem
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import io.reactivex.Single
import javax.inject.Inject

class DeleteGroupItemUseCase @Inject constructor(private val database: AppDatabase) {
    fun deleteGroupItems(groupItems: List<GroupItem>): Single<Boolean> {
        database.getGroupItemRepository()
                .deleteGroupItemsById(groupItems.map { groupItem -> groupItem.id })

        return Single.just(true)
    }
}
