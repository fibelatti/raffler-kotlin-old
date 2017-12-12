package com.fibelatti.raffler.data.group

import android.support.test.runner.AndroidJUnit4
import com.fibelatti.raffler.data.BaseDbTest
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class GroupRepositoryTest : BaseDbTest() {
    companion object {
        const val GROUP_ID = 1L
        const val GROUP_NAME = "Group Name"
        const val GROUP_ITEM_ID = 10L
        const val GROUP_ITEM_NAME = "Group Item Name"
    }

    @Test
    fun getGroupById() {
        // Arrange
        val testObserver = TestObserver<GroupWithItems>()
        createAndInsertGroup()

        // Act
        appDatabase.getGroupRepository()
                .getGroupById(GROUP_ID)
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompleteAndNoErrors(testObserver)
        assertGroup(testObserver)
    }

    @Test
    fun getGroupByIdWithItems() {
        // Arrange
        val testObserver = TestObserver<GroupWithItems>()
        createAndInsertGroup()
        createAndInsertGroupItem()

        // Act
        appDatabase.getGroupRepository()
                .getGroupById(GROUP_ID)
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompleteAndNoErrors(testObserver)
        assertGroup(testObserver)
        assertGroupItems(testObserver)
    }

    @Test
    fun deleteGroupById() {
        // Arrange
        val testObserver = TestObserver<List<GroupWithItems>>()
        createAndInsertGroup()

        // Act
        appDatabase.getGroupRepository().deleteGroupById(GROUP_ID)

        // Assert
        appDatabase.getGroupRepository()
                .getAllGroups()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        assertCompleteAndNoErrors(testObserver)
        assertTrue(testObserver.values()[0].isEmpty())
    }

    @Test
    fun getAllGroupsEmpty() {
        // Arrange
        val testObserver = TestObserver<List<GroupWithItems>>()

        // Act
        appDatabase.getGroupRepository()
                .getAllGroups()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompleteAndNoErrors(testObserver)
        assertTrue(testObserver.values()[0].isEmpty())
    }

    @Test
    fun getAllGroupsNotEmpty() {
        // Arrange
        val testObserver = TestObserver<List<GroupWithItems>>()
        createAndInsertGroup()

        // Act
        appDatabase.getGroupRepository()
                .getAllGroups()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompleteAndNoErrors(testObserver)
        assertTrue(testObserver.values()[0].isNotEmpty())
    }

    private fun createAndInsertGroup() {
        appDatabase.getGroupRepository().saveGroup(Group(GROUP_ID, GROUP_NAME))
    }

    private fun createAndInsertGroupItem() {
        appDatabase.getGroupItemRepository().saveGroupItems(GroupItem(GROUP_ITEM_ID, GROUP_ID, GROUP_ITEM_NAME))
    }

    private fun assertGroup(testObserver: TestObserver<GroupWithItems>) {
        val result = testObserver.values()[0]
        assertEquals(GROUP_ID, result.group.id)
        assertEquals(GROUP_NAME, result.group.name)
    }

    private fun assertGroupItems(testObserver: TestObserver<GroupWithItems>) {
        val result = testObserver.values()[0]

        assertTrue(result.items.isNotEmpty())
        assertEquals(GROUP_ITEM_ID, result.items[0].id)
        assertEquals(GROUP_ITEM_NAME, result.items[0].name)
    }
}
