package com.fibelatti.raffler.presentation.quickdecisions.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fibelatti.raffler.R
import com.fibelatti.raffler.core.extensions.inflate
import com.fibelatti.raffler.presentation.base.BaseDelegateAdapter
import com.fibelatti.raffler.presentation.base.BaseViewType
import com.fibelatti.raffler.presentation.common.ObservableView
import com.fibelatti.raffler.presentation.models.AddNewPlaceholder
import kotlinx.android.synthetic.main.list_item_quick_decision.view.*

class AddNewDelegateAdapter : BaseDelegateAdapter {
    val itemClickObservable = ObservableView<AddNewPlaceholder>()
    var colorList: List<Int> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BaseViewType) {
        (holder as? ViewHolder)?.bind()
    }

    internal inner class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.list_item_add_new)) {
        fun bind() = with(itemView) {
            layout_cardView.setCardBackgroundColor(colorList[layoutPosition % colorList.size])

            itemView.setOnClickListener { itemClickObservable.emitNext(AddNewPlaceholder()) }
        }
    }
}
