package com.inhouse.cleannytimesapp.data.local.db

import androidx.room.Room
import com.google.gson.Gson
import org.junit.After
import org.junit.Before
import org.robolectric.RuntimeEnvironment

abstract class DbTest {
    private lateinit var articleDatabase: ArticleDatabase
    val db: ArticleDatabase
        get() = articleDatabase

    @Before
    fun setup() {
        articleDatabase = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.getApplication(),
            ArticleDatabase::class.java
        ).allowMainThreadQueries()
            .addTypeConverter(RoomTypeConverters(Gson()))
            .build()
    }

    @After
    fun close() {
        db.close()
    }
}
