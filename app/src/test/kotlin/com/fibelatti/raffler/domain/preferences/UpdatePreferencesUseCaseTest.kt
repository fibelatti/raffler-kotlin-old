package com.fibelatti.raffler.domain.preferences

import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.data.preferences.PreferencesRepository
import com.fibelatti.raffler.presentation.models.Preferences
import io.reactivex.Completable
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class UpdatePreferencesUseCaseTest : BaseTest() {
    private val mockPreferencesRepository = mock(PreferencesRepository::class.java)

    private val updatePreferencesUseCase = UpdatePreferencesUseCase(mockPreferencesRepository)

    @Test
    fun testUpdatePreferencesSetAllValuesToTrue() {
        // Arrange
        val testObserver = TestObserver<Boolean>()
        val preferences = arrangePreferences(true)
        arrangeRepository(true)

        // Act
        updatePreferencesUseCase.updatePreferences(preferences)
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        verifyRepository(true)
    }

    @Test
    fun testUpdatePreferencesSetAllValuesToFalse() {
        // Arrange
        val testObserver = TestObserver<Boolean>()
        val preferences = arrangePreferences(false)
        arrangeRepository(false)

        // Act
        updatePreferencesUseCase.updatePreferences(preferences)
            .subscribeOn(testSchedulerProvider.io())
            .observeOn(testSchedulerProvider.mainThread())
            .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        verifyRepository(false)
    }

    private fun arrangePreferences(value: Boolean) = Preferences(value, value, value, value)

    private fun arrangeRepository(value: Boolean) {
        given(mockPreferencesRepository.setRouletteMusicEnabled(value))
            .willReturn(Completable.complete())
        given(mockPreferencesRepository.setCrashReportEnabled(value))
            .willReturn(Completable.complete())
        given(mockPreferencesRepository.setAnalyticsEnabled(value))
            .willReturn(Completable.complete())
        given(mockPreferencesRepository.setNumberRangeEnabled(value))
            .willReturn(Completable.complete())
    }

    private fun verifyRepository(value: Boolean) {
        verify(mockPreferencesRepository).setRouletteMusicEnabled(value)
        verify(mockPreferencesRepository).setCrashReportEnabled(value)
        verify(mockPreferencesRepository).setAnalyticsEnabled(value)
        verify(mockPreferencesRepository).setNumberRangeEnabled(value)
    }
}
