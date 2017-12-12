package com.fibelatti.raffler.data.preferences

import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.data.localdatastorage.UserPreferencesContract
import io.reactivex.observers.TestObserver
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class PreferencesRepositoryTest : BaseTest() {
    private val mockUserPreferences = mock(UserPreferencesContract::class.java)
    private val preferencesRepository = PreferencesRepository(mockUserPreferences)

    @Test
    fun getRouletteMusicEnabled() {
        // Arrange
        val testObserver = TestObserver<Boolean>()

        given(mockUserPreferences.getRouletteMusicEnabled())
                .willReturn(true)

        // Act
        preferencesRepository.getRouletteMusicEnabled()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserver)
        assert(testObserver.values()[0])
    }

    @Test
    fun setRouletteMusicEnabled() {
        // Arrange
        val testObserver = TestObserver<Boolean>()

        // Act
        preferencesRepository.setRouletteMusicEnabled(true)
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        verify(mockUserPreferences).setRouletteMusicEnabled(true)
    }

    @Test
    fun getCrashReportEnabled() {
        // Arrange
        val testObserver = TestObserver<Boolean>()

        given(mockUserPreferences.getCrashReportEnabled())
                .willReturn(true)

        // Act
        preferencesRepository.getCrashReportEnabled()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserver)
        assert(testObserver.values()[0])
    }

    @Test
    fun setCrashReportEnabled() {
        // Arrange
        val testObserver = TestObserver<Boolean>()

        // Act
        preferencesRepository.setCrashReportEnabled(true)
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        verify(mockUserPreferences).setCrashReportEnabled(true)
    }

    @Test
    fun getAnalyticsEnabled() {
        // Arrange
        val testObserver = TestObserver<Boolean>()

        given(mockUserPreferences.getAnalyticsEnabled())
                .willReturn(true)

        // Act
        preferencesRepository.getAnalyticsEnabled()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserver)
        assert(testObserver.values()[0])
    }

    @Test
    fun setAnalyticsEnabled() {
        // Arrange
        val testObserver = TestObserver<Boolean>()

        // Act
        preferencesRepository.setAnalyticsEnabled(true)
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        verify(mockUserPreferences).setAnalyticsEnabled(true)
    }

    @Test
    fun getNumberRangeEnabled() {
        // Arrange
        val testObserver = TestObserver<Boolean>()

        given(mockUserPreferences.getNumberRangeEnabled())
                .willReturn(true)

        // Act
        preferencesRepository.getNumberRangeEnabled()
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertSingleOnCompleteWithNoErrors(testObserver)
        assert(testObserver.values()[0])
    }

    @Test
    fun setNumberRangeEnabled() {
        // Arrange
        val testObserver = TestObserver<Boolean>()

        // Act
        preferencesRepository.setNumberRangeEnabled(true)
                .subscribeOn(testSchedulerProvider.io())
                .observeOn(testSchedulerProvider.mainThread())
                .subscribe(testObserver)

        // Assert
        assertCompletableOnComplete(testObserver)
        verify(mockUserPreferences).setNumberRangeEnabled(true)
    }
}
