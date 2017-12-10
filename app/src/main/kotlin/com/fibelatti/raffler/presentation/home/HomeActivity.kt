package com.fibelatti.raffler.presentation.home

import android.os.Bundle
import android.support.annotation.IntDef
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.widget.FrameLayout
import com.fibelatti.raffler.R
import com.fibelatti.raffler.core.extensions.inTransaction
import com.fibelatti.raffler.presentation.base.BaseActivity
import com.fibelatti.raffler.presentation.preferences.PreferencesFragment
import com.fibelatti.raffler.presentation.quickdecisions.QuickDecisionsFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_toolbar_default.*
import javax.inject.Inject

class HomeActivity :
        BaseActivity(),
        NavigationContract.View,
        BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val BUNDLE_SELECTED_ITEM_ID = "SELECTED_ITEM_ID"
        private const val BUNDLE_CURRENT_VIEW = "CURRENT_VIEW"

        @IntDef(CURRENT_VIEW_NONE, CURRENT_VIEW_QUICK_DECISIONS, CURRENT_VIEW_GROUPS, CURRENT_VIEW_PREFERENCES)
        @Retention(AnnotationRetention.SOURCE)
        annotation class CurrentView

        private const val CURRENT_VIEW_NONE = -1L
        private const val CURRENT_VIEW_QUICK_DECISIONS = 0L
        private const val CURRENT_VIEW_GROUPS = 1L
        private const val CURRENT_VIEW_PREFERENCES = 2L
    }

    @Inject
    lateinit var presenter: NavigationContract.Presenter

    private var selectedItemId: Int = R.id.menuItem_quickDecisions
    @CurrentView
    private var currentView: Long = CURRENT_VIEW_NONE

    private lateinit var quickDecisionsFragment: QuickDecisionsFragment
    private lateinit var preferencesFragment: PreferencesFragment

    override val rootLayout: FrameLayout?
        get() = layout_root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getPresentationComponent(this).inject(this)

        savedInstanceState?.let { restoreFromState(it) }

        presenter.attachView(this)

        initFragments()
        setUpLayout()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putInt(BUNDLE_SELECTED_ITEM_ID, selectedItemId)
        outState?.putLong(BUNDLE_CURRENT_VIEW, currentView)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (selectedItemId != item.itemId) {
            selectedItemId = item.itemId
            item.isChecked = true

            when (item.itemId) {
                R.id.menuItem_quickDecisions -> presenter.onQuickDecisionsClicked()
                R.id.menuItem_myGroups -> presenter.onGroupsClicked()
                R.id.menuItem_preferences -> presenter.onPreferencesClicked()
            }
        }

        return true
    }

    override fun goToQuickDecisions() {
        updateContent(CURRENT_VIEW_QUICK_DECISIONS)
    }

    override fun goToGroups() {
        updateContent(CURRENT_VIEW_GROUPS)
    }

    override fun goToPreferences() {
        updateContent(CURRENT_VIEW_PREFERENCES)
    }

    private fun setUpLayout() {
        setSupportActionBar(toolbar)

        updateContent(currentView)

        layout_bottomNavigation.selectedItemId = selectedItemId
        layout_bottomNavigation.setOnNavigationItemSelectedListener(this)
    }

    private fun restoreFromState(savedInstanceState: Bundle) {
        selectedItemId = savedInstanceState.getInt(BUNDLE_SELECTED_ITEM_ID)
        currentView = savedInstanceState.getLong(BUNDLE_CURRENT_VIEW)
    }

    private fun initFragments() {
        quickDecisionsFragment = QuickDecisionsFragment.newInstance()
        preferencesFragment = PreferencesFragment.newInstance()
    }

    private fun updateContent(@CurrentView currentView: Long) {
        if (this.currentView != currentView || currentView == CURRENT_VIEW_NONE) {
            this.currentView = currentView

            when (currentView) {
                CURRENT_VIEW_NONE,
                CURRENT_VIEW_QUICK_DECISIONS -> {
                    title = getString(R.string.home_menu_item_quick_decisions)
                    toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorAccent))
                    layout_bottomNavigation.itemBackgroundResource = R.color.colorAccent
                    pushFragment(quickDecisionsFragment)
                }
                CURRENT_VIEW_GROUPS -> {
                    title = getString(R.string.home_menu_item_my_groups)
                    toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    layout_bottomNavigation.itemBackgroundResource = R.color.colorPrimary
//                    pushFragment(searchMoviesFragment)
                }
                CURRENT_VIEW_PREFERENCES -> {
                    title = getString(R.string.home_menu_item_preferences)
                    toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorGrayDark))
                    layout_bottomNavigation.itemBackgroundResource = R.color.colorGrayDark
                    pushFragment(preferencesFragment)
                }
            }
        }
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager.inTransaction {
            replace(R.id.layout_fragmentContainer, fragment)
        }
    }
}
