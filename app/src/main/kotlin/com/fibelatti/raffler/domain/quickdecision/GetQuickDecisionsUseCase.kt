package com.fibelatti.raffler.domain.quickdecision

import com.fibelatti.raffler.core.Constants
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.data.models.QuickDecision
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import com.fibelatti.raffler.presentation.models.QuickDecision as PresentationModel

class GetQuickDecisionsUseCase @Inject constructor(private val database: AppDatabase) {
    fun getAllQuickDecisions(): Single<List<PresentationModel>> {
        val locale = if (Constants.SUPPORTED_LOCALES.contains(Locale.getDefault().language))
            Locale.getDefault().language
        else
            Constants.LOCALE_EN

        return database.getQuickDecisionRepository()
                .fetchAllQuickDecisions()
                .flattenAsObservable<QuickDecision> { list -> list }
                .filter { quickDecision ->
                    quickDecision.locale == locale || quickDecision.locale == Constants.LOCALE_NONE
                }
                .map { quickDecision ->
                    QuickDecisionMapper.toPresentationModel(quickDecision)
                }
                .toList()
    }
}
