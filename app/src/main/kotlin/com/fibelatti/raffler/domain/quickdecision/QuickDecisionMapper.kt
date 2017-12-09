package com.fibelatti.raffler.domain.quickdecision

import com.fibelatti.raffler.data.quickdecision.QuickDecision
import com.fibelatti.raffler.presentation.models.QuickDecision as PresentationModel

object QuickDecisionMapper {
    fun toPresentationModel(quickDecision: QuickDecision): PresentationModel {
        return PresentationModel(quickDecision.name, quickDecision.values.split(","))
    }
}
