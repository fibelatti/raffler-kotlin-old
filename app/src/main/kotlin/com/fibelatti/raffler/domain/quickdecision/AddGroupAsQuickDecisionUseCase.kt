package com.fibelatti.raffler.domain.quickdecision

import com.fibelatti.raffler.core.LOCALE_NONE
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import com.fibelatti.raffler.data.quickdecision.QuickDecision
import com.fibelatti.raffler.presentation.models.Group
import io.reactivex.Completable
import javax.inject.Inject
import com.fibelatti.raffler.presentation.models.QuickDecision as PresentationModel

class AddGroupAsQuickDecisionUseCase @Inject constructor(private val database: AppDatabase) {
    fun addGroupAsQuickDecision(group: Group): Completable {
        val quickDecision = QuickDecision(
                group.id.toString(),
                LOCALE_NONE,
                group.name,
                group.concatenatedGroupItemNames)

        database.getQuickDecisionRepository()
                .addQuickDecision(quickDecision)

        return Completable.complete()
    }
}
