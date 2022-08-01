package com.inhouse.cleannytimesapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.inhouse.cleannytimesapp.data.model.ArticleEntity
import com.inhouse.cleannytimesapp.data.model.MediaEntity
import com.inhouse.cleannytimesapp.data.model.MediaMetadataEntity

@Database(
    version = 1,
    entities = [ArticleEntity::class, MediaEntity::class, MediaMetadataEntity::class],
    exportSchema = false
)
@TypeConverters(RoomTypeConverters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}
