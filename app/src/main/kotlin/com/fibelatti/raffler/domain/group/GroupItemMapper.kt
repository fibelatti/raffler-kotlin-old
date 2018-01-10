package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.GroupItem as DataModel
import com.fibelatti.raffler.presentation.models.GroupItem as PresentationModel

object GroupItemMapper {
    fun toPresentationModel(groupItem: DataModel) =
        PresentationModel(groupItem.id, groupItem.name, true)

    fun toDataModel(groupItem: PresentationModel, groupId: Long) =
        DataModel(groupItem.id, groupId, groupItem.name)
}
