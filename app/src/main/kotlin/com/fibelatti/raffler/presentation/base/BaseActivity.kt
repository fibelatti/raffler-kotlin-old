package com.fibelatti.raffler.presentation.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.fibelatti.raffler.R

fun AppCompatActivity.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun AppCompatActivity.hideKeyboard() {
    val view = this.currentFocus
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commitAllowingStateLoss()
}

abstract class BaseActivity : AppCompatActivity(), BaseContract.View {
    private lateinit var progressBarLayout: View
    private lateinit var placeholderRetryLayout: View

    private var progressBarLayoutAdded = false
    private var errorLayoutAdded = false

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressBarLayout = layoutInflater.inflate(R.layout.layout_progress_bar_default, null)
        placeholderRetryLayout = layoutInflater.inflate(R.layout.layout_placeholder_retry_button, null)
    }

    override fun showProgress() {
        rootLayout?.let {
            if (!progressBarLayoutAdded) {
                it.addView(progressBarLayout)

                progressBarLayout.bringToFront()

                progressBarLayoutAdded = true
            }
        }
    }

    override fun hideProgress() {
        rootLayout?.let {
            if (progressBarLayoutAdded) {
                it.removeView(progressBarLayout)
                progressBarLayoutAdded = false
            }
        }
    }

    protected fun showErrorLayout(retryButtonListener: () -> Unit, errorMessage: String = getString(R.string.generic_msg_error)) {
        rootLayout?.let {
            if (!errorLayoutAdded) {
                val textViewErrorMessage = placeholderRetryLayout.findViewById<AppCompatTextView>(R.id.textView_errorMessage)
                val retryButton = placeholderRetryLayout.findViewById<AppCompatButton>(R.id.button_retry)

                textViewErrorMessage.text = errorMessage

                retryButton.setOnClickListener {
                    hideErrorLayout()
                    retryButtonListener()
                }

                it.addView(placeholderRetryLayout)

                placeholderRetryLayout.bringToFront()

                errorLayoutAdded = true
            }
        }
    }

    private fun hideErrorLayout() {
        rootLayout?.let {
            it.removeView(placeholderRetryLayout)
            errorLayoutAdded = false
        }
    }
}
