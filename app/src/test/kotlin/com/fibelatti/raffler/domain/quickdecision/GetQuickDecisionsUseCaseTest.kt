package com.fibelatti.raffler.domain.quickdecision

import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.core.Constants
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.data.quickdecision.QuickDecisionRepositoryContract
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.util.*
import com.fibelatti.raffler.data.quickdecision.QuickDecision as DataModel
import com.fibelatti.raffler.presentation.models.QuickDecision as PresentationModel

class GetQuickDecisionsUseCaseTest : BaseTest() {
    private val mockAppDatabase = mock(AppDatabase::class.java)
    private val mockLocale = mock(Locale::class.java)
    private val mockQuickDecisionsRepository = mock(QuickDecisionRepositoryContract::class.java)
    private val getQuickDecisionsUseCase = GetQuickDecisionsUseCase(mockAppDatabase, mockLocale)

    private val testObserver = TestObserver<List<PresentationModel>>()

    companion object {
        private const val QUICK_DECISION_ID_EN = "id_en"
        private const val QUICK_DECISION_ID_ES = "id_es"
        private const val QUICK_DECISION_ID_CUSTOM = "id_custom"

        private const val QUICK_DECISION_NAME_EN = "Quick Decision En"
        private const val QUICK_DECISION_NAME_ES = "Quick Decision Es"
        private const val QUICK_DECISION_NAME_CUSTOM = "Quick Decision Custom"

        private const val QUICK_DECISION_VALUES = "value1,value2"

        private const val LOCALE_FR = "fr"
    }

    @Before
    fun setup() {
        given(mockAppDatabase.getQuickDecisionRepository())
                .willReturn(mockQuickDecisionsRepository)
        given(mockQuickDecisionsRepository.fetchAllQuickDecisions())
                .willReturn(Single.just(getSampleData()))
    }

    @Test
    fun getAllQuickDecisionsLocaleEn() {
        // Arrange
        given(mockLocale.language)
                .willReturn(Constants.LOCALE_EN)

        // Act
        getQuickDecisionsUseCase.getAllQuickDecisions()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserver)
        val result = testObserver.values()[0]
        assertEquals(2, result.size)
        assertNotNull(result.find { quickDecision -> quickDecision.name == QUICK_DECISION_NAME_EN })
        assertNotNull(result.find { quickDecision -> quickDecision.name == QUICK_DECISION_NAME_CUSTOM })
    }

    @Test
    fun getAllQuickDecisionsLocaleEs() {
        // Arrange
        given(mockLocale.language)
                .willReturn(Constants.LOCALE_ES)

        // Act
        getQuickDecisionsUseCase.getAllQuickDecisions()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserver)
        val result = testObserver.values()[0]
        assertEquals(2, result.size)
        assertNotNull(result.find { quickDecision -> quickDecision.name == QUICK_DECISION_NAME_ES })
        assertNotNull(result.find { quickDecision -> quickDecision.name == QUICK_DECISION_NAME_CUSTOM })
    }

    @Test
    fun getAllQuickDecisionsUnsupportedLocale() {
        // Arrange
        given(mockLocale.language)
                .willReturn(LOCALE_FR)

        // Act
        getQuickDecisionsUseCase.getAllQuickDecisions()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserver)
        val result = testObserver.values()[0]
        assertEquals(2, result.size)
        assertNotNull(result.find { quickDecision -> quickDecision.name == QUICK_DECISION_NAME_EN })
        assertNotNull(result.find { quickDecision -> quickDecision.name == QUICK_DECISION_NAME_CUSTOM })
    }

    private fun getSampleData(): List<DataModel> {
        val quickDecisionEn = DataModel(QUICK_DECISION_ID_EN, Constants.LOCALE_EN, QUICK_DECISION_NAME_EN, QUICK_DECISION_VALUES)
        val quickDecisionEs = DataModel(QUICK_DECISION_ID_ES, Constants.LOCALE_ES, QUICK_DECISION_NAME_ES, QUICK_DECISION_VALUES)
        val quickDecisionCustom = DataModel(QUICK_DECISION_ID_CUSTOM, Constants.LOCALE_NONE, QUICK_DECISION_NAME_CUSTOM, QUICK_DECISION_VALUES)

        return listOf(quickDecisionEn, quickDecisionEs, quickDecisionCustom)
    }
}
