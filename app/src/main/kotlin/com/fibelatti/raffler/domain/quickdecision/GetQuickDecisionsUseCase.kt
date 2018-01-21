package com.fibelatti.raffler.domain.quickdecision

import com.fibelatti.raffler.core.LOCALE_EN
import com.fibelatti.raffler.core.LOCALE_NONE
import com.fibelatti.raffler.core.SUPPORTED_LOCALES
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.data.quickdecision.QuickDecision
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import com.fibelatti.raffler.presentation.models.QuickDecision as PresentationModel

class GetQuickDecisionsUseCase @Inject constructor(private val database: AppDatabase, private val locale: Locale) {
    fun getAllQuickDecisions(): Single<List<PresentationModel>> {
        val locale = if (SUPPORTED_LOCALES.contains(locale.language.toLowerCase()))
            locale.language
        else
            LOCALE_EN

        return database.getQuickDecisionRepository()
            .fetchAllQuickDecisions()
            .flattenAsObservable<QuickDecision> { list -> list }
            .filter { quickDecision ->
                quickDecision.locale == locale || quickDecision.locale == LOCALE_NONE
            }
            .map { quickDecision ->
                QuickDecisionMapper.toPresentationModel(quickDecision)
            }
            .toList()
    }
}
