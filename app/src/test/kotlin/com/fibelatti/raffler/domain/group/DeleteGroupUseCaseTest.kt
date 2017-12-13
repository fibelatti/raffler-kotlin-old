package com.fibelatti.raffler.domain.group

import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.Mockito.verify

class DeleteGroupUseCaseTest : BaseGroupDomainTest() {
    private val deleteGroupUseCase = DeleteGroupUseCase(mockDatabase)
    private val testObserver = TestObserver<Completable>()

    @Test
    fun deleteGroup() {
        // Act
        deleteGroupUseCase.deleteGroup(getSamplePresentationGroup())
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        verify(mockGroupRepository).deleteGroupById(GROUP_ID)
    }
}
