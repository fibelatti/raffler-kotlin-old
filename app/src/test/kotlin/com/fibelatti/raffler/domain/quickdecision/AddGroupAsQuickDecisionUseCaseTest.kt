package com.fibelatti.raffler.domain.quickdecision

import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.core.LOCALE_NONE
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.data.quickdecision.QuickDecision
import com.fibelatti.raffler.data.quickdecision.QuickDecisionRepositoryContract
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.GroupItem
import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class AddGroupAsQuickDecisionUseCaseTest : BaseTest() {
    private val mockAppDatabase = mock(AppDatabase::class.java)
    private val mockQuickDecisionsRepository = mock(QuickDecisionRepositoryContract::class.java)
    private val addGroupAsQuickDecisionUseCase = AddGroupAsQuickDecisionUseCase(mockAppDatabase)

    @Before
    fun setup() {
        given(mockAppDatabase.getQuickDecisionRepository())
            .willReturn(mockQuickDecisionsRepository)
    }

    @Test
    fun getAllQuickDecisionsLocaleEn() {
        // Arrange
        val groupItem1 = GroupItem(10L, "Item 1", true)
        val groupItem2 = GroupItem(20L, "Item 2", true)
        val group = Group(1L, "Group Name", listOf(groupItem1, groupItem2))
        val expectedQuickDecision = QuickDecision("1", LOCALE_NONE, "Group Name", "Item 1,Item 2")

        val testObserver = TestObserver<Completable>()

        // Act
        addGroupAsQuickDecisionUseCase.addGroupAsQuickDecision(group)
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        verify(mockQuickDecisionsRepository).addQuickDecision(expectedQuickDecision)
    }
}
