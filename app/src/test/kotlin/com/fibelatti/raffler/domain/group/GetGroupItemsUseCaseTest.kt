package com.fibelatti.raffler.domain.group

import com.fibelatti.raffler.presentation.models.GroupItem
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.BDDMockito.given

class GetGroupItemsUseCaseTest : BaseGroupDomainTest() {
    private val getGroupItemsUseCase = GetGroupItemsUseCase(mockDatabase)
    private val testObserverList = TestObserver<List<GroupItem>>()

    @Test
    fun getAllGroupItems() {
        // Arrange
        given(mockGroupItemRepository.getAllGroupItemsByGroupId(GROUP_ID))
                .willReturn(Single.just(listOf(getSampleDataGroupItem1(), getSampleDataGroupItem2())))

        // Act
        getGroupItemsUseCase.getAllGroupItemsByGroupId(GROUP_ID)
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserverList)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserverList)
        val listResult = testObserverList.values()[0]
        assertEquals(GROUP_ITEM_ID_1, listResult[0].id)
        assertEquals(GROUP_ITEM_NAME_1, listResult[0].name)
        assertEquals(GROUP_ITEM_ID_2, listResult[1].id)
        assertEquals(GROUP_ITEM_NAME_2, listResult[1].name)
    }

    @Test
    fun getAllGroupItemsEmpty() {
        // Arrange
        given(mockGroupItemRepository.getAllGroupItemsByGroupId(GROUP_ID))
                .willReturn(Single.error(mockException))

        // Act
        getGroupItemsUseCase.getAllGroupItemsByGroupId(GROUP_ID)
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserverList)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserverList)
        val result = testObserverList.values()[0]
        assertTrue(result.isEmpty())
    }
}
