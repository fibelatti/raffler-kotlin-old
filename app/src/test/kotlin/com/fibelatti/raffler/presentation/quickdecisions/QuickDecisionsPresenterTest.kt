package com.fibelatti.raffler.presentation.quickdecisions

import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.domain.group.GetGroupsUseCase
import com.fibelatti.raffler.domain.quickdecision.AddGroupAsQuickDecisionUseCase
import com.fibelatti.raffler.domain.quickdecision.GetQuickDecisionsUseCase
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.QuickDecision
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class QuickDecisionsPresenterTest : BaseTest() {
    private val mockGetQuickDecisionsUseCase = mock(GetQuickDecisionsUseCase::class.java)
    private val mockAddGroupAsQuickDecisionUseCase = mock(AddGroupAsQuickDecisionUseCase::class.java)
    private val mockGetGroupUseCase = mock(GetGroupsUseCase::class.java)
    private val mockView = mock(QuickDecisionsContract.View::class.java)
    private val mockException: Exception = mock(Exception::class.java)

    private val mockQuickDecision = mock(QuickDecision::class.java)
    private val mockGroup = mock(Group::class.java)

    private val quickDecisionPresenter = QuickDecisionsPresenter(testSchedulerProvider,
        mockGetQuickDecisionsUseCase,
        mockAddGroupAsQuickDecisionUseCase,
        mockGetGroupUseCase)

    @Before
    fun setup() {
        given(mockException.message)
            .willReturn(GENERIC_ERROR_MESSAGE)

        quickDecisionPresenter.attachView(mockView)
    }

    @Test
    fun bootstrap() {
        // Arrange
        given(mockGetQuickDecisionsUseCase.getAllQuickDecisions())
            .willReturn(Single.just(listOf(mockQuickDecision)))

        // Act
        quickDecisionPresenter.bootstrap()

        // Assert
        verify(mockView).showProgress()
        verify(mockView).hideProgress()
        verify(mockView).onDataLoaded(listOf(mockQuickDecision))
    }

    @Test
    fun bootstrapError() {
        // Arrange
        given(mockGetQuickDecisionsUseCase.getAllQuickDecisions())
            .willReturn(Single.error(mockException))

        // Act
        quickDecisionPresenter.bootstrap()

        // Assert
        verify(mockView).showProgress()
        verify(mockView).hideProgress()
        verify(mockView).handleError(GENERIC_ERROR_MESSAGE)
    }

    @Test
    fun getQuickDecisionResult() {
        // Arrange
        val randomValue = "Random value"

        given(mockQuickDecision.items)
            .willReturn(listOf(randomValue))

        // Act
        quickDecisionPresenter.getQuickDecisionResult(mockQuickDecision)

        // Assert
        verify(mockView).onQuickDecisionResult(randomValue, false)
    }

    @Test
    fun addNewQuickDecisionNoGroups() {
        // Arrange
        given(mockGetGroupUseCase.getAllGroups())
            .willReturn(Single.just(emptyList()))

        // Act
        quickDecisionPresenter.addNewQuickDecision()

        // Assert
        verify(mockView).onGroupCreationRequired()
    }

    @Test
    fun addNewQuickDecisionWithGroups() {
        // Arrange
        given(mockGetGroupUseCase.getAllGroups())
            .willReturn(Single.just(listOf(mockGroup)))

        // Act
        quickDecisionPresenter.addNewQuickDecision()

        // Assert
        verify(mockView).onDisplayUserGroups(listOf(mockGroup))
    }

    @Test
    fun addGroupToQuickDecisions() {
        // Arrange
        given(mockAddGroupAsQuickDecisionUseCase.addGroupAsQuickDecision(mockGroup))
            .willReturn(Completable.complete())
        given(mockGetQuickDecisionsUseCase.getAllQuickDecisions())
            .willReturn(Single.just(listOf(mockQuickDecision)))

        // Act
        quickDecisionPresenter.addGroupToQuickDecisions(mockGroup)

        // Assert
        verify(mockView).showProgress()
        verify(mockView).hideProgress()
        verify(mockView).onQuickDecisionsUpdated(listOf(mockQuickDecision))
    }

    @Test
    fun addGroupToQuickDecisionsError() {
        // Arrange
        given(mockAddGroupAsQuickDecisionUseCase.addGroupAsQuickDecision(mockGroup))
            .willReturn(Completable.error(mockException))

        // Act
        quickDecisionPresenter.addGroupToQuickDecisions(mockGroup)

        // Assert
        verify(mockView).showProgress()
        verify(mockView).hideProgress()
        verify(mockView).handleError(GENERIC_ERROR_MESSAGE)
    }
}
