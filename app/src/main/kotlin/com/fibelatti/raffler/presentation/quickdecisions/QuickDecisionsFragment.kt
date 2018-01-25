package com.fibelatti.raffler.presentation.quickdecisions

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.fibelatti.raffler.R
import com.fibelatti.raffler.presentation.base.BaseFragment
import com.fibelatti.raffler.presentation.common.ItemOffsetDecoration
import com.fibelatti.raffler.presentation.common.ObservableView
import com.fibelatti.raffler.presentation.common.calculateColorGradient
import com.fibelatti.raffler.presentation.models.AddNewPlaceholder
import com.fibelatti.raffler.presentation.models.Group
import com.fibelatti.raffler.presentation.models.QuickDecision
import com.fibelatti.raffler.presentation.quickdecisions.adapter.QuickDecisionsAdapter
import com.fibelatti.raffler.presentation.quickdecisions.adapter.ViewType
import kotlinx.android.synthetic.main.fragment_recycler_view.layout_hintContainer
import kotlinx.android.synthetic.main.fragment_recycler_view.layout_root
import kotlinx.android.synthetic.main.fragment_recycler_view.recyclerView_cardItems
import javax.inject.Inject

class QuickDecisionsFragment :
    BaseFragment(),
    QuickDecisionsContract.View {

    companion object {
        val TAG: String = QuickDecisionsFragment::class.java.simpleName

        fun newInstance() = QuickDecisionsFragment()
    }

    @Inject
    lateinit var quickDecisionsPresenter: QuickDecisionsContract.Presenter
    @Inject
    lateinit var adapter: QuickDecisionsAdapter

    override val rootLayout: FrameLayout?
        get() = layout_root

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        activity?.let {
            getPresentationComponent(it).inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_recycler_view, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLayout()
        setupRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        quickDecisionsPresenter.bind(this)
    }

    override fun onPause() {
        quickDecisionsPresenter.unbind()
        super.onPause()
    }

    override fun handleError(errorMessage: String?) {
        showErrorLayout({ recoverFromError() }, errorMessage
            ?: getString(R.string.generic_msg_error))
    }

    override fun onNetworkError() {
        handleError(getString(R.string.network_msg_error))
    }

    override fun quickDecisionClicked(): ObservableView<QuickDecision> = adapter.getQuickDecisionClickEvent()

    override fun addNewClicked(): ObservableView<Unit> = adapter.getAddQuickDecisionClickEvent()

    override fun createGroup(): ObservableView<Group> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDataLoaded(quickDecisions: List<QuickDecision>) {
        val dataSet = ArrayList<ViewType>()

        dataSet.add(AddNewPlaceholder())
        dataSet.addAll(quickDecisions)

        context?.let {
            val colorList = calculateColorGradient(
                getColor(it, R.color.colorAccent),
                getColor(it, R.color.colorPrimary),
                dataSet.size - 1)

            adapter.colorList = colorList
        }

        adapter.addManyToList(dataSet)
    }

    override fun onQuickDecisionResult(result: String, isOdd: Boolean) {
        context?.let {
            val intentBuilder: QuickDecisionResultActivity.IntentBuilder = QuickDecisionResultActivity.IntentBuilder(it)
                .addExtraResult(result)
                .addExtraIsOdd(isOdd)

            startActivity(intentBuilder.build())
        }
    }

    override fun onDisplayUserGroups(groups: List<Group>) {
        // TODO implement after implementing groups
    }

    override fun onGroupCreationRequired() {
        // TODO implement after implementing groups
    }

    override fun onQuickDecisionsUpdated(quickDecisions: List<QuickDecision>) {
        // TODO implement after implementing groups
    }

    private fun setUpLayout() {
        showDismissibleHint(layout_hintContainer, hintMessage = getString(R.string.quick_decision_dismissible_hint))
    }

    private fun setupRecyclerView() {
        recyclerView_cardItems.addItemDecoration(ItemOffsetDecoration(recyclerView_cardItems.context, R.dimen.margin_smaller))
        recyclerView_cardItems.adapter = adapter
        recyclerView_cardItems.layoutManager = GridLayoutManager(context, 2)
    }

    private fun recoverFromError() {
        quickDecisionsPresenter.unbind()
        quickDecisionsPresenter.bind(this)
    }
}
