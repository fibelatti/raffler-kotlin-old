package com.fibelatti.raffler.presentation.quickdecisions.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fibelatti.raffler.R
import com.fibelatti.raffler.core.extensions.inflate
import com.fibelatti.raffler.presentation.base.BaseDelegateAdapter
import com.fibelatti.raffler.presentation.base.BaseViewType
import com.fibelatti.raffler.presentation.models.QuickDecision
import kotlinx.android.synthetic.main.list_item_quick_decision.view.*

class QuickDecisionDelegateAdapter : BaseDelegateAdapter {
    interface Listener {
        fun onQuickDecisionClicked(quickDecision: QuickDecision)
    }

    var listener: Listener? = null
    var colorList: List<Int> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder = DataViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: BaseViewType) {
        (holder as DataViewHolder).bind(item as QuickDecision)
    }

    internal inner class DataViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(parent.inflate(R.layout.list_item_quick_decision)) {
        fun bind(item: QuickDecision) = with(itemView) {
            layout_cardView.setCardBackgroundColor(colorList[layoutPosition % colorList.size])

            textView_quickDecisionName.text = item.name

            setOnClickListener({ listener?.onQuickDecisionClicked(item) })
        }
    }
}
