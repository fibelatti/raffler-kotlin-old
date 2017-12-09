package com.fibelatti.raffler.domain.quickdecision

import com.fibelatti.raffler.core.Constants
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.data.models.QuickDecision
import com.fibelatti.raffler.presentation.models.Group
import io.reactivex.Single
import javax.inject.Inject
import com.fibelatti.raffler.presentation.models.QuickDecision as PresentationModel

class AddGroupAsQuickDecisionUseCase @Inject constructor(private val database: AppDatabase) {
    fun addGroupAsQuickDecision(group: Group): Single<Boolean> {
        val quickDecision = QuickDecision(
                group.id.toString(),
                Constants.LOCALE_NONE,
                group.name,
                group.concatenatedGroupItemNames)

        return database.getQuickDecisionRepository()
                .addQuickDecision(quickDecision)
    }
}
