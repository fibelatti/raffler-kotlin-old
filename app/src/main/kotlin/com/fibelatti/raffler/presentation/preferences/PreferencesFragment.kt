package com.fibelatti.raffler.presentation.preferences

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.fibelatti.raffler.R
import com.fibelatti.raffler.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_preferences.*

class PreferencesFragment :
        BaseFragment(),
        PreferencesContract.View {

    companion object {
        val TAG: String = PreferencesFragment::class.java.simpleName

        fun newInstance() = PreferencesFragment()
    }

    override val rootLayout: FrameLayout?
        get() = layout_root

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        activity?.let {
            getPresentationComponent(it).inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_preferences, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLayout()
    }

    override fun handleError(errorMessage: String?) {
        showErrorLayout({ recoverFromError() }, errorMessage ?: getString(R.string.generic_msg_error))
    }

    override fun onNetworkError() {
        handleError(getString(R.string.network_msg_error))
    }

    private fun setUpLayout() {

    }

    private fun recoverFromError() {

    }
}