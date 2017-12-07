package com.fibelatti.raffler.domain.preferences

import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.data.preferences.PreferencesRepository
import com.fibelatti.raffler.presentation.models.Preferences
import io.reactivex.observers.TestObserver
import junit.framework.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

class UpdatePreferencesUseCaseTest : BaseTest() {
    private val mockPreferencesRepository = Mockito.mock(PreferencesRepository::class.java)

    private val updatePreferencesUseCase = UpdatePreferencesUseCase(mockPreferencesRepository)

    @Test
    fun testUpdatePreferencesSetAllValuesToTrue() {
        // Arrange
        val testObserver = TestObserver<Boolean>()
        val preferences = arrangePreferences(true)

        // Act
        updatePreferencesUseCase.updatePreferences(preferences)
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompleteAndNoErrors(testObserver)
        Assert.assertTrue(testObserver.values()[0])
        verifyRepository(true)
    }

    @Test
    fun testUpdatePreferencesSetAllValuesToFalse() {
        // Arrange
        val testObserver = TestObserver<Boolean>()
        val preferences = arrangePreferences(false)

        // Act
        updatePreferencesUseCase.updatePreferences(preferences)
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompleteAndNoErrors(testObserver)
        Assert.assertTrue(testObserver.values()[0])
        verifyRepository(false)
    }

    private fun arrangePreferences(value: Boolean): Preferences {
        return Preferences(value, value, value, value)
    }

    private fun verifyRepository(value: Boolean) {
        verify(mockPreferencesRepository).setRouletteMusicEnabled(value)
        verify(mockPreferencesRepository).setCrashReportEnabled(value)
        verify(mockPreferencesRepository).setAnalyticsEnabled(value)
        verify(mockPreferencesRepository).setNumberRangeEnabled(value)
    }
}