package com.fibelatti.raffler.data

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.fibelatti.raffler.BaseTest
import com.fibelatti.raffler.data.localdatastorage.AppDatabase
import org.junit.After
import org.junit.Before

abstract class BaseDbTest: BaseTest() {
    protected lateinit var appDatabase: AppDatabase

    @Before
    fun initDb() {
        appDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                AppDatabase::class.java).build()
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }
}
