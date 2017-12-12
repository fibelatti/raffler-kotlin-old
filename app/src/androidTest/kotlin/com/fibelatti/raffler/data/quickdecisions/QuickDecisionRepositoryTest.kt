package com.fibelatti.raffler.data.quickdecisions

import android.support.test.runner.AndroidJUnit4
import com.fibelatti.raffler.BaseDbTest
import com.fibelatti.raffler.data.quickdecision.QuickDecision
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(AndroidJUnit4::class)
class QuickDecisionRepositoryTest : BaseDbTest() {
    companion object {
        const val QUICK_DECISION_ID = "TestId"
        const val QUICK_DECISION_LOCALE = "TestLocale"
        const val QUICK_DECISION_NAME = "TestName"
        const val QUICK_DECISION_VALUES = "TestValue1,TestValue2,TestValue3"
    }

    @Test
    fun addQuickDecision() {
        // Arrange
        val testObserver = TestObserver<List<QuickDecision>>()
        val quickDecision = QuickDecision(QUICK_DECISION_ID, QUICK_DECISION_LOCALE, QUICK_DECISION_NAME, QUICK_DECISION_VALUES)

        // Act
        appDatabase.getQuickDecisionRepository()
                .addQuickDecision(quickDecision)

        // Assert
        appDatabase.getQuickDecisionRepository()
                .fetchAllQuickDecisions()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        assertCompleteAndNoErrors(testObserver)
        assertTrue(testObserver.values()[0].isNotEmpty())
        val filteredList = testObserver.values()[0].filter { value -> value.id == QUICK_DECISION_ID }
        assertTrue(filteredList.isNotEmpty())
        assertEquals(QUICK_DECISION_ID, filteredList[0].id)
        assertEquals(QUICK_DECISION_LOCALE, filteredList[0].locale)
        assertEquals(QUICK_DECISION_NAME, filteredList[0].name)
        assertEquals(QUICK_DECISION_VALUES, filteredList[0].values)
    }
}
