package com.fibelatti.raffler.domain.preferences

import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.data.preferences.PreferencesRepository
import com.fibelatti.raffler.presentation.models.Preferences
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class GetPreferencesUseCaseTest : BaseTest() {
    private val mockPreferencesRepository = mock(PreferencesRepository::class.java)

    private val getPreferencesUseCase = GetPreferencesUseCase(mockPreferencesRepository)

    @Test
    fun testGetPreferencesAllValuesShouldBeTrue() {
        // Arrange
        val testObserver = TestObserver<Preferences>()

        arrangeRepository(true)

        // Act
        getPreferencesUseCase.getPreferences()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompleteAndNoErrors(testObserver)

        val preferences = testObserver.values()[0]

        assert(preferences.rouletteMusicEnabled)
        assert(preferences.crashReportEnabled)
        assert(preferences.analyticsEnabled)
        assert(preferences.numberRangeEnabled)
    }

    @Test
    fun testGetPreferencesAllValuesShouldBeFalse() {
        // Arrange
        val testObserver = TestObserver<Preferences>()

        arrangeRepository(false)

        // Act
        getPreferencesUseCase.getPreferences()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompleteAndNoErrors(testObserver)

        val preferences = testObserver.values()[0]

        assert(!preferences.rouletteMusicEnabled)
        assert(!preferences.crashReportEnabled)
        assert(!preferences.analyticsEnabled)
        assert(!preferences.numberRangeEnabled)
    }

    private fun arrangeRepository(value: Boolean) {
        given(mockPreferencesRepository.getRouletteMusicEnabled())
                .willReturn(Single.just(value))
        given(mockPreferencesRepository.getCrashReportEnabled())
                .willReturn(Single.just(value))
        given(mockPreferencesRepository.getAnalyticsEnabled())
                .willReturn(Single.just(value))
        given(mockPreferencesRepository.getNumberRangeEnabled())
                .willReturn(Single.just(value))
    }
}
