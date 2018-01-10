package com.fibelatti.raffler.presentation.common

import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import com.fibelatti.raffler.R

class DialogHelper(private val alertDialogBuilder: AlertDialog.Builder) {
    fun newOkDialog(message: String, onClickListener: DialogInterface.OnClickListener): AlertDialog.Builder
        = alertDialogBuilder
        .setMessage(message)
        .setPositiveButton(R.string.hint_ok, onClickListener)
}
