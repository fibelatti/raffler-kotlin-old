package com.fibelatti.raffler.presentation.common

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

class DialogHelper {
    companion object {
        fun newOkDialog(context: Context, message: String, onClickListener: DialogInterface.OnClickListener): AlertDialog.Builder
                = AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, onClickListener)
    }
}
