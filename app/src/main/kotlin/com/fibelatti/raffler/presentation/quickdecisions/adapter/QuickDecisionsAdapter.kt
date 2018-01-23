package com.fibelatti.raffler.presentation.quickdecisions.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fibelatti.raffler.presentation.base.BaseDelegateAdapter
import com.fibelatti.raffler.presentation.base.BaseViewType
import com.fibelatti.raffler.presentation.common.ObservableView
import com.fibelatti.raffler.presentation.models.QuickDecision
import javax.inject.Inject

interface ViewType : BaseViewType {
    companion object {
        const val ADD_NEW = 0
        const val QUICK_DECISION = 1
    }
}

class QuickDecisionsAdapter @Inject constructor(
    private val quickDecisionDelegateAdapter: QuickDecisionDelegateAdapter,
    private val addNewDelegateAdapter: AddNewDelegateAdapter
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var items: MutableList<BaseViewType> = ArrayList()
    var colorList: List<Int> = ArrayList()
        set(value) {
            field = value
            quickDecisionDelegateAdapter.colorList = colorList
            addNewDelegateAdapter.colorList = colorList
        }

    private val delegateAdapters = SparseArrayCompat<BaseDelegateAdapter>()

    init {
        delegateAdapters.put(ViewType.ADD_NEW, addNewDelegateAdapter)
        delegateAdapters.put(ViewType.QUICK_DECISION, quickDecisionDelegateAdapter)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegateAdapters[viewType].onCreateViewHolder(parent)

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    fun getQuickDecisionClickEvent(): ObservableView<QuickDecision> = quickDecisionDelegateAdapter.itemClickObservable

    fun getAddQuickDecisionClickEvent(): ObservableView<Unit> = addNewDelegateAdapter.itemClickObservable

    fun addManyToList(listItems: List<BaseViewType>) {
        items.clear()
        items.addAll(listItems)
        notifyDataSetChanged()
    }
}
