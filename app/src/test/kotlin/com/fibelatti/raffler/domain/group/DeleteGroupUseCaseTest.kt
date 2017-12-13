package com.fibelatti.raffler.domain.group

import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.verify

class DeleteGroupUseCaseTest : BaseGroupDomainTest() {
    private val deleteGroupUseCase = DeleteGroupUseCase(mockDatabase)
    private val testObserver = TestObserver<Completable>()

    @Test
    fun deleteGroup() {
        // Arrange
        Mockito.`when`(mockDatabase.runInTransaction(ArgumentMatchers.any()))
                .thenAnswer { mockedRunnable(it.arguments[0] as Runnable) }

        // Act
        deleteGroupUseCase.deleteGroup(getSamplePresentationGroup())
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        verify(mockGroupRepository).deleteGroupById(GROUP_ID)
        verify(mockQuickDecisionRepository).deleteQuickDecisionById(GROUP_ID.toString())
    }
}
