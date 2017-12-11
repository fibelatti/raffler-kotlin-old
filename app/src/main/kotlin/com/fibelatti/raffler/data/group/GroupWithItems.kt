package com.fibelatti.raffler.data.group

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

class GroupWithItems {
    @Embedded
    var group: Group = Group()

    @Relation(parentColumn = Group.COLUMN_ID, entityColumn = GroupItem.COLUMN_GROUP_ID)
    var items: List<GroupItem> = ArrayList()
}
