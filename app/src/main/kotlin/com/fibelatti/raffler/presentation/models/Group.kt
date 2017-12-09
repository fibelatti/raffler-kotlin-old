package com.fibelatti.raffler.presentation.models

data class Group(val id: Long, val name: String, val items: List<GroupItem>) {
    val groupItemNames: List<String>?
        get() = items.map { item -> item.name }


    val concatenatedGroupItemNames: String
        get() = items.joinToString(",") { item -> item.name }

    val itemsCount: Int
        get() = this.items.size

    val selectedItems: List<GroupItem>
        get() = items.filter { item -> item.isIncluded }
}


