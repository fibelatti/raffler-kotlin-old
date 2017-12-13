package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.data.group.GroupItemRepositoryContract
import com.fibelatti.raffler.data.group.GroupRepositoryContract
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.data.quickdecision.QuickDecisionRepositoryContract
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.GroupItem
import org.junit.Before
import org.mockito.BDDMockito
import org.mockito.Mockito.mock

abstract class BaseGroupDomainTest: BaseTest() {
    protected val mockDatabase: AppDatabase = mock(AppDatabase::class.java)
    protected val mockGroupRepository: GroupRepositoryContract = mock(GroupRepositoryContract::class.java)
    protected val mockGroupItemRepository: GroupItemRepositoryContract = mock(GroupItemRepositoryContract::class.java)
    protected val mockQuickDecisionRepository: QuickDecisionRepositoryContract = mock(QuickDecisionRepositoryContract::class.java)

    companion object {
        const val GROUP_ID = 1L
        const val GROUP_NAME = "Group name"

        const val GROUP_ITEM_ID_1 = 10L
        const val GROUP_ITEM_NAME_1 = "Item 1"

        const val GROUP_ITEM_ID_2 = 20L
        const val GROUP_ITEM_NAME_2 = "Item 2"
    }

    @Before
    fun setup() {
        BDDMockito.given(mockDatabase.getGroupRepository())
                .willReturn(mockGroupRepository)
        BDDMockito.given(mockDatabase.getGroupItemRepository())
                .willReturn(mockGroupItemRepository)
    }

    protected fun getSamplePresentationGroup(): Group {
        val groupItem1 = GroupItem(GROUP_ITEM_ID_1, GROUP_ITEM_NAME_1, true)
        val groupItem2 = GroupItem(GROUP_ITEM_ID_2, GROUP_ITEM_NAME_2, true)

        return Group(GROUP_ID, GROUP_NAME, listOf(groupItem1, groupItem2))
    }

    protected fun getSampleDataGroup() = com.fibelatti.raffler.data.group.Group(GROUP_ID, GROUP_NAME)

    protected fun getSampleDataGroupItem1() = com.fibelatti.raffler.data.group.GroupItem(GROUP_ITEM_ID_1, GROUP_ID, GROUP_ITEM_NAME_1)

    protected fun getSampleDataGroupItem2() = com.fibelatti.raffler.data.group.GroupItem(GROUP_ITEM_ID_2, GROUP_ID, GROUP_ITEM_NAME_2)
}
