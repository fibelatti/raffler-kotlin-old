package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.GroupItem
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import io.reactivex.Single
import javax.inject.Inject
import com.fibelatti.raffler.presentation.models.Group as PresentationModel

class SaveGroupUseCase @Inject constructor(private val database: AppDatabase) {
    fun saveGroup(group: PresentationModel): Single<Boolean> {
        database.runInTransaction({
            database.getGroupItemRepository()
                    .deleteGroupItemsByGroupId(group.id)

            database.getGroupRepository()
                    .saveGroup(GroupMapper.toDataModel(group))

            val groupItems: Array<GroupItem> = group.items
                    .map { GroupItemMapper.toDataModel(it, group.id) }.toTypedArray()

            database.getGroupItemRepository()
                    .saveGroupItems(*groupItems)
        })

        return Single.just(true)
    }
}
