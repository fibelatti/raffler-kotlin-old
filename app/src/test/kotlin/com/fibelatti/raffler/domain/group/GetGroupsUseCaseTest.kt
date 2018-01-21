package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.data.group.GroupWithItems
import com.fibelatti.raffler.presentation.models.Group
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.BDDMockito.given

class GetGroupsUseCaseTest : BaseGroupDomainTest() {
    private val getGroupUseCase = GetGroupsUseCase(mockDatabase)
    private val testObserverList = TestObserver<List<Group>>()
    private val testObserver = TestObserver<Group>()

    @Test
    fun getAllGroups() {
        // Arrange
        given(mockGroupRepository.getAllGroups())
            .willReturn(Single.just(listOf(getSampleDatabaseResponse())))

        // Act
        getGroupUseCase.getAllGroups()
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserverList)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserverList)
        val listResult = testObserverList.values()[0]
        assertEquals(getSamplePresentationGroup(), listResult.first())
    }

    @Test
    fun getAllGroupsEmpty() {
        // Arrange
        given(mockGroupRepository.getAllGroups())
            .willReturn(Single.error(mockException))

        // Act
        getGroupUseCase.getAllGroups()
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserverList)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserverList)
        val result = testObserverList.values()[0]
        assertTrue(result.isEmpty())
    }

    @Test
    fun getGroupById() {
        // Arrange
        given(mockGroupRepository.getGroupById(GROUP_ID))
            .willReturn(Single.just(getSampleDatabaseResponse()))

        // Act
        getGroupUseCase.getGroupById(GROUP_ID)
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserver)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserver)
        assertEquals(getSamplePresentationGroup(), testObserver.values()[0])
    }

    @Test
    fun getGroupByIdError() {
        // Arrange
        given(mockGroupRepository.getGroupById(GROUP_ID))
            .willReturn(Single.error(mockException))

        // Act
        getGroupUseCase.getGroupById(GROUP_ID)
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserver)

        // Assert
        testObserver.assertError(mockException)
    }

    private fun getSampleDatabaseResponse(): GroupWithItems {
        val groupWithItems = GroupWithItems()

        groupWithItems.group = getSampleDataGroup()
        groupWithItems.items = listOf(getSampleDataGroupItem1(), getSampleDataGroupItem2())

        return groupWithItems
    }
}
