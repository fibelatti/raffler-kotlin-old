package com.fibelatti.raffler.data.localdatastorage

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import android.content.Context
import com.fibelatti.raffler.data.group.GroupItemRepositoryContract
import com.fibelatti.raffler.data.group.GroupRepositoryContract
import com.fibelatti.raffler.data.group.QuickDecisionRespositoryContract
import com.fibelatti.raffler.data.models.Group
import com.fibelatti.raffler.data.models.GroupItem
import com.fibelatti.raffler.data.models.QuickDecision
import com.fibelatti.raffler.di.qualifier.AppQualifier
import javax.inject.Inject

@Database(entities = [Group::class, GroupItem::class, QuickDecision::class],
        version = AppDatabase.DATABASE_VERSION)
abstract class AppDatabase @Inject constructor(@AppQualifier val context: Context) : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "com.fibelatti.raffler.data.db"
        const val DATABASE_VERSION = 5
    }

    abstract fun getGroupRepository(): GroupRepositoryContract
    abstract fun getGroupItemRepository(): GroupItemRepositoryContract
    abstract fun getQuickDecisionRepository(): QuickDecisionRespositoryContract

    object RoomCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            db.execSQL(QuickDecision.INITIAL_SETUP)
        }
    }

    object MigrationTo4 : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(QuickDecision.DROP_TABLE)
            database.execSQL(QuickDecision.CREATE_TABLE)

            database.execSQL("drop table if exists settings")
        }
    }

    object MigrationTo5 : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("drop table if exists settings")

            database.execSQL(QuickDecision.INITIAL_SETUP)
        }
    }
}