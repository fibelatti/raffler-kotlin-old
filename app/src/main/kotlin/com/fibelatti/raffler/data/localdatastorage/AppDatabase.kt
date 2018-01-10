package com.fibelatti.raffler.data.localdatastorage

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.migration.Migration
import com.fibelatti.raffler.data.group.Group
import com.fibelatti.raffler.data.group.GroupItem
import com.fibelatti.raffler.data.group.GroupItemRepositoryContract
import com.fibelatti.raffler.data.group.GroupRepositoryContract
import com.fibelatti.raffler.data.quickdecision.QuickDecision
import com.fibelatti.raffler.data.quickdecision.QuickDecisionRepositoryContract

const val DATABASE_NAME = "com.fibelatti.raffler.data.db"
const val DATABASE_VERSION = 5

@Database(entities = [Group::class, GroupItem::class, QuickDecision::class],
    version = DATABASE_VERSION,
    exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getGroupRepository(): GroupRepositoryContract
    abstract fun getGroupItemRepository(): GroupItemRepositoryContract
    abstract fun getQuickDecisionRepository(): QuickDecisionRepositoryContract

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
