package com.fibelatti.raffler.data.group

import android.support.test.runner.AndroidJUnit4
import com.fibelatti.raffler.data.BaseDbTest
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class GroupItemRepositoryTest : BaseDbTest() {
    companion object {
        const val GROUP_ID = 1L
        const val GROUP_NAME = "Group Name"
        const val GROUP_ID_OTHER = 2L
        const val GROUP_NAME_OTHER = "Group Name Other"

        const val GROUP_ITEM_ID_1 = 10L
        const val GROUP_ITEM_NAME_1 = "Group Name 1"
        const val GROUP_ITEM_ID_2 = 20L
        const val GROUP_ITEM_NAME_2 = "Group Name 2"
        const val GROUP_ITEM_ID_3 = 30L
        const val GROUP_ITEM_NAME_3 = "Group Name 3"
    }

    @Test
    fun getAllGroupItemsByGroupId() {
        // Arrange
        val testObserver = TestObserver<List<GroupItem>>()
        createSampleData()

        // Act
        appDatabase.getGroupItemRepository()
            .getAllGroupItemsByGroupId(GROUP_ID)
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserver)

        // Assert
        val result = testObserver.values()[0]

        assertSingleOnCompleteWithNoErrors(testObserver)
        assertTrue(result.isNotEmpty())
        assertEquals(2, result.size)
    }

    @Test
    fun deleteGroupItemsByGroupId() {
        // Arrange
        val testObserver = TestObserver<List<GroupItem>>()
        val testObserverOther = TestObserver<List<GroupItem>>()
        createSampleData()

        // Act
        appDatabase.getGroupItemRepository()
            .deleteGroupItemsByGroupId(GROUP_ID)

        // Assert
        appDatabase.getGroupItemRepository()
            .getAllGroupItemsByGroupId(GROUP_ID)
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserver)
        val result = testObserver.values()[0]

        assertSingleOnCompleteWithNoErrors(testObserver)
        assertEquals(0, result.size)

        appDatabase.getGroupItemRepository()
            .getAllGroupItemsByGroupId(GROUP_ID_OTHER)
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserverOther)
        val resultOther = testObserverOther.values()[0]

        assertSingleOnCompleteWithNoErrors(testObserverOther)
        assertEquals(1, resultOther.size)
    }

    @Test
    fun deleteGroupItemsById() {
        // Arrange
        val testObserver = TestObserver<List<GroupItem>>()
        createSampleData()

        // Act
        appDatabase.getGroupItemRepository()
            .deleteGroupItemsById(Collections.singletonList(GROUP_ITEM_ID_1))

        // Assert
        appDatabase.getGroupItemRepository()
            .getAllGroupItemsByGroupId(GROUP_ID)
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserver)
        val result = testObserver.values()[0]

        assertSingleOnCompleteWithNoErrors(testObserver)
        assertEquals(1, result.size)
    }

    private fun createSampleData() {
        createAndInsertGroup(GROUP_ID, GROUP_NAME)
        createAndInsertGroup(GROUP_ID_OTHER, GROUP_NAME_OTHER)
        createAndInsertGroupItem(GROUP_ITEM_ID_1, GROUP_ID, GROUP_ITEM_NAME_1)
        createAndInsertGroupItem(GROUP_ITEM_ID_2, GROUP_ID, GROUP_ITEM_NAME_2)
        createAndInsertGroupItem(GROUP_ITEM_ID_3, GROUP_ID_OTHER, GROUP_ITEM_NAME_3)
    }

    private fun createAndInsertGroup(groupId: Long, groupName: String) {
        appDatabase.getGroupRepository().saveGroup(Group(groupId, groupName))
    }

    private fun createAndInsertGroupItem(groupItemId: Long, groupId: Long, groupItemName: String) {
        appDatabase.getGroupItemRepository().saveGroupItems(GroupItem(groupItemId, groupId, groupItemName))
    }
}
