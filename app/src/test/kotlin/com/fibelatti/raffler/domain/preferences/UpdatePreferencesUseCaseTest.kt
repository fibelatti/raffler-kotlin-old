package com.fibelatti.raffler.domain.preferences

import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.data.preferences.PreferencesRepository
import com.fibelatti.raffler.presentation.models.Preferences
import io.reactivex.Single
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
        assertCompleteAndNoErrors(testObserver)
        assert(testObserver.values()[0])
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
        assertCompleteAndNoErrors(testObserver)
        assert(testObserver.values()[0])
        verifyRepository(false)
    }

    private fun arrangePreferences(value: Boolean) = Preferences(value, value, value, value)

    private fun arrangeRepository(value: Boolean) {
        given(mockPreferencesRepository.setRouletteMusicEnabled(value))
                .willReturn(Single.just(true))
        given(mockPreferencesRepository.setCrashReportEnabled(value))
                .willReturn(Single.just(true))
        given(mockPreferencesRepository.setAnalyticsEnabled(value))
                .willReturn(Single.just(true))
        given(mockPreferencesRepository.setNumberRangeEnabled(value))
                .willReturn(Single.just(true))
    }

    private fun verifyRepository(value: Boolean) {
        verify(mockPreferencesRepository).setRouletteMusicEnabled(value)
        verify(mockPreferencesRepository).setCrashReportEnabled(value)
        verify(mockPreferencesRepository).setAnalyticsEnabled(value)
        verify(mockPreferencesRepository).setNumberRangeEnabled(value)
    }
}
