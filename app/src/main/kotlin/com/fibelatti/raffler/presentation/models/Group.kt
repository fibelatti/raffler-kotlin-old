package com.fibelatti.raffler.presentation.models

data class Group(val id: Long, val name: String, val items: List<GroupItem>) {
    val concatenatedGroupItemNames: String
        get() = items.joinToString(",") { item -> item.name }
}


