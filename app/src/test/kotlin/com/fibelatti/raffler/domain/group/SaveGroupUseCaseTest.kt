package com.fibelatti.raffler.domain.group

import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class SaveGroupUseCaseTest : BaseGroupDomainTest() {
    private val saveGroupUseCase = SaveGroupUseCase(mockDatabase)
    private val testObserver = TestObserver<Completable>()

    @Test
    fun saveGroup() {
        // Arrange
        `when`(mockDatabase.runInTransaction(any()))
            .thenAnswer { mockedRunnable(it.arguments[0] as Runnable) }

        // Act
        saveGroupUseCase.saveGroup(getSamplePresentationGroup())
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        verify(mockGroupItemRepository).deleteGroupItemsByGroupId(GROUP_ID)
        verify(mockGroupRepository).saveGroup(getSampleDataGroup())
        verify(mockGroupItemRepository).saveGroupItems(getSampleDataGroupItem1(), getSampleDataGroupItem2())
    }
}
