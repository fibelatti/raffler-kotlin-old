package com.fibelatti.raffler.presentation.preferences

import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.domain.preferences.GetPreferencesUseCase
import com.fibelatti.raffler.domain.preferences.UpdatePreferencesUseCase
import com.fibelatti.raffler.presentation.models.Preferences
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class PreferencesPresenterTest: BaseTest() {
    private val mockGetPreferencesUseCase = mock(GetPreferencesUseCase::class.java)
    private val mockUpdatePreferencesUseCase = mock(UpdatePreferencesUseCase::class.java)
    private val mockView = mock(PreferencesContract.View::class.java)
    private val mockPreferences = mock(Preferences::class.java)
    private val mockException = mock(Exception::class.java)

    private val preferencesPresenter = PreferencesPresenter(testSchedulerProvider, mockGetPreferencesUseCase, mockUpdatePreferencesUseCase)

    @Before
    fun setUp() {
        preferencesPresenter.attachView(mockView)
    }

    @Test
    fun testGetPreferencesIsSuccessful() {
        // Arrange
        given(mockGetPreferencesUseCase.getPreferences())
                .willReturn(Single.just(mockPreferences))

        // Act
        preferencesPresenter.getPreferences()

        // Assert
        verify(mockView).showProgress()
        verify(mockView).hideProgress()
        verify(mockView).onPreferencesFetched(mockPreferences)
    }

    @Test
    fun testGetPreferencesError() {
        // Arrange
        given(mockGetPreferencesUseCase.getPreferences())
                .willReturn(Single.error(mockException))
        given(mockException.message)
                .willReturn("Error")

        // Act
        preferencesPresenter.getPreferences()

        // Assert
        verify(mockView).showProgress()
        verify(mockView).hideProgress()
        verify(mockView).handleError("Error")
    }

    @Test
    fun testUpdatePreferencesIsSuccessful() {
        // Arrange
        given(mockUpdatePreferencesUseCase.updatePreferences(mockPreferences))
                .willReturn(Completable.complete())

        // Act
        preferencesPresenter.updatePreferences(mockPreferences)

        // Assert
        verify(mockView).onPreferencesUpdated()
    }

    @Test
    fun testUpdatePreferencesError() {
        // Arrange
        given(mockUpdatePreferencesUseCase.updatePreferences(mockPreferences))
                .willReturn(Completable.error(mockException))
        given(mockException.message)
                .willReturn("Error")

        // Act
        preferencesPresenter.updatePreferences(mockPreferences)

        // Assert
        verify(mockView).hideProgress()
        verify(mockView).handleError("Error")
    }
}
