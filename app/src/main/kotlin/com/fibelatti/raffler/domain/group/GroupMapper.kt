package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.Group
import com.fibelatti.raffler.data.group.GroupWithItems as DataModel
import com.fibelatti.raffler.presentation.models.Group as PresentationModel

object GroupMapper {
    fun toPresentationModel(groupWithItems: DataModel): PresentationModel {
        return PresentationModel(groupWithItems.group.id, groupWithItems.group.name, groupWithItems.items.map { GroupItemMapper.toPresentationModel(it) })
    }

    fun toDataModel(group: PresentationModel): Group {
        return Group(group.id, group.name)
    }
}
