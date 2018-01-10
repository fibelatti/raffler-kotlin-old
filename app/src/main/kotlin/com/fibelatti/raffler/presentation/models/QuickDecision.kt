package com.fibelatti.raffler.presentation.models

import android.os.Parcel
import android.os.Parcelable
import com.fibelatti.raffler.core.extensions.createParcel
import com.fibelatti.raffler.presentation.quickdecisions.adapter.ViewType

data class QuickDecision(
    val name: String,
    val items: List<String>
) : Parcelable, ViewType {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        mutableListOf<String>().apply {
            parcel.readList(this, String::class.java.classLoader)
        })

    override fun getViewType() = ViewType.QUICK_DECISION

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeList(items)
    }

    override fun describeContents() = 0

    companion object {
        val CREATOR = createParcel { QuickDecision(it) }
    }
}
