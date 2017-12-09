package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.GroupItem
import com.fibelatti.raffler.data.group.Group as DataModel
import com.fibelatti.raffler.presentation.models.Group as PresentationModel

object GroupMapper {
    fun toPresentationModel(group: DataModel): PresentationModel {
        return PresentationModel(group.id, group.name, group.items.map { GroupItemMapper.toPresentationModel(it) })
    }

    fun toDataModel(group: PresentationModel): DataModel {
        return DataModel(group.id, group.name, group.items.map { groupItem ->
            GroupItem(groupItem.id, group.id, groupItem.name)
        })
    }
}
