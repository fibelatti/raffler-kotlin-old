package com.fibelatti.raffler.presentation.quickdecisions

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.FrameLayout
import com.fibelatti.raffler.R
import com.fibelatti.raffler.presentation.common.ColorUtils
import com.fibelatti.raffler.core.extensions.ifNotNullThisElseThat
import com.fibelatti.raffler.presentation.base.BaseActivity
import com.fibelatti.raffler.presentation.base.BaseIntentBuilder
import kotlinx.android.synthetic.main.activity_quick_decision_result.*

class QuickDecisionResultActivity
    : BaseActivity() {

    companion object {
        private const val BUNDLE_RESULT = "RESULT"
        private const val BUNDLE_IS_ODD = "IS_ODD"
    }

    lateinit var result: String
    var isOdd: Boolean = false

    override val rootLayout: FrameLayout?
        get() = layout_root

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quick_decision_result)

        savedInstanceState.ifNotNullThisElseThat({ restoreFromState(it) }, { parseIntent(intent) })
        setupLayout()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString(BUNDLE_RESULT, result)
        outState?.putBoolean(BUNDLE_IS_ODD, isOdd)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorWhiteOpaque)
        }
    }

    private fun parseIntent(intent: Intent) {
        result = intent.getStringExtra(BUNDLE_RESULT)
        isOdd = intent.getBooleanExtra(BUNDLE_IS_ODD, false)
    }

    private fun restoreFromState(savedInstanceState: Bundle) {
        result = savedInstanceState.getString(BUNDLE_RESULT)
        isOdd = savedInstanceState.getBoolean(BUNDLE_IS_ODD)
    }

    private fun setupLayout() {
        val color = ContextCompat.getColor(this, if (isOdd) R.color.colorAccent else R.color.colorPrimary)
        val sourceDrawable = ContextCompat.getDrawable(this, R.drawable.ic_close_white_24dp)

        sourceDrawable?.let {
            val sourceBitmap = ColorUtils.convertDrawableToBitmap(it)
            val changedBitmap = ColorUtils.changeImageColor(sourceBitmap, color)

            fab_close.setImageBitmap(changedBitmap)
        }

        text_result.text = result
        text_result.setBackgroundColor(color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = color
        }

        fab_close.setOnClickListener { finish() }
    }

    class IntentBuilder(context: Context) : BaseIntentBuilder<QuickDecisionResultActivity>(context, QuickDecisionResultActivity::class.java) {
        fun addExtraResult(result: String): IntentBuilder {
            intent.putExtra(BUNDLE_RESULT, result)
            return this
        }

        fun addExtraIsOdd(isOdd: Boolean): IntentBuilder {
            intent.putExtra(BUNDLE_IS_ODD, isOdd)
            return this
        }
    }
}
