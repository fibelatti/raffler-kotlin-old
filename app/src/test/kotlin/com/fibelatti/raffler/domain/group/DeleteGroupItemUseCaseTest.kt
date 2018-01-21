package com.fibelatti.raffler.domain.group

import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.Mockito

class DeleteGroupItemUseCaseTest : BaseGroupDomainTest() {
    private val deleteGroupItemUseCase = DeleteGroupItemUseCase(mockDatabase)
    private val testObserver = TestObserver<Completable>()

    @Test
    fun deleteGroupItems() {
        // Act
        deleteGroupItemUseCase.deleteGroupItems(getSamplePresentationGroup().items)
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        Mockito.verify(mockGroupItemRepository).deleteGroupItemsById(listOf(GROUP_ITEM_ID_1, GROUP_ITEM_ID_2))

    }
}
