package com.fibelatti.raffler.presentation.preferences

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.fibelatti.raffler.R
import com.fibelatti.raffler.presentation.base.BaseFragment
import com.fibelatti.raffler.presentation.models.Preferences
import kotlinx.android.synthetic.main.fragment_preferences.*
import kotlinx.android.synthetic.main.layout_preferences_analytics_opt_out.*
import kotlinx.android.synthetic.main.layout_preferences_crash_report_opt_out.*
import kotlinx.android.synthetic.main.layout_preferences_include_range.*
import kotlinx.android.synthetic.main.layout_preferences_roulette_music.*
import javax.inject.Inject

class PreferencesFragment :
        BaseFragment(),
        PreferencesContract.View {

    companion object {
        val TAG: String = PreferencesFragment::class.java.simpleName

        fun newInstance() = PreferencesFragment()
    }

    @Inject
    lateinit var preferencesPresenter: PreferencesContract.Presenter

    override val rootLayout: FrameLayout?
        get() = layout_root

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        activity?.let {
            getPresentationComponent(it).inject(this)
        }

        preferencesPresenter.attachView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_preferences, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLayout()
    }

    override fun onResume() {
        super.onResume()
        preferencesPresenter.getPreferences()
    }

    override fun onDetach() {
        preferencesPresenter.detachView()
        super.onDetach()
    }

    override fun handleError(errorMessage: String?) {
        showErrorLayout({ recoverFromError() }, errorMessage ?: getString(R.string.generic_msg_error))
    }

    override fun onNetworkError() {
        handleError(getString(R.string.network_msg_error))
    }

    override fun onPreferencesFetched(preferences: Preferences) {
        checkBox_rouletteMusic.isChecked = preferences.rouletteMusicEnabled
        checkBox_crashReportOptOut.isChecked = preferences.crashReportEnabled
        checkBox_analyticsOptOut.isChecked = preferences.analyticsEnabled
        checkBox_includeRange.isChecked = preferences.numberRangeEnabled
    }

    private fun setUpLayout() {

    }

    private fun recoverFromError() {
        preferencesPresenter.getPreferences()
    }
}
