package com.fibelatti.raffler.presentation.preferences

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.fibelatti.raffler.R
import com.fibelatti.raffler.core.snackbar
import com.fibelatti.raffler.core.withItIfNotNull
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

    lateinit var preferences: Preferences

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
        this.preferences = preferences

        checkBox_rouletteMusic.isChecked = preferences.rouletteMusicEnabled
        checkBox_crashReportOptOut.isChecked = preferences.crashReportEnabled
        checkBox_analyticsOptOut.isChecked = preferences.analyticsEnabled
        checkBox_includeRange.isChecked = preferences.numberRangeEnabled

        setListeners()
    }

    override fun onPreferencesUpdated() {
        layout_root.snackbar(getString(R.string.preferences_changes_saved))
    }

    private fun setUpLayout() {
        withItIfNotNull(activity) {
            try {
                val pInfo = packageManager.getPackageInfo(packageName, 0)
                textView_appVersion.text = getString(R.string.preferences_app_version, pInfo.versionName)
            } catch (e: PackageManager.NameNotFoundException) {
                textView_appVersion.visibility = View.GONE
            }
        }
    }

    private fun setListeners() {
        checkBox_rouletteMusic.setOnCheckedChangeListener { _, isChecked ->
            preferences.rouletteMusicEnabled = isChecked
            preferencesPresenter.updatePreferences(preferences)
        }

        textView_crashReportOptOut.movementMethod = LinkMovementMethod.getInstance()

        checkBox_crashReportOptOut.setOnCheckedChangeListener { _, isChecked ->
            preferences.crashReportEnabled = isChecked
            preferencesPresenter.updatePreferences(preferences)
        }

        textView_analyticsOptOut.movementMethod = LinkMovementMethod.getInstance()

        checkBox_analyticsOptOut.setOnCheckedChangeListener { _, isChecked ->
            preferences.analyticsEnabled = isChecked
            preferencesPresenter.updatePreferences(preferences)
        }

        checkBox_includeRange.setOnCheckedChangeListener { _, isChecked ->
            preferences.numberRangeEnabled = isChecked
            preferencesPresenter.updatePreferences(preferences)
        }

        button_share.setOnClickListener {
            //analyticsHelper.fireShareAppEvent()

            val message = getString(R.string.preferences_share_text, String.format("%s?id=%s", "http://play.google.com/store/apps/details", activity?.packageName))
            val share = Intent(Intent.ACTION_SEND)
            share.type = "text/plain"
            share.putExtra(Intent.EXTRA_TEXT, message)

            startActivity(Intent.createChooser(share, getString(R.string.preferences_share_title)))
        }

        button_rate.setOnClickListener {
            //analyticsHelper.fireRateAppEvent()
            //val rateFragment = RateAppDialogFragment()
            //rateFragment.show(supportFragmentManager, RateAppDialogFragment.TAG)
        }
    }

    private fun recoverFromError() {
        preferencesPresenter.getPreferences()
    }
}
