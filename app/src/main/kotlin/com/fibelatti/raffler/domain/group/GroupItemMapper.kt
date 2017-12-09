package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.GroupItem
import com.fibelatti.raffler.presentation.models.GroupItem as PresentationModel

object GroupItemMapper {
    fun toPresentationModel(groupItem: GroupItem): PresentationModel {
        return PresentationModel(groupItem.id, groupItem.name, true)
    }
}
